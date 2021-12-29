/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import de.nilsdruyen.myboardgames.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() :
  BaseViewModel<MainContract.MainAction, MainContract.MainState, MainContract.MainIntent>() {

  override fun createInitialState(): MainContract.MainState = MainContract.MainState.DoNothing

  override fun intentToAction(intent: MainContract.MainIntent): MainContract.MainAction =
    when (intent) {
      is MainContract.MainIntent.DoNothing -> MainContract.MainAction.DoNothing
    }

  override fun handleAction(action: MainContract.MainAction) {}
}