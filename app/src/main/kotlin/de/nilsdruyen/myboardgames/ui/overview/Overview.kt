/*
 * Created by Nils Druyen on 01-02-2022
 * Copyright © 2022 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.overview

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import de.nilsdruyen.myboardgames.data.models.BoardGame
import de.nilsdruyen.myboardgames.ui.components.LoadingMorty
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun Overview(
  viewModel: OverviewViewModel,
  showGame: (BoardGame) -> Unit,
  addGame: () -> Unit,
) {
  val state by viewModel.state.collectAsState()
  val filterState = viewModel.filterState.collectAsState()
  val coroutineScope = rememberCoroutineScope()

  val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

  val scrollBehavior = remember { TopAppBarDefaults.pinnedScrollBehavior() }

  LaunchedEffect(key1 = Unit) {
    viewModel.setAction(LoadGames)
  }

  ModalBottomSheetLayout(
    sheetState = bottomSheetState,
    sheetBackgroundColor = MaterialTheme.colorScheme.surfaceVariant,
    sheetElevation = 16.dp,
    sheetContent = {
      FilterDialog(state = filterState.value) {
        viewModel.setAction(ApplyFilter(it))
      }
    }
  ) {
    Scaffold(
      modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
      topBar = {
        SmallTopAppBar(
          title = { Text("Meine Spiele") },
          actions = {
            IconButton(onClick = {
              coroutineScope.launch {
                if (bottomSheetState.isVisible) {
                  bottomSheetState.hide()
                } else {
                  bottomSheetState.show()
                }
              }
            }) {
              BadgedBox(badge = { Badge { Text("1") } }) {
                Icon(
                  imageVector = Icons.Filled.FilterList,
                  contentDescription = "Filter Games"
                )
              }
            }
//            IconButton(onClick = { }) {
//              Icon(
//                imageVector = Icons.Filled.Search,
//                contentDescription = "Search Game"
//              )
//            }
          },
          scrollBehavior = scrollBehavior
        )
      },
      floatingActionButton = {
        FloatingActionButton(
          onClick = { addGame() },
        ) {
          Icon(Icons.Filled.Add, contentDescription = "Localized description")
        }
      },
      floatingActionButtonPosition = FabPosition.End,
//    scaffoldState = bottomSheetScaffoldState,
//    sheetShape = RoundedCornerShape(8.dp, topEnd = 8.dp),
//    sheetBackgroundColor = MaterialTheme.colorScheme.surfaceVariant,
//    sheetElevation = 16.dp,
//    sheetContent = {
//      FilterDialog(state = filterState.value) {
//        viewModel.setAction(ApplyFilter(it))
//      }
//    },
      content = {
        Crossfade(targetState = state) { state ->
          when (state) {
            is Loading -> {
              Box(modifier = Modifier.fillMaxSize()) {
                LoadingMorty(
                  Modifier
                    .fillMaxSize(fraction = 0.5f)
                    .align(Alignment.Center)
                )
              }
            }
            EmptyList -> {
              Box(modifier = Modifier.fillMaxSize()) {
                Text(
                  text = "Keine Spiele eingetragen!",
                  modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center)
                )
              }
            }
            is AllGames -> BoardGameList(state.games, showGame)
          }
        }
      }
    )
  }
}

@Composable
fun BoardGameList(
  games: List<BoardGame>,
  showGame: (BoardGame) -> Unit,
) {
  LazyColumn(
    contentPadding = PaddingValues(bottom = 4.dp)
  ) {
    items(games) { game ->
      GameItem(game, showGame)
    }
  }
}