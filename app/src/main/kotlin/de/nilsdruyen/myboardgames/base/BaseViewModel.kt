/*
 * Created by Nils Druyen on 01-27-2022
 * Copyright © 2022 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.nilsdruyen.myboardgames.annotations.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel<Action : ViewAction, State : ViewState, Intent : ViewIntent> :
    ViewModel() {

    @Inject
    @MainDispatcher
    lateinit var dispatcher: CoroutineDispatcher

    private val initialState: State by lazy { createInitialState() }

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state

    private val currentState: State
        get() = state.value

    private val _event: MutableSharedFlow<Action> = MutableSharedFlow()
    private val event: SharedFlow<Action> = _event.asSharedFlow()

    private val _intent: Channel<Intent> = Channel()
    val intent = _intent.receiveAsFlow()

    init {
        observerEvents()
    }

    private fun observerEvents() {
        viewModelScope.launch(dispatcher) {
            event.collect(this@BaseViewModel::handleAction)
        }
    }

    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(dispatcher) { block() }
    }

    fun dispatchIntent(intent: Intent) {
        handleAction(intentToAction(intent))
    }

    abstract fun createInitialState(): State

    abstract fun intentToAction(intent: Intent): Action

    abstract fun handleAction(action: Action)

    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _state.value = newState
    }

    fun setAction(action: Action) {
        viewModelScope.launch(dispatcher) {
            _event.emit(action)
        }
    }

    fun setIntent(builder: () -> Intent) {
        viewModelScope.launch(dispatcher) {
            _intent.send(builder())
        }
    }
}