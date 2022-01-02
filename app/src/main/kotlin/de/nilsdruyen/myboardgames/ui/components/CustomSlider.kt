/*
 * Created by Nils Druyen on 01-02-2022
 * Copyright © 2022 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.material.SliderColors
import androidx.compose.material.SliderDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

data class SliderItem(
  val value: Int,
  val text: String,
)

@Composable
fun CustomSlider(
  values: List<SliderItem>,
  value: Int,
  onValueChange: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  val (sliderValue, setSliderValue) = remember { mutableStateOf(value) }
  val drawPadding = with(LocalDensity.current) { 10.dp.toPx() }
  val textTopMargin = with(LocalDensity.current) { 16.dp.toPx() }
  val textSize = with(LocalDensity.current) { 16.dp.toPx() }
  val lineHeightDp = 10.dp
  val lineHeightPx = with(LocalDensity.current) { lineHeightDp.toPx() }
  val canvasHeight = 50.dp
  val textPaint = android.graphics.Paint().apply {
    color = if (isSystemInDarkTheme()) 0xffffffff.toInt() else 0xffff47586B.toInt()
    textAlign = android.graphics.Paint.Align.CENTER
    this.textSize = textSize
  }
  val min = values.minByOrNull { it.value }?.value?.toFloat() ?: 0f
  val max = values.maxByOrNull { it.value }?.value?.toFloat() ?: 1f
  val range = min..max
  Box(
    contentAlignment = Alignment.Center,
    modifier = modifier,
  ) {
    Canvas(
      modifier = Modifier
        .height(canvasHeight)
        .fillMaxWidth()
        .padding(
          top = canvasHeight
            .div(2)
            .minus(lineHeightDp.div(2))
        )
    ) {
      val distance = (size.width.minus(2 * drawPadding)).div(values.size.minus(1))
      values.forEachIndexed { index, step ->
        this.drawContext.canvas.nativeCanvas.drawText(
          step.text,
          drawPadding + index.times(distance),
          size.height + textTopMargin,
          textPaint
        )
      }
    }
    Slider(
      modifier = Modifier.fillMaxWidth(),
      value = sliderValue.toFloat(),
      valueRange = range,
      steps = values.size,
      colors = customSliderColors(),
      onValueChange = {
        setSliderValue(it.roundToInt())
        onValueChange(it.roundToInt())
      })
  }
}

@Composable
private fun customSliderColors(): SliderColors = SliderDefaults.colors(
  thumbColor = MaterialTheme.colorScheme.primary,
  activeTrackColor = MaterialTheme.colorScheme.primary,
  inactiveTrackColor = MaterialTheme.colorScheme.primaryContainer,
  activeTickColor = MaterialTheme.colorScheme.primary,
  inactiveTickColor = MaterialTheme.colorScheme.primaryContainer,
)