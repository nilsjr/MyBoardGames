/*
 * Created by Nils Druyen on 12-31-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.overview

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import de.nilsdruyen.myboardgames.data.models.BoardGame
import de.nilsdruyen.myboardgames.ui.components.LoadingMorty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Overview(
  viewModel: OverviewViewModel,
  showGame: (BoardGame) -> Unit,
  addGame: () -> Unit,
  showFilter: () -> Unit
) {
  val state by viewModel.state.collectAsState()
  val scrollBehavior = remember { TopAppBarDefaults.pinnedScrollBehavior() }

  LaunchedEffect(key1 = Unit) {
    viewModel.setAction(OverviewContract.OverviewAction.LoadGames)
  }

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      SmallTopAppBar(
        title = { Text("Meine Spiele") },
        actions = {
          IconButton(onClick = { showFilter() }) {
            Icon(
              imageVector = Icons.Filled.FilterList,
              contentDescription = "Filter Games"
            )
          }
          IconButton(onClick = { addGame() }) {
            Icon(
              imageVector = Icons.Filled.Search,
              contentDescription = "Search Game"
            )
          }
        },
        scrollBehavior = scrollBehavior
      )
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = {
          addGame()
        },
      ) {
        Icon(Icons.Filled.Add, contentDescription = "Localized description")
      }
    },
    floatingActionButtonPosition = FabPosition.End,
    content = {
      Crossfade(targetState = state) { state ->
        when (state) {
          is OverviewContract.OverviewState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
              LoadingMorty(
                Modifier
                  .fillMaxSize(fraction = 0.5f)
                  .align(Alignment.Center)
              )
            }
          }
          OverviewContract.OverviewState.EmptyList -> {
            Box(modifier = Modifier.fillMaxSize()) {
              Text(
                text = "Keine Spiele eingetragen!",
                modifier = Modifier
                  .padding(16.dp)
                  .align(Alignment.Center)
              )
            }
          }
          is OverviewContract.OverviewState.AllGames -> {
            BoardGameList(state.games, showGame)
          }
        }
      }
    }
  )
}

@Composable
fun BoardGameList(
  games: List<BoardGame>,
  showGame: (BoardGame) -> Unit,
) {
  LazyColumn(
    contentPadding = PaddingValues(vertical = 12.dp)
  ) {
    items(games) { game ->
      GameItem(game, showGame)
    }
  }
}