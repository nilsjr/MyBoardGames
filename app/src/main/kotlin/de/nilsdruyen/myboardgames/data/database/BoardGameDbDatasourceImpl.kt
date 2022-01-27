/*
 * Created by Nils Druyen on 01-27-2022
 * Copyright © 2022 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data.database

import de.nilsdruyen.myboardgames.annotations.IoDispatcher
import de.nilsdruyen.myboardgames.data.database.daos.BoardGameDao
import de.nilsdruyen.myboardgames.data.database.transformers.BoardGameTransformer
import de.nilsdruyen.myboardgames.data.models.BoardGame
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BoardGameDbDatasourceImpl @Inject constructor(
    private val boardGameDao: BoardGameDao,
    private val transformer: BoardGameTransformer,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : BoardGameDbDatasource {

    override suspend fun observeGames(): Flow<List<BoardGame>> = withContext(dispatcher) {
        boardGameDao.getGames().map { it.map(transformer::toModel) }
    }

    override suspend fun getGame(id: String): BoardGame = withContext(dispatcher) {
        transformer.toModel(boardGameDao.getGame(id))
    }

    override suspend fun addGame(boardGame: BoardGame) = withContext(dispatcher) {
        boardGameDao.addBoardGame(transformer.toEntity(boardGame))
    }

    override suspend fun deleteGame(id: String) = withContext(dispatcher) {
        boardGameDao.deleteGame(id)
    }
}