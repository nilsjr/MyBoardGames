/*
 * Created by Nils Druyen on 01-02-2022
 * Copyright © 2022 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data.models

enum class GameType {
  Dice, Board, Card, Escape, Puzzle,
}

fun GameType.toName(): String = when (this) {
  GameType.Dice -> "Würfelspiel"
  GameType.Board -> "Brettspiel"
  GameType.Card -> "Kartenspiel"
  GameType.Escape -> "Escape Spiel"
  GameType.Puzzle -> "Puzzle"
}