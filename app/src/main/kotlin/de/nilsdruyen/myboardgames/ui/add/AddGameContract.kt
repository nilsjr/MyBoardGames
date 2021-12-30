/*
 * Created by Nils Druyen on 12-30-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.add

import de.nilsdruyen.myboardgames.base.ViewAction
import de.nilsdruyen.myboardgames.base.ViewIntent
import de.nilsdruyen.myboardgames.base.ViewState

interface AddGameContract {

  sealed class AddGameAction : ViewAction {

    data class Add(val name: String) : AddGameAction()
  }

  sealed class AddGameState : ViewState {

    object Empty : AddGameState()

    object GameAdded : AddGameState()
  }

  sealed class AddGameIntent : ViewIntent {

    data class Add(val name: String) : AddGameIntent()
  }
}