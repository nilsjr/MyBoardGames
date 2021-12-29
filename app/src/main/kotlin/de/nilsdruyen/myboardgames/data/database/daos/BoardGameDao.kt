/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.nilsdruyen.myboardgames.data.database.entities.BoardGameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BoardGameDao {

  @Query("SELECT * FROM board_game_table")
  fun getGames(): Flow<List<BoardGameEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addBoardGame(game: BoardGameEntity)
}