/*
 * Created by Nils Druyen on 01-02-2022
 * Copyright © 2022 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.overview

import de.nilsdruyen.myboardgames.base.ViewAction
import de.nilsdruyen.myboardgames.base.ViewIntent
import de.nilsdruyen.myboardgames.base.ViewState
import de.nilsdruyen.myboardgames.data.models.BoardGame

interface OverviewContract

sealed class OverviewAction : ViewAction

object LoadGames : OverviewAction()

data class ApplyFilter(val filterState: FilterState) : OverviewAction()

object ResetFilter : OverviewAction()

sealed class OverviewIntent : ViewIntent

object LoadOverview : OverviewIntent()

sealed class OverviewState : ViewState

object Loading : OverviewState()

object EmptyList : OverviewState()

data class AllGames(val games: List<BoardGame> = emptyList()) : OverviewState()
