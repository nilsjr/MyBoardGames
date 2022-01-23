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
import kotlinx.coroutines.flow.combine
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
    val filterState: StateFlow<FilterState> = _filterState

    private val _orderState = MutableStateFlow(true)
    val orderState: StateFlow<Boolean> = _orderState

    override fun createInitialState(): OverviewState = Loading

    override fun intentToAction(intent: OverviewIntent): OverviewAction =
        when (intent) {
            is LoadOverview -> LoadGames
        }

    override fun handleAction(action: OverviewAction) {
        launchOnUI {
            when (action) {
                LoadGames -> {
                    combine(
                        orderState,
                        filterState,
                        repository.observeList()
                    ) { order, filter, list ->
                        list.apply {
                            if (order) sortedBy { it.name } else sortedByDescending { it.name }
                        }.apply {
                            if (filter.isActive) {
                                list.filter { item ->
                                    filter.isGameType.getOrDefault(
                                        item.type,
                                        false
                                    )
                                }
                            }
                        }
                    }.collect { list ->
                        setState {
                            Timber.d("state: ${list.size}")
                            if (list.isEmpty()) EmptyList else AllGames(list)
                        }
                    }
                }
                ApplyFilter -> {
                    _filterState.value = filterState.value.copy(isActive = true)
                }
                is ChangeFilter -> {
                    _filterState.value = action.filterState
                }
                ResetFilter -> {
                    _filterState.value = initialFilterState
                }
                OrderAction -> {
                    _orderState.value = !orderState.value
                }
            }
        }
    }
}