/*
 * Created by Nils Druyen on 12-30-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.detail

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import de.nilsdruyen.myboardgames.base.BaseViewModel
import de.nilsdruyen.myboardgames.data.BoardGameRepository
import de.nilsdruyen.myboardgames.ui.Screen
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(
  private val repository: BoardGameRepository,
  val saveState: SavedStateHandle,
) : BaseViewModel<GameDetailAction, GameDetailState, GameDetailIntent>() {

  val gameId = saveState.get<String>(Screen.GameDetails.argument)!!

  init {

    launchOnUI {
      val game = repository.get(gameId)
      setState { Details(game) }
    }
  }

  override fun createInitialState(): GameDetailState = Loading

  override fun intentToAction(intent: GameDetailIntent): GameDetailAction {
    TODO("Not yet implemented")
  }

  override fun handleAction(action: GameDetailAction) {
//    launchOnUI {
//      when (action) {
//        is LoadGame -> {
//          val game = repository.get(action.id)
//          setState { Details(game) }
//        }
//        is DeleteGame -> {
//          repository.delete(action.id)
//          setState { GameDeleted }
//        }
//      }
    }
}