/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data

import de.nilsdruyen.myboardgames.data.database.BoardGameDbDatasource
import de.nilsdruyen.myboardgames.data.models.BoardGame
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