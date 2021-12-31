/*
 * Created by Nils Druyen on 12-31-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data.models

import java.time.LocalDate
import java.util.UUID

object BoardGameFactory {

  fun buildNewGame(name: String): BoardGame {
    return BoardGame(
      id = UUID.randomUUID().toString(),
      name = name,
      type = GameType.Board,
      players = 0,
      playTimeInMin = 0,
      score = 0,
      manufacturer = "",
      addedDate = LocalDate.now(),
      purchasedAt = LocalDate.now(),
    )
  }

  fun buildNewGame(
    name: String,
    type: GameType,
    players: Int,
    playTimeInMin: Int,
    score: Int,
    manufacturer: String,
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
}