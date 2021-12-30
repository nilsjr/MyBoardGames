/*
 * Created by Nils Druyen on 12-30-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.overview

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
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
import de.nilsdruyen.myboardgames.ui.components.MaterialCard

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
        title = { Text("Mein Spiele") },
        actions = {
          IconButton(onClick = { showFilter() }) {
            Icon(
              imageVector = Icons.Filled.FilterList,
              contentDescription = "Filter Games"
            )
          }
          IconButton(onClick = { addGame() }) {
            Icon(
              imageVector = Icons.Filled.Add,
              contentDescription = "Add Game"
            )
          }
        },
        scrollBehavior = scrollBehavior
      )
    },
    floatingActionButton = {
      SmallFloatingActionButton(
        onClick = { /* do something */ },
      ) {
        Icon(Icons.Filled.Add, contentDescription = "Localized description")
      }
    },
    floatingActionButtonPosition = FabPosition.End,
    content = { innerPadding ->
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
            BoardGameList(innerPadding, state.games, showGame)
          }
        }
      }
    }
  )
}

@Composable
fun BoardGameList(
  innerPadding: PaddingValues,
  games: List<BoardGame>,
  showGame: (BoardGame) -> Unit,
) {
  LazyColumn(
    contentPadding = innerPadding,
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    items(games) { game ->
      GameItem(game, showGame)
    }
  }
}

@Composable
fun GameItem(game: BoardGame, onGameClicked: (BoardGame) -> Unit) {
  MaterialCard(
    modifier = Modifier
      .clickable { onGameClicked(game) }
      .fillMaxWidth()
      .padding(horizontal = 8.dp),
//    backgroundColor = MaterialTheme.colorScheme.surface,
//    contentColor = MaterialTheme.colorScheme.primary,
    shape = RoundedCornerShape(16.dp),
    elevation = 6.dp
  ) {
    Column(modifier = Modifier.padding(4.dp)) {
      Text(
        text = game.name,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(8.dp)
      )
      Text(
        text = game.type.name,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(8.dp)
      )
    }
  }
}