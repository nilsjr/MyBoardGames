/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import de.nilsdruyen.myboardgames.ui.theme.MyBoardGamesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      MyBoardGamesTheme {
        MyBoardGames()
      }
    }
  }
}