/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data.database.transformers

import de.nilsdruyen.myboardgames.data.database.entities.BoardGameEntity
import de.nilsdruyen.myboardgames.data.database.entities.LocationEntity
import de.nilsdruyen.myboardgames.data.models.BoardGame
import de.nilsdruyen.myboardgames.data.models.Location
import javax.inject.Inject

class BoardGameTransformerImpl @Inject constructor() : BoardGameTransformer {

  override fun toModel(entity: BoardGameEntity): BoardGame = with(entity) {
    BoardGame(
      id = id,
      name = name,
      rating = rating,
      location = Location(location.closet, location.drawer)
    )
  }

  override fun toEntity(model: BoardGame): BoardGameEntity = with(model) {
    BoardGameEntity(
      id = id,
      name = name,
      rating = rating,
      location = LocationEntity(location.closet, location.drawer),
      ean = ""
    )
  }
}