/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.data.models

data class BoardGame(
  val id: String,
  val name: String,
  val rating: Int,
  val location: Location,
)