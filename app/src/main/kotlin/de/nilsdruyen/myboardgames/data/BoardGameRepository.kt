/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data

import androidx.annotation.WorkerThread
import de.nilsdruyen.myboardgames.data.models.BoardGame
import kotlinx.coroutines.flow.Flow

interface BoardGameRepository {

  @WorkerThread
  fun observeList(): Flow<List<BoardGame>>

  suspend fun get(id: String): BoardGame

  suspend fun add(boardGame: BoardGame)

  suspend fun delete(id: String)
}