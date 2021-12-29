/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data.models

import java.time.LocalDate

data class BoardGame(
  val id: String,
  val name: String,
  val type: GameType,
  val players: Int,
  val playTimeInMin: Int,
  val score: Int,
  val manufacturer: String,
  val addedDate: LocalDate,
  val purchasedAt: LocalDate,
  val linkedGameIds: List<String> = emptyList(),
  val ean: String = "",
  val played: Int = 0,
  val notes: String = "",
)