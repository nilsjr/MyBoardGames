/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data.database.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

@ProvidedTypeConverter
class StringListConverter(moshi: Moshi) {

  private val type = Types.newParameterizedType(List::class.java, String::class.java)
  private val adapter = moshi.adapter<List<String>>(type)

  @TypeConverter
  fun toList(value: String): List<String> = adapter.fromJson(value) ?: emptyList()

  @TypeConverter
  fun fromList(value: List<String>): String = adapter.toJson(value)
}