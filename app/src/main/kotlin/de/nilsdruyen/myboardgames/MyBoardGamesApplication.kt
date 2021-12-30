/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import de.nilsdruyen.myboardgames.utils.DebugTree
import timber.log.Timber
import timber.log.Timber.Forest.plant

@HiltAndroidApp
class MyBoardGamesApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    if (BuildConfig.DEBUG) {
      plant(DebugTree())
    }
    Timber.d("App started!")
  }
}