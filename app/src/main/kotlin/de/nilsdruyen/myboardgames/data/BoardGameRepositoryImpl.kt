/*
 * Created by Nils Druyen on 01-27-2022
 * Copyright © 2022 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data

import de.nilsdruyen.myboardgames.data.database.BoardGameDbDatasource
import de.nilsdruyen.myboardgames.data.models.BoardGame
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BoardGameRepositoryImpl @Inject constructor(
    private val datasource: BoardGameDbDatasource
) : BoardGameRepository {

    override suspend fun observeList(): Flow<List<BoardGame>> {
        return datasource.observeGames().map { list ->
            list.sortedBy { it.name.lowercase() }
        }
    }

    override suspend fun get(id: String): BoardGame = datasource.getGame(id)

    override suspend fun add(boardGame: BoardGame) {
        datasource.addGame(boardGame)
    }

    override suspend fun delete(id: String) {
        datasource.deleteGame(id)
    }
}