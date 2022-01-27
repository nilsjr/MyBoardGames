/*
 * Created by Nils Druyen on 01-27-2022
 * Copyright © 2022 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data

import de.nilsdruyen.myboardgames.data.models.BoardGame
import kotlinx.coroutines.flow.Flow

interface BoardGameRepository {

  suspend fun observeList(): Flow<List<BoardGame>>

  suspend fun get(id: String): BoardGame

  suspend fun add(boardGame: BoardGame)

  suspend fun delete(id: String)
}