/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data.database.transformers

import de.nilsdruyen.myboardgames.data.database.entities.BoardGameEntity
import de.nilsdruyen.myboardgames.data.models.BoardGame
import javax.inject.Inject

class BoardGameTransformerImpl @Inject constructor() : BoardGameTransformer {

  override fun toModel(entity: BoardGameEntity): BoardGame = with(entity) {
    BoardGame(
      id = id,
      name = name,
      type = type,
      players = players,
      playTimeInMin = playTimeInMin,
      score = score,
      manufacturer = manufacturer,
      addedDate = addedDate,
      purchasedAt = purchasedAt,
      linkedGameIds = linkedGameIds,
      ean = ean,
      played = played,
      notes = notes
    )
  }

  override fun toEntity(model: BoardGame): BoardGameEntity = with(model) {
    BoardGameEntity(
      id = id,
      name = name,
      type = type,
      players = players,
      playTimeInMin = playTimeInMin,
      score = score,
      manufacturer = manufacturer,
      addedDate = addedDate,
      purchasedAt = purchasedAt,
      linkedGameIds = linkedGameIds,
      ean = ean,
      played = played,
      notes = notes
    )
  }
}