package de.nilsdruyen.myboardgames.model

import kotlinx.coroutines.flow.Flow

interface BoardGameRepository {

  fun observeList(): Flow<List<BoardGame>>

  suspend fun add(boardGame: BoardGame)
}