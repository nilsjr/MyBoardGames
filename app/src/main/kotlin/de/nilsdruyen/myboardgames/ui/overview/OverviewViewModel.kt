/*
 * Created by Nils Druyen on 01-02-2022
 * Copyright © 2022 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.overview

import dagger.hilt.android.lifecycle.HiltViewModel
import de.nilsdruyen.myboardgames.base.BaseViewModel
import de.nilsdruyen.myboardgames.data.BoardGameRepository
import de.nilsdruyen.myboardgames.data.models.GameType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
  private val repository: BoardGameRepository
) : BaseViewModel<OverviewAction, OverviewState, OverviewIntent>() {

  private val initialFilterState: FilterState by lazy {
    FilterState(isActive = false, GameType.values().toList().associateWith { true })
  }

  private val _filterState = MutableStateFlow(initialFilterState)
  val filterState: StateFlow<FilterState> get() = _filterState

  override fun createInitialState(): OverviewState = Loading

  override fun intentToAction(intent: OverviewIntent): OverviewAction =
    when (intent) {
      is LoadOverview -> LoadGames
    }

  override fun handleAction(action: OverviewAction) {
    launchOnUI {
      when (action) {
        LoadGames -> {
          val list = repository.observeList().first()
          setState {
            Timber.d("state: ${list.size}")
            if (list.isEmpty()) EmptyList else AllGames(list)
          }
        }
        is ApplyFilter -> {}
        ResetFilter -> {}
      }
    }
  }
}