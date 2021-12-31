/*
 * Created by Nils Druyen on 12-31-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.add

import dagger.hilt.android.lifecycle.HiltViewModel
import de.nilsdruyen.myboardgames.base.BaseViewModel
import de.nilsdruyen.myboardgames.data.BoardGameRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddGameViewModel @Inject constructor(
  private val repository: BoardGameRepository
) : BaseViewModel<AddGameAction, AddGameState, AddGameIntent>() {

  override fun createInitialState(): AddGameState = Empty

  override fun intentToAction(intent: AddGameIntent): AddGameAction =
    when (intent) {
      is AddIntent -> AddAction(intent.game)
    }

  override fun handleAction(action: AddGameAction) {
    launchOnUI {
      when (action) {
        is AddAction -> {
          kotlin.runCatching { repository.add(action.game) }.onSuccess {
            Timber.d("Game added")
            setState { GameAdded }
          }.onFailure {
            Timber.e(it)
            setState { AddError(AddErrorType.DuplicateNameOrId) }
          }
        }
      }
    }
  }
}