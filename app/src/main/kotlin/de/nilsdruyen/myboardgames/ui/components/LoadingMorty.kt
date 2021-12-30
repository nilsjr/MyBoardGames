/*
 * Created by Nils Druyen on 12-30-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import de.nilsdruyen.myboardgames.R

@Composable
fun LoadingMorty(modifier: Modifier = Modifier) {
  val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.morty))
  val progress by animateLottieCompositionAsState(
    composition = composition,
    iterations = LottieConstants.IterateForever
  )
  LottieAnimation(
    composition = composition,
    progress = progress,
    modifier = modifier,
  )
}