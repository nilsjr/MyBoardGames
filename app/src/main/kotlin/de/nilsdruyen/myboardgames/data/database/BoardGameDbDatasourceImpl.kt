/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data.database

import de.nilsdruyen.myboardgames.data.database.daos.BoardGameDao
import de.nilsdruyen.myboardgames.data.database.transformers.BoardGameTransformer
import de.nilsdruyen.myboardgames.data.models.BoardGame
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BoardGameDbDatasourceImpl @Inject constructor(
  private val boardGameDao: BoardGameDao,
  private val transformer: BoardGameTransformer,
) : BoardGameDbDatasource {

  override fun observeGames(): Flow<List<BoardGame>> =
    boardGameDao.getGames().map { it.map(transformer::toModel) }
}