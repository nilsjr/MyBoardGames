/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import de.nilsdruyen.myboardgames.ui.add.AddGame
import de.nilsdruyen.myboardgames.ui.overview.Overview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBoardGames() {
  val navController = rememberNavController()

  ProvideWindowInsets {
    NavHost(navController = navController, startDestination = Screen.Overview.path) {
      composable(Screen.Overview.path) {
        Overview(
          viewModel = hiltViewModel(),
          showGame = {
            navController.navigate("${Screen.GameDetails.path}/${it.id}")
          },
          addGame = {
            navController.navigate(Screen.AddGame.path)
          }
        )
      }
      composable(Screen.AddGame.path) {
        AddGame(
          viewModel = hiltViewModel(),
          onBackPressed = {
            navController.navigateUp()
          }
        )
      }
//      composable(
//        route = Screen.GameDetails.pathWithArgument,
//        arguments = listOf(navArgument(Screen.GameDetails.argument) { type = NavType.StringType })
//      ) { backStackEntry ->
//        val gameId =
//          backStackEntry.arguments?.getLong(Screen.GameDetails.argument) ?: return@composable
//
//        PosterDetails(posterId = posterId, viewModel = hiltViewModel()) {
//          navController.navigateUp()
//        }
//      }
    }
  }
}

sealed class Screen(val path: String) {

  object Overview : Screen("Overview")

  object GameDetails : Screen("GameDetails") {

    const val pathWithArgument: String = "GameDetails/{id}"
    const val argument: String = "id"
  }

  object AddGame : Screen("AddGame")
}