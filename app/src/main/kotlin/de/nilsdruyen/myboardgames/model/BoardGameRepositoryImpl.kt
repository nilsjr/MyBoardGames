package de.nilsdruyen.myboardgames.model

import de.nilsdruyen.myboardgames.model.database.BoardGameDbDatasource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BoardGameRepositoryImpl @Inject constructor(
  private val datasource: BoardGameDbDatasource
) : BoardGameRepository {

  override fun observeList(): Flow<List<BoardGame>> = datasource.observeGames()

  override suspend fun add(boardGame: BoardGame) {
    TODO("Not yet implemented")
  }
}