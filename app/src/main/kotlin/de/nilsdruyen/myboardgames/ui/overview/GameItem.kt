/*
 * Created by Nils Druyen on 01-24-2022
 * Copyright © 2022 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.overview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import de.nilsdruyen.myboardgames.data.models.BoardGame
import de.nilsdruyen.myboardgames.data.models.BoardGameFactory
import de.nilsdruyen.myboardgames.data.models.GameType
import de.nilsdruyen.myboardgames.ui.components.MaterialCard
import de.nilsdruyen.myboardgames.ui.theme.MyBoardGamesTheme

@Composable
fun GameItem(game: BoardGame, onGameClicked: (BoardGame) -> Unit) {
  MaterialCard(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp),
    backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
    shape = RoundedCornerShape(8.dp),
    elevation = 4.dp
  ) {
    ConstraintLayout(
      modifier = Modifier
        .clickable { onGameClicked(game) }
        .padding(8.dp)
    ) {
      val (name, type, manufacturer, players, playTime, score) = createRefs()

      Text(
        text = game.name,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier
          .constrainAs(name) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
          }
      )
      Text(
        text = game.type.name,
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier
          .constrainAs(type) {
            start.linkTo(parent.start)
            top.linkTo(name.bottom, margin = 4.dp)
          }
      )
      Text(
        text = game.manufacturer,
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier
          .constrainAs(manufacturer) {
            width = Dimension.fillToConstraints
            start.linkTo(parent.start)
            bottom.linkTo(parent.bottom)
            end.linkTo(players.start, margin = 8.dp)
            top.linkTo(type.bottom, margin = 4.dp)
          }
      )
      Players(players = game.players, modifier = Modifier.constrainAs(players) {
        bottom.linkTo(parent.bottom)
        end.linkTo(playTime.start, margin = 16.dp)
      })
      Score(score = game.players, modifier = Modifier.constrainAs(score) {
        top.linkTo(parent.top)
        end.linkTo(parent.end)
      })
      PlayTime(playTime = game.players, modifier = Modifier.constrainAs(playTime) {
        end.linkTo(parent.end)
        bottom.linkTo(parent.bottom)
      })
    }
  }
}

@Composable
fun Players(players: Int, modifier: Modifier = Modifier) {
  Row(
    modifier = modifier
  ) {
    Text(
      text = players.toString(),
      style = MaterialTheme.typography.labelLarge,
      modifier = Modifier
        .padding(horizontal = 8.dp)
        .align(Alignment.CenterVertically)
    )
    Icon(
      imageVector = Icons.Filled.Groups,
      contentDescription = "Players"
    )
  }
}

@Composable
fun PlayTime(playTime: Int, modifier: Modifier = Modifier) {
  Row(
    modifier = modifier
  ) {
    Icon(
      imageVector = Icons.Filled.Schedule,
      contentDescription = "Time to play"
    )
    Text(
      text = "$playTime min",
      style = MaterialTheme.typography.labelLarge,
      modifier = Modifier
        .padding(start = 4.dp)
        .align(Alignment.CenterVertically)
    )
  }
}

@Composable
fun Score(score: Int, modifier: Modifier = Modifier) {
  Row(
    modifier = modifier
  ) {
    repeat(score) {
      Icon(
        imageVector = Icons.Filled.StarRate,
        contentDescription = "Time to play",
        modifier = Modifier.size(14.dp)
      )
    }
  }
}

@Preview
@Composable
fun PreviewItem() {
  MyBoardGamesTheme(darkTheme = true) {
    val game = BoardGameFactory.buildNewGame(
      name = "Test Spiel",
      type = GameType.Card,
      players = 2,
      playTimeInMin = 20,
      score = 3,
      manufacturer = "Kosmos"
    )
    GameItem(game = game, onGameClicked = {})
  }
}