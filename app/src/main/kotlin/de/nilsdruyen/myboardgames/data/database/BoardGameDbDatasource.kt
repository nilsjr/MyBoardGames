/*
 * Created by Nils Druyen on 01-27-2022
 * Copyright © 2022 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data.database

import de.nilsdruyen.myboardgames.data.models.BoardGame
import kotlinx.coroutines.flow.Flow

interface BoardGameDbDatasource {

  suspend fun observeGames(): Flow<List<BoardGame>>

  suspend fun getGame(id: String): BoardGame

  suspend fun addGame(boardGame: BoardGame)

  suspend fun deleteGame(id: String)
}