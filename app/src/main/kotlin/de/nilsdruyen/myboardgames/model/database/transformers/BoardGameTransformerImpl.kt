package de.nilsdruyen.myboardgames.model.database.transformers

import de.nilsdruyen.myboardgames.model.BoardGame
import de.nilsdruyen.myboardgames.model.Location
import de.nilsdruyen.myboardgames.model.database.entities.BoardGameEntity
import de.nilsdruyen.myboardgames.model.database.entities.LocationEntity
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