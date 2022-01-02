/*
 * Created by Nils Druyen on 01-02-2022
 * Copyright © 2022 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import de.nilsdruyen.myboardgames.ui.add.AddGame
import de.nilsdruyen.myboardgames.ui.detail.GameDetail
import de.nilsdruyen.myboardgames.ui.overview.Overview
import de.nilsdruyen.myboardgames.utils.Constants.NavigationDuration

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MyBoardGames() {
  val navController = rememberAnimatedNavController()
  val systemUiController = rememberSystemUiController()
  val useDarkIcons = !isSystemInDarkTheme()
  val surfaceColor = MaterialTheme.colorScheme.surface

  SideEffect {
    systemUiController.setSystemBarsColor(
      color = surfaceColor,
      darkIcons = useDarkIcons
    )
  }

  ProvideWindowInsets {
    AnimatedNavHost(navController = navController, startDestination = Screen.Overview.path) {
      composable(
        route = Screen.Overview.path,
        enterTransition = {
          when (initialState.destination.route) {
            Screen.GameDetails.pathWithArgument -> slideIntoContainer(
              AnimatedContentScope.SlideDirection.Left,
              animationSpec = tween(NavigationDuration)
            )
            Screen.AddGame.path -> slideIntoContainer(
              AnimatedContentScope.SlideDirection.Down,
              animationSpec = tween(NavigationDuration),
              initialOffset = { (it * 0.2).toInt() }
            )
            else -> null
          }
        },
        exitTransition = {
          when (targetState.destination.route) {
            Screen.GameDetails.pathWithArgument -> slideOutOfContainer(
              AnimatedContentScope.SlideDirection.Left,
              animationSpec = tween(NavigationDuration)
            )
            Screen.AddGame.path -> slideOutOfContainer(
              AnimatedContentScope.SlideDirection.Up,
              animationSpec = tween(NavigationDuration),
              targetOffset = { (it * 0.2).toInt() }
            )
            else -> null
          }
        },
        popEnterTransition = {
          when (initialState.destination.route) {
            Screen.GameDetails.pathWithArgument -> slideIntoContainer(
              AnimatedContentScope.SlideDirection.Right,
              animationSpec = tween(NavigationDuration)
            )
            Screen.AddGame.path -> slideIntoContainer(
              AnimatedContentScope.SlideDirection.Down,
              animationSpec = tween(NavigationDuration),
            )
            else -> null
          }
        },
        popExitTransition = {
          when (targetState.destination.route) {
            Screen.GameDetails.pathWithArgument ->
              slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(NavigationDuration)
              )
            Screen.AddGame.path -> slideOutOfContainer(
              AnimatedContentScope.SlideDirection.Up,
              animationSpec = tween(NavigationDuration),
            )
            else -> null
          }
        },
      ) {
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
      composable(
        Screen.AddGame.path,
        enterTransition = {
          slideIntoContainer(
            AnimatedContentScope.SlideDirection.Up,
            animationSpec = tween(NavigationDuration)
          )
        },
        exitTransition = {
          slideOutOfContainer(
            AnimatedContentScope.SlideDirection.Down,
            animationSpec = tween(NavigationDuration)
          )
        },
        popEnterTransition = {
          slideIntoContainer(
            AnimatedContentScope.SlideDirection.Up,
            animationSpec = tween(NavigationDuration)
          )
        },
        popExitTransition = {
          slideOutOfContainer(
            AnimatedContentScope.SlideDirection.Down,
            animationSpec = tween(NavigationDuration)
          )
        },
      ) {
        AddGame(
          viewModel = hiltViewModel(),
          onBackPressed = {
            navController.navigateUp()
          }
        )
      }
      composable(
        route = Screen.GameDetails.pathWithArgument,
        arguments = listOf(navArgument(Screen.GameDetails.argument) {
          type = NavType.StringType
        }),
        enterTransition = {
          slideIntoContainer(
            AnimatedContentScope.SlideDirection.Left,
            animationSpec = tween(NavigationDuration)
          )
        },
        exitTransition = {
          slideOutOfContainer(
            AnimatedContentScope.SlideDirection.Left,
            animationSpec = tween(NavigationDuration)
          )
        },
        popEnterTransition = {
          slideIntoContainer(
            AnimatedContentScope.SlideDirection.Right,
            animationSpec = tween(NavigationDuration)
          )
        },
        popExitTransition = {
          slideOutOfContainer(
            AnimatedContentScope.SlideDirection.Right,
            animationSpec = tween(NavigationDuration)
          )
        },
      ) { backStackEntry ->
        val gameId =
          backStackEntry.arguments?.getString(Screen.GameDetails.argument) ?: return@composable

        GameDetail(gameId = gameId, viewModel = hiltViewModel()) {
          navController.navigateUp()
        }
      }
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