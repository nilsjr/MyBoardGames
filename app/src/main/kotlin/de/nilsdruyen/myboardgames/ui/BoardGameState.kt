/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui

import de.nilsdruyen.myboardgames.base.ViewState
import de.nilsdruyen.myboardgames.data.models.BoardGame

sealed class BoardGameState : ViewState {

  object Loading : BoardGameState()

  data class Overview(val games: List<BoardGame> = emptyList()) : BoardGameState()
}