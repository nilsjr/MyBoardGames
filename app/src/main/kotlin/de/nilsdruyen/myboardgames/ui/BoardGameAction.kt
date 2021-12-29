package de.nilsdruyen.myboardgames.ui

import de.nilsdruyen.myboardgames.base.MviAction

sealed class BoardGameAction: MviAction {

  object LoadAction: BoardGameAction()
}
