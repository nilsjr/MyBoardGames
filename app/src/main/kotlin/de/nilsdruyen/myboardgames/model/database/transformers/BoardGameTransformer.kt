package de.nilsdruyen.myboardgames.model.database.transformers

import de.nilsdruyen.myboardgames.model.BoardGame
import de.nilsdruyen.myboardgames.model.database.entities.BoardGameEntity

interface BoardGameTransformer {

  fun toModel(entity: BoardGameEntity): BoardGame

  fun toEntity(model: BoardGame): BoardGameEntity
}