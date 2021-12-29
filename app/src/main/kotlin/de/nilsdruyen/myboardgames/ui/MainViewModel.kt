/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.nilsdruyen.myboardgames.data.BoardGameRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val repository: BoardGameRepository
) : ViewModel() {

  private val _overviewState = mutableStateOf<BoardGameState>(BoardGameState.Loading)
  val overviewState: State<BoardGameState> get() = _overviewState

  init {
    loadGames()
  }

  private fun loadGames() {
    viewModelScope.launch {
      val list = repository.observeList().first()
      _overviewState.value = BoardGameState.Overview(list)
    }
  }
}