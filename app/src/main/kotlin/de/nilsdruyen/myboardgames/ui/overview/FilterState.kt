/*
 * Created by Nils Druyen on 01-02-2022
 * Copyright © 2022 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.overview

import de.nilsdruyen.myboardgames.data.models.GameType

data class FilterState(
  val isActive: Boolean = false,
  val isGameType: Map<GameType, Boolean> = emptyMap(),
  val isManufacturer: Set<String> = emptySet(),
  val isPlayerCount: Int = 1,
  val isPlayTimeInMin: Int = 10,
)