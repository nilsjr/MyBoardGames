package de.nilsdruyen.myboardgames.model.database

import de.nilsdruyen.myboardgames.model.BoardGame
import de.nilsdruyen.myboardgames.model.database.daos.BoardGameDao
import de.nilsdruyen.myboardgames.model.database.transformers.BoardGameTransformer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BoardGameDbDatasourceImpl @Inject constructor(
  private val boardGameDao: BoardGameDao,
  private val transformer: BoardGameTransformer,
) : BoardGameDbDatasource {

  override fun observeGames(): Flow<List<BoardGame>> =
    boardGameDao.getCharacter().map { it.map(transformer::toModel) }
}