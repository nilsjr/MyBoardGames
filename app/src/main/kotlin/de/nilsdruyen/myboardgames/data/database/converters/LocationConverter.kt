/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data.database.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import de.nilsdruyen.myboardgames.data.database.entities.LocationEntity

@ProvidedTypeConverter
class LocationConverter(moshi: Moshi) {

  private val adapter: JsonAdapter<LocationEntity> = moshi.adapter(LocationEntity::class.java)

  @TypeConverter
  fun toLocation(value: String): LocationEntity =
    adapter.fromJson(value) ?: throw NullPointerException("decoding not possible")

  @TypeConverter
  fun fromLocation(value: LocationEntity): String = adapter.toJson(value)
}