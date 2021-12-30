/*
 * Created by Nils Druyen on 12-30-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.detail

import de.nilsdruyen.myboardgames.base.ViewAction
import de.nilsdruyen.myboardgames.base.ViewIntent
import de.nilsdruyen.myboardgames.base.ViewState
import de.nilsdruyen.myboardgames.data.models.BoardGame

interface GameDetailContract {

  sealed class GameDetailAction : ViewAction {

    data class LoadGame(val id: String) : GameDetailAction()

    data class DeleteGame(val id: String) : GameDetailAction()
  }

  sealed class GameDetailState : ViewState {

    object Loading : GameDetailState()

    data class Details(val game: BoardGame) : GameDetailState()
  }

  sealed class GameDetailIntent : ViewIntent
}