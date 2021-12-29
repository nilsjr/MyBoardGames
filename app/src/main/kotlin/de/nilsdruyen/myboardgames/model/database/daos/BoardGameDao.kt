package de.nilsdruyen.myboardgames.model.database.daos

import androidx.room.Dao
import androidx.room.Query
import de.nilsdruyen.myboardgames.model.database.entities.BoardGameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BoardGameDao {

  @Query("SELECT * FROM board_game_table")
  fun getCharacter(): Flow<List<BoardGameEntity>>
}