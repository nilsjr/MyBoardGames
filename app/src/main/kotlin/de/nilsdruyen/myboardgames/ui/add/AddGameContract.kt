/*
 * Created by Nils Druyen on 12-31-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.add

import de.nilsdruyen.myboardgames.base.ViewAction
import de.nilsdruyen.myboardgames.base.ViewIntent
import de.nilsdruyen.myboardgames.base.ViewState
import de.nilsdruyen.myboardgames.data.models.BoardGame

sealed interface AddGameAction : ViewAction

data class AddAction(val game: BoardGame) : AddGameAction

sealed interface AddGameState : ViewState

object Empty : AddGameState

object GameAdded : AddGameState

data class AddError(val type: AddErrorType) : AddGameState

sealed interface AddGameIntent : ViewIntent

data class AddIntent(val game: BoardGame) : AddGameIntent