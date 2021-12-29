package de.nilsdruyen.myboardgames.model

data class BoardGame(
  val id: String,
  val name: String,
  val rating: Int,
  val location: Location,
)

data class Location(
  val closet: String,
  val drawer: String,
)