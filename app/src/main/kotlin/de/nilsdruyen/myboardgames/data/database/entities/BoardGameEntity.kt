/*
 * Created by Nils Druyen on 12-31-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import de.nilsdruyen.myboardgames.data.models.GameType
import java.time.LocalDate

@Entity(tableName = "board_game_table", indices = [Index(value = ["name"], unique = true)])
data class BoardGameEntity(
  @PrimaryKey val id: String,
  val name: String,
  val type: GameType,
  val players: Int,
  val playTimeInMin: Int,
  val score: Int,
  val manufacturer: String,
  val addedDate: LocalDate,
  val purchasedAt: LocalDate,
  val linkedGameIds: List<String>,
  val ean: String,
  val played: Int,
  val notes: String,
)