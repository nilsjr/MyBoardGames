/*
 * Created by Nils Druyen on 01-01-2022
 * Copyright © 2022 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.detail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.StarRate
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import de.nilsdruyen.myboardgames.data.models.BoardGame
import de.nilsdruyen.myboardgames.data.models.BoardGameFactory
import de.nilsdruyen.myboardgames.data.models.GameType
import de.nilsdruyen.myboardgames.data.models.toName
import de.nilsdruyen.myboardgames.ui.components.MaterialCard
import de.nilsdruyen.myboardgames.ui.theme.MyBoardGamesTheme

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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Detail(game: BoardGame, isInEditMode: Boolean) {
  ConstraintLayout(
    modifier = Modifier.fillMaxSize()
  ) {
    val (name, manufacturer, image, bgCenter, cards) = createRefs()
    OutlinedTextField(
      value = game.name,
      label = { Text("Name", color = MaterialTheme.colorScheme.secondary) },
      enabled = isInEditMode,
      onValueChange = {
//        game = game.copy(manufacturer = it)
      },
      modifier = Modifier
        .constrainAs(name) {
          width = Dimension.fillToConstraints
          top.linkTo(parent.top, margin = 16.dp)
          start.linkTo(parent.start, margin = 16.dp)
          end.linkTo(image.start, margin = 16.dp)
        },
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
      colors = outlineTextFieldStyle()
    )
    OutlinedTextField(
      value = game.manufacturer,
      label = { Text("Hersteller", color = MaterialTheme.colorScheme.secondary) },
      enabled = isInEditMode,
      onValueChange = {
//        game = game.copy(manufacturer = it)
      },
      modifier = Modifier
        .constrainAs(manufacturer) {
          width = Dimension.fillToConstraints
          start.linkTo(parent.start, margin = 16.dp)
          end.linkTo(image.start, margin = 16.dp)
          top.linkTo(name.bottom)
        },
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
      colors = outlineTextFieldStyle()
    )
    Box(
      modifier = Modifier
        .size(96.dp)
        .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))
        .constrainAs(image) {
          end.linkTo(parent.end, margin = 16.dp)
          top.linkTo(name.top)
          bottom.linkTo(manufacturer.bottom)
        }
    ) {
      Icon(
        imageVector = Icons.Filled.Casino,
        contentDescription = "Icon",
        modifier = Modifier
          .size(48.dp)
          .align(Alignment.Center),
        tint = MaterialTheme.colorScheme.onPrimary,
      )
    }
    val color = MaterialTheme.colorScheme.secondary
    val borderColor = MaterialTheme.colorScheme.secondaryContainer
    Canvas(modifier = Modifier
      .constrainAs(cards) {
        width = Dimension.fillToConstraints
        height = Dimension.value(80.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(bgCenter.top, margin = 0.dp)
      }
    ) {
      val canvasSize = size
      val canvasWidth = size.width
      val canvasHeight = size.height
      rotate(degrees = 25F) {
        drawRoundRect(
          color = color,
          topLeft = Offset(x = canvasWidth / 2.5f, y = canvasHeight / 4f),
          size = Size(canvasSize.width, 300f),
          cornerRadius = CornerRadius(
            x = 24.dp.toPx(),
            y = 24.dp.toPx()
          ),
        )
        drawRoundRect(
          color = borderColor,
          topLeft = Offset(x = canvasWidth / 2.5f, y = canvasHeight / 4f),
          size = Size(canvasSize.width, 300f),
          style = Stroke(
            width = 4.dp.toPx(),
          ),
          cornerRadius = CornerRadius(
            x = 24.dp.toPx(),
            y = 24.dp.toPx()
          ),
        )
      }
      rotate(degrees = 17F) {
        drawRoundRect(
          color = color,
          topLeft = Offset(x = canvasWidth / 4f, y = canvasHeight / 1.5f),
          size = Size(canvasSize.width, 300f),
          cornerRadius = CornerRadius(
            x = 24.dp.toPx(),
            y = 24.dp.toPx()
          ),
        )
        drawRoundRect(
          color = borderColor,
          topLeft = Offset(x = canvasWidth / 4f, y = canvasHeight / 1.5f),
          size = Size(canvasSize.width, 300f),
          style = Stroke(
            width = 4.dp.toPx(),
          ),
          cornerRadius = CornerRadius(
            x = 24.dp.toPx(),
            y = 24.dp.toPx()
          ),
        )
      }
      rotate(degrees = 10F) {
        drawRoundRect(
          color = color,
          topLeft = Offset(x = canvasWidth / 10f, y = canvasHeight / 1f),
          size = Size(canvasSize.width, 300f),
          cornerRadius = CornerRadius(
            x = 24.dp.toPx(),
            y = 24.dp.toPx()
          ),
        )
        drawRoundRect(
          color = borderColor,
          topLeft = Offset(x = canvasWidth / 10f, y = canvasHeight / 1f),
          size = Size(canvasSize.width, 300f),
          style = Stroke(
            width = 4.dp.toPx(),
          ),
          cornerRadius = CornerRadius(
            x = 24.dp.toPx(),
            y = 24.dp.toPx()
          ),
        )
      }
    }
    Surface(
      modifier = Modifier
        .constrainAs(bgCenter) {
          width = Dimension.fillToConstraints
          height = Dimension.wrapContent
          start.linkTo(parent.start)
          bottom.linkTo(parent.bottom)
          end.linkTo(parent.end)
        },
      color = MaterialTheme.colorScheme.secondary,
      shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
      elevation = 8.dp
    ) {
      val cells = listOf<@Composable ColumnScope.() -> Unit>(
        { GameTyp(game.type) },
        { Players(players = game.players) },
        { PlayTime(playTimeInMin = game.playTimeInMin) },
        { Score(score = game.score) },
      )
      LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier
          .fillMaxWidth()
      ) {
        itemsIndexed(cells) { index, item ->
          val shape = when (index) {
            0 -> RoundedCornerShape(topStart = 16.dp)
            1 -> RoundedCornerShape(topEnd = 16.dp)
            2 -> RoundedCornerShape(bottomStart = 16.dp)
            3 -> RoundedCornerShape(bottomEnd = 16.dp)
            else -> RectangleShape
          }
          MaterialCard(
            modifier = Modifier
              .fillMaxWidth()
              .aspectRatio(1f)
              .padding(4.dp),
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            shape = shape,
            content = {
              Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
              ) { item(this) }
            }
          )
        }
      }
    }
  }
}

@Composable
fun ColumnScope.Entry(label: String, value: String) {
  Text(
    text = label,
    modifier = Modifier.align(Alignment.CenterHorizontally),
    style = MaterialTheme.typography.bodyLarge,
  )
  Text(
    text = value,
    modifier = Modifier.align(Alignment.CenterHorizontally),
    style = MaterialTheme.typography.headlineMedium,
  )
}

@Composable
fun ColumnScope.GameTyp(type: GameType) {
  Entry(label = "Typ", value = type.toName())
}

@Composable
fun ColumnScope.Players(players: Int) {
  Icon(
    imageVector = Icons.Filled.Groups,
    contentDescription = "Players",
    modifier = Modifier.size(24.dp),
  )
  Text(
    text = players.toString(),
    style = MaterialTheme.typography.headlineMedium,
    modifier = Modifier
      .align(Alignment.CenterHorizontally)
  )
}

@Composable
fun ColumnScope.PlayTime(playTimeInMin: Int) {
  Icon(
    imageVector = Icons.Filled.Schedule,
    contentDescription = "Time to play",
    modifier = Modifier.size(24.dp),
  )
  Text(
    text = "$playTimeInMin min",
    style = MaterialTheme.typography.headlineMedium,
    modifier = Modifier
      .align(Alignment.CenterHorizontally)
  )
}

@Composable
fun ColumnScope.Score(score: Int) {
  Text(
    text = "Score",
    modifier = Modifier
      .align(Alignment.CenterHorizontally)
      .padding(bottom = 8.dp),
    style = MaterialTheme.typography.titleMedium,
  )
  if (score == 0) {
    Text(
      text = "Keine Angabe",
      style = MaterialTheme.typography.headlineMedium,
      modifier = Modifier
        .align(Alignment.CenterHorizontally)
    )
  } else {
    Row {
      repeat(score) {
        Icon(
          imageVector = Icons.Filled.StarRate,
          contentDescription = "Time to play",
          modifier = Modifier.size(24.dp)
        )
      }
    }
  }
}

@Composable
fun outlineTextFieldStyle(): TextFieldColors =
  TextFieldDefaults.outlinedTextFieldColors(
    cursorColor = MaterialTheme.colorScheme.onSurfaceVariant,
    textColor = MaterialTheme.colorScheme.onSurfaceVariant,
    backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
    placeholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
    focusedBorderColor = MaterialTheme.colorScheme.primary,
  )

@Preview(
  showBackground = true,
  backgroundColor = 0xFF1a1c19
)
@Composable
fun PreviewDetail() {
  val game = BoardGameFactory.buildGame(
    name = "test"
  )
  MyBoardGamesTheme(darkTheme = true) {
    Detail(game, false)
  }
}