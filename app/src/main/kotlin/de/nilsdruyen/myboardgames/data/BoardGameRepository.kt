/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data

import de.nilsdruyen.myboardgames.data.models.BoardGame
import kotlinx.coroutines.flow.Flow

interface BoardGameRepository {

  fun observeList(): Flow<List<BoardGame>>

  suspend fun add(boardGame: BoardGame)
}