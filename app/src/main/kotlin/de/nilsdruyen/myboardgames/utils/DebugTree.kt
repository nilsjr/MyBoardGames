/*
 * Created by Nils Druyen on 12-30-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.utils

import timber.log.Timber

class DebugTree : Timber.DebugTree() {

  override fun createStackElementTag(element: StackTraceElement): String =
    String.format(
      null,
      "%s %s:%s",
      super.createStackElementTag(element),
      element.methodName,
      element.lineNumber
    )
}