/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui

import de.nilsdruyen.myboardgames.base.ViewAction
import de.nilsdruyen.myboardgames.base.ViewIntent
import de.nilsdruyen.myboardgames.base.ViewState

interface MainContract {

  sealed class MainAction : ViewAction {

    object DoNothing : MainAction()
  }

  sealed class MainIntent : ViewIntent {

    object DoNothing : MainIntent()
  }

  sealed class MainState : ViewState {

    object DoNothing : MainState()
  }
}