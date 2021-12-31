/*
 * Created by Nils Druyen on 12-31-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.theme

import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

@Composable
fun ColorScheme.toOutlineTextFieldStyle(): TextFieldColors =
  TextFieldDefaults.outlinedTextFieldColors(
    cursorColor = this.onSurfaceVariant,
    textColor = this.onSurfaceVariant,
    backgroundColor = this.surfaceVariant,
    placeholderColor = this.onSurfaceVariant,
    focusedBorderColor = this.secondary
  )