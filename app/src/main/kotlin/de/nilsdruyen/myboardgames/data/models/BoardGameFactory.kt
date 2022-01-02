/*
 * Created by Nils Druyen on 01-02-2022
 * Copyright © 2022 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data.models

import java.time.LocalDate
import java.util.UUID

object BoardGameFactory {

  fun buildGame(
    name: String,
    type: GameType = GameType.Board,
    players: Int = 2,
    playTimeInMin: Int = 10,
    score: Int = 1,
    manufacturer: String = "Irgendwer",
  ): BoardGame {
    return BoardGame(
      id = UUID.randomUUID().toString(),
      name = name,
      type = type,
      players = players,
      playTimeInMin = playTimeInMin,
      score = score,
      manufacturer = manufacturer,
      addedDate = LocalDate.now(),
      purchasedAt = LocalDate.now(),
    )
  }

  fun buildNewGame(name: String): BoardGame = buildGame(name = name)

  fun buildNewGame(
    name: String,
    type: GameType,
    players: Int,
    playTimeInMin: Int,
    score: Int,
    manufacturer: String,
  ): BoardGame = buildGame(
    name = name,
    type = type,
    players = players,
    playTimeInMin = playTimeInMin,
    score = score,
    manufacturer = manufacturer,
  )
}