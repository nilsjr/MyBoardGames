package de.nilsdruyen.myboardgames.ui

import de.nilsdruyen.myboardgames.base.MviState
import de.nilsdruyen.myboardgames.model.BoardGame

sealed class BoardGameState : MviState {

  object Loading : BoardGameState()

  data class Overview(val games: List<BoardGame> = emptyList()) : BoardGameState()
}
