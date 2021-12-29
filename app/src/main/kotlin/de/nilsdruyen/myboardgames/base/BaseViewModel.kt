/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Intent : ViewIntent, Action : ViewAction, State : ViewState> :
  ViewModel() {

  private val _state = MutableStateFlow<State?>(null)
  val state: StateFlow<State?> get() = _state

  fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch { block() }
  }

  fun dispatchIntent(intent: Intent) {
    handleAction(fromIntent(intent))
  }

  abstract fun fromIntent(intent: Intent): Action

  abstract fun handleAction(action: Action)
}