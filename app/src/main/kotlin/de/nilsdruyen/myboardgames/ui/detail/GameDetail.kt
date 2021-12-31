/*
 * Created by Nils Druyen on 12-31-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import de.nilsdruyen.myboardgames.data.models.BoardGame
import de.nilsdruyen.myboardgames.data.models.BoardGameFactory
import de.nilsdruyen.myboardgames.ui.theme.MyBoardGamesTheme
import de.nilsdruyen.myboardgames.ui.theme.toOutlineTextFieldStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetail(gameId: String, viewModel: GameDetailViewModel, onBackPressed: () -> Unit) {
  val state by viewModel.state.collectAsState()
  if (state is GameDeleted) onBackPressed()

  val openDeleteDialog = remember { mutableStateOf(false) }
  var isInEditMode by remember { mutableStateOf(false) }

  LaunchedEffect(key1 = gameId) {
    viewModel.setAction(LoadGame(gameId))
  }

  if (openDeleteDialog.value) {
    DeleteDialog(openDeleteDialog) {
      viewModel.setAction(DeleteGame(gameId))
    }
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
        },
        actions = {
          if (isInEditMode) {
            IconButton(onClick = {
              isInEditMode = true
            }) {
              Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Save changes"
              )
            }
          } else {
            IconButton(onClick = {
              isInEditMode = true
            }) {
              Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit Game"
              )
            }
            IconButton(onClick = {
              openDeleteDialog.value = true
            }) {
              Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Add Game"
              )
            }
          }
        },
      )
    }
  ) {
    if (state is Details) {
      Detail((state as Details).game, isInEditMode)
    } else {
      Loading()
    }
  }
}

@Composable
fun DeleteDialog(openDeleteDialog: MutableState<Boolean>, acceptDelete: () -> Unit) {
  AlertDialog(
    onDismissRequest = {
      openDeleteDialog.value = false
    },
    title = { Text("Löschen") },
    text = { Text("Möchten Sie das Spiel wirklich löschen?") },
    confirmButton = {
      TextButton(onClick = {
        acceptDelete()
        openDeleteDialog.value = false
      }) {
        Text("Löschen")
      }
    },
    dismissButton = {
      TextButton(onClick = {
        openDeleteDialog.value = false
      }) {
        Text("Nein")
      }
    }
  )
}

@Composable
fun Loading() {
  Box(modifier = Modifier.fillMaxSize()) {
    CircularProgressIndicator()
  }
}

@Composable
fun Detail(game: BoardGame, isInEditMode: Boolean) {
  ConstraintLayout(
    modifier = Modifier.fillMaxSize()
  ) {
    val (name, type) = createRefs()

    OutlinedTextField(
      value = game.manufacturer,
      label = { Text("Name") },
      enabled = isInEditMode,
      onValueChange = {
//        game = game.copy(manufacturer = it)
      },
      modifier = Modifier
        .constrainAs(name) {
          width = Dimension.fillToConstraints
          top.linkTo(parent.top)
          start.linkTo(parent.start)
          end.linkTo(parent.end)
        }
        .padding(16.dp),
      keyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.Words,
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
      ),
      keyboardActions = KeyboardActions(
        onDone = {
//          keyboardController?.hide()
        }
      ),
      singleLine = true,
      colors = MaterialTheme.colorScheme.toOutlineTextFieldStyle()
    )

//    Text(
//      text = game.name,
//      modifier = Modifier
//        .constrainAs(name) {
//          top.linkTo(parent.top)
//          start.linkTo(parent.start)
//          end.linkTo(parent.end)
//        }
//        .padding(16.dp)
//    )
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

@Preview
@Composable
fun PreviewDetail() {
  val game = BoardGameFactory.buildNewGame("test")
  MyBoardGamesTheme(darkTheme = true) {
    Detail(game, false)
  }
}

@Preview
@Composable
fun PreviewDetailEdit() {
  val game = BoardGameFactory.buildNewGame("test")
  MyBoardGamesTheme(darkTheme = true) {
    Detail(game, true)
  }
}