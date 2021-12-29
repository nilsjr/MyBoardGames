/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui

import de.nilsdruyen.myboardgames.base.ViewIntent

sealed class BoardGameIntent : ViewIntent {

  object LoadBoardGames : BoardGameIntent()
}
