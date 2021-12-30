/*
 * Created by Nils Druyen on 12-30-2021
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
}