/*
 * Created by Nils Druyen on 12-30-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.add

import dagger.hilt.android.lifecycle.HiltViewModel
import de.nilsdruyen.myboardgames.base.BaseViewModel
import de.nilsdruyen.myboardgames.data.BoardGameRepository
import de.nilsdruyen.myboardgames.data.models.BoardGameFactory
import javax.inject.Inject

@HiltViewModel
class AddGameViewModel @Inject constructor(
  private val repository: BoardGameRepository
) :
  BaseViewModel<AddGameContract.AddGameAction, AddGameContract.AddGameState, AddGameContract.AddGameIntent>() {

  override fun createInitialState(): AddGameContract.AddGameState =
    AddGameContract.AddGameState.Empty

  override fun intentToAction(intent: AddGameContract.AddGameIntent): AddGameContract.AddGameAction =
    when (intent) {
      is AddGameContract.AddGameIntent.Add -> AddGameContract.AddGameAction.Add(intent.name)
    }

  override fun handleAction(action: AddGameContract.AddGameAction) {
    launchOnUI {
      when (action) {
        is AddGameContract.AddGameAction.Add -> {
          repository.add(BoardGameFactory.buildNewGame(name = action.name))
          setState {
            AddGameContract.AddGameState.GameAdded
          }
        }
      }
    }
  }
}