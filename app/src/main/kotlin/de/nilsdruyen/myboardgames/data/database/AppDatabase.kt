/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.squareup.moshi.Moshi
import de.nilsdruyen.myboardgames.data.database.converters.DateConverter
import de.nilsdruyen.myboardgames.data.database.converters.GameTypeConverter
import de.nilsdruyen.myboardgames.data.database.converters.StringListConverter
import de.nilsdruyen.myboardgames.data.database.daos.BoardGameDao
import de.nilsdruyen.myboardgames.data.database.entities.BoardGameEntity

@Database(entities = [BoardGameEntity::class], version = 1)
@TypeConverters(DateConverter::class, GameTypeConverter::class, StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {

  abstract fun boardGameDao(): BoardGameDao

  companion object {

    private var instance: AppDatabase? = null

    @JvmStatic
    @Synchronized
    fun getInstance(applicationContext: Context, moshi: Moshi): AppDatabase {
      if (instance == null) {
        instance =
          Room.databaseBuilder(applicationContext, AppDatabase::class.java, "my_board_games.db")
            .addTypeConverter(StringListConverter(moshi))
            .fallbackToDestructiveMigration()
            .build()
      }
      return instance!!
    }
  }
}