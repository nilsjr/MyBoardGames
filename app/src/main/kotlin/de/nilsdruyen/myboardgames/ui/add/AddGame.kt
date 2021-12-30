/*
 * Created by Nils Druyen on 12-30-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.add

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddGame(
  viewModel: AddGameViewModel,
  onBackPressed: () -> Unit,
) {
  val keyboardController = LocalSoftwareKeyboardController.current
  var gameName by remember { mutableStateOf("") }
  val focusRequester = remember { FocusRequester() }
  val state by viewModel.state.collectAsState()

  if (state == AddGameContract.AddGameState.GameAdded) {
    onBackPressed()
  }

  Scaffold(
    topBar = {
      SmallTopAppBar(
        title = { Text("Spiel hinzufügen") },
        navigationIcon = {
          IconButton(onClick = { onBackPressed() }) {
            Icon(
              imageVector = Icons.Filled.ArrowBack,
              contentDescription = "Backpress"
            )
          }
        }
      )
    }
  ) {
    ConstraintLayout(
      modifier = Modifier.fillMaxSize()
    ) {
      val (createButton, nameField) = createRefs()

      TextField(
        value = gameName,
        textStyle = TextStyle(Color.White),
        onValueChange = {
          gameName = it
        },
        label = { Text("Name des Spiels") },
        modifier = Modifier
          .focusRequester(focusRequester)
          .constrainAs(nameField) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
          }
          .padding(8.dp)
          .focusable(),
        keyboardOptions = KeyboardOptions(
          keyboardType = KeyboardType.Text,
          imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
          onDone = {
            viewModel.setAction(AddGameContract.AddGameAction.Add(gameName))
          }
        ),
        singleLine = true,
      )

      Button(
        onClick = {
          Timber.d("add game $gameName")
          viewModel.setAction(AddGameContract.AddGameAction.Add(gameName))
        },
        modifier = Modifier
          .constrainAs(createButton) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
          }
          .padding(16.dp)
      ) {
        Text(text = "Speichern")
      }
    }
  }

  LaunchedEffect(key1 = Unit, block = {
    focusRequester.requestFocus()
    keyboardController?.show()
  })
}