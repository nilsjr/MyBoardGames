/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import de.nilsdruyen.myboardgames.data.models.BoardGame
import de.nilsdruyen.myboardgames.ui.theme.MyBoardGamesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      MyBoardGamesTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          Overview()
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Overview() {
  val viewModel: MainViewModel = viewModel()
  val state by viewModel.overviewState

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
        is BoardGameState.Loading -> {
          Text("loading")
        }
        is BoardGameState.Overview -> {
          val games = (state as BoardGameState.Overview).games
          if (games.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
              Text(
                text = "Keine Spiele eingetragen!",
                modifier = Modifier
                  .padding(16.dp)
                  .align(Alignment.Center)
              )
            }
          } else {
            BoardGameList(innerPadding = innerPadding, (state as BoardGameState.Overview).games)
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
) {
  LazyColumn(
    contentPadding = innerPadding,
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    items(count = games.size) {
      Text(
        text = games[it].name,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp)
      )
    }
  }
}

@Composable
fun Greeting(name: String) {
  Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  MyBoardGamesTheme {
    Greeting("Android")
  }
}