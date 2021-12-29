/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.overview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import de.nilsdruyen.myboardgames.data.models.BoardGame

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Overview(viewModel: OverviewViewModel, showGame: (BoardGame) -> Unit) {
  val state by viewModel.state.collectAsState()
  val scrollBehavior = remember { TopAppBarDefaults.pinnedScrollBehavior() }

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      SmallTopAppBar(
        title = { Text("Mein Spiele") },
        navigationIcon = {
          IconButton(onClick = { /* doSomething() */ }) {
            Icon(
              imageVector = Icons.Filled.Menu,
              contentDescription = "Localized description"
            )
          }
        },
        actions = {
          IconButton(onClick = { /* doSomething() */ }) {
            Icon(
              imageVector = Icons.Filled.Favorite,
              contentDescription = "Localized description"
            )
          }
        },
        scrollBehavior = scrollBehavior
      )
    },
    content = { innerPadding ->
      when (state) {
        is OverviewContract.OverviewState.Loading -> {
          Text("loading")
        }
        is OverviewContract.OverviewState.AllGames -> {
          BoardGameList(
            innerPadding = innerPadding,
            (state as OverviewContract.OverviewState.AllGames).games,
            showGame
          )
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
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    items(games) { game ->
      Text(
        text = game.name,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
          .fillMaxWidth()
          .clickable { showGame(game) }
          .padding(horizontal = 16.dp)
      )
    }
  }
}