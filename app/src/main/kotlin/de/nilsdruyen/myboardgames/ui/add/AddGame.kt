/*
 * Created by Nils Druyen on 12-30-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.add

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGame(
  viewModel: AddGameViewModel,
  onBackPressed: () -> Unit,
) {
  var gameName by remember { mutableStateOf("") }

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

      OutlinedTextField(
        value = gameName,
        onValueChange = { gameName = it },
        label = { Text("Name des Spiels") },
        modifier = Modifier
          .constrainAs(nameField) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
          }
          .padding(16.dp)
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
}