/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data.database.converters

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

object DateConverter {

  @TypeConverter
  fun fromTimestamp(value: Long?): LocalDate? =
    value?.let { Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalDate() }

  @TypeConverter
  fun dateToTimestamp(date: LocalDate?): Long? =
    date?.atStartOfDay(ZoneId.systemDefault())?.toEpochSecond()
}