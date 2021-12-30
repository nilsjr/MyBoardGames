/*
 * Created by Nils Druyen on 12-30-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import de.nilsdruyen.myboardgames.data.models.BoardGame

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetail(gameId: String, viewModel: GameDetailViewModel, onBackPressed: () -> Unit) {
  val state by viewModel.state.collectAsState()

  LaunchedEffect(key1 = gameId) {
    viewModel.setAction(GameDetailContract.GameDetailAction.LoadGame(gameId))
  }

  Scaffold(
    topBar = {
      SmallTopAppBar(
        title = { Text("Detail") },
        navigationIcon = {
          IconButton(onClick = { onBackPressed() }) {
            Icon(
              imageVector = Icons.Filled.ArrowBack,
              contentDescription = "Backpress"
            )
          }
        }
      )
    }
  ) {
    if (state is GameDetailContract.GameDetailState.Details) {
      Detail((state as GameDetailContract.GameDetailState.Details).game)
    } else {
      Loading()
    }
  }
}

@Composable
fun Loading() {
  Box(modifier = Modifier.fillMaxSize()) {
    Text(text = "Lädt...", modifier = Modifier.align(Alignment.Center))
  }
}

@Composable
fun Detail(game: BoardGame) {
  ConstraintLayout(
    modifier = Modifier.fillMaxSize()
  ) {
    val (name, type) = createRefs()

    Text(
      text = game.name,
      modifier = Modifier
        .constrainAs(name) {
          top.linkTo(parent.top)
          start.linkTo(parent.start)
          end.linkTo(parent.end)
        }
        .padding(16.dp)
    )
    Text(
      text = game.type.name,
      modifier = Modifier
        .constrainAs(type) {
          top.linkTo(name.bottom)
          start.linkTo(parent.start)
          end.linkTo(parent.end)
        }
        .padding(16.dp)
    )
  }
}