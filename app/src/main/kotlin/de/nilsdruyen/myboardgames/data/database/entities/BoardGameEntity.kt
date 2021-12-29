/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "board_game_table")
data class BoardGameEntity(
  @PrimaryKey val id: String,
  val name: String,
  val rating: Int,
  val location: LocationEntity,
  val ean: String,
)

@JsonClass(generateAdapter = true)
data class LocationEntity(
  val closet: String,
  val drawer: String,
)
