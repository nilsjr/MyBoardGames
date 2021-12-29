package de.nilsdruyen.myboardgames.model.database

import de.nilsdruyen.myboardgames.model.BoardGame
import kotlinx.coroutines.flow.Flow

interface BoardGameDbDatasource {

  fun observeGames(): Flow<List<BoardGame>>
}