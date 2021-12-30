/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.overview

import dagger.hilt.android.lifecycle.HiltViewModel
import de.nilsdruyen.myboardgames.base.BaseViewModel
import de.nilsdruyen.myboardgames.data.BoardGameRepository
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
  private val repository: BoardGameRepository
) :
  BaseViewModel<OverviewContract.OverviewAction, OverviewContract.OverviewState, OverviewContract.OverviewIntent>() {

  override fun createInitialState(): OverviewContract.OverviewState =
    OverviewContract.OverviewState.Loading

  override fun intentToAction(intent: OverviewContract.OverviewIntent): OverviewContract.OverviewAction =
    when (intent) {
      is OverviewContract.OverviewIntent.LoadOverview -> OverviewContract.OverviewAction.LoadGames
    }

  override fun handleAction(action: OverviewContract.OverviewAction) {
    launchOnUI {
      when (action) {
        OverviewContract.OverviewAction.LoadGames -> {
          val list = repository.observeList().first()
          setState {
            Timber.d("state: ${list.size}")
            if (list.isEmpty()) {
              OverviewContract.OverviewState.EmptyList
            } else {
              OverviewContract.OverviewState.AllGames(list)
            }
          }
        }
      }
    }
  }
}