/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data.database.converters

import androidx.room.TypeConverter
import de.nilsdruyen.myboardgames.data.models.GameType

object GameTypeConverter {

  @TypeConverter
  fun toType(value: String) = enumValueOf<GameType>(value)

  @TypeConverter
  fun fromType(value: GameType) = value.name
}