package de.nilsdruyen.myboardgames.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.squareup.moshi.Moshi
import de.nilsdruyen.myboardgames.model.database.converters.LocationConverter
import de.nilsdruyen.myboardgames.model.database.daos.BoardGameDao
import de.nilsdruyen.myboardgames.model.database.entities.BoardGameEntity

@Database(entities = [BoardGameEntity::class], version = 1)
@TypeConverters(LocationConverter::class)
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
            .addTypeConverter(LocationConverter(moshi))
            .fallbackToDestructiveMigration()
            .build()
      }
      return instance!!
    }
  }
}