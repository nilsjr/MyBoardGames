/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data.database.transformers

import de.nilsdruyen.myboardgames.data.database.entities.BoardGameEntity
import de.nilsdruyen.myboardgames.data.models.BoardGame

interface BoardGameTransformer {

  fun toModel(entity: BoardGameEntity): BoardGame

  fun toEntity(model: BoardGame): BoardGameEntity
}