/*
 * Created by Nils Druyen on 12-30-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.detail

import dagger.hilt.android.lifecycle.HiltViewModel
import de.nilsdruyen.myboardgames.base.BaseViewModel
import de.nilsdruyen.myboardgames.data.BoardGameRepository
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(
  private val repository: BoardGameRepository
) : BaseViewModel<GameDetailContract.GameDetailAction,
        GameDetailContract.GameDetailState,
        GameDetailContract.GameDetailIntent>() {

  override fun createInitialState(): GameDetailContract.GameDetailState =
    GameDetailContract.GameDetailState.Loading

  override fun intentToAction(intent: GameDetailContract.GameDetailIntent): GameDetailContract.GameDetailAction {
    TODO("Not yet implemented")
  }

  override fun handleAction(action: GameDetailContract.GameDetailAction) {
    launchOnUI {
      when (action) {
        is GameDetailContract.GameDetailAction.LoadGame -> {
          val game = repository.get(action.id)
          setState {
            GameDetailContract.GameDetailState.Details(game)
          }
        }
        is GameDetailContract.GameDetailAction.DeleteGame -> {}
      }
    }
  }
}