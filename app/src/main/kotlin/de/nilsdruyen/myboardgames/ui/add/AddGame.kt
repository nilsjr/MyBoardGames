/*
 * Created by Nils Druyen on 12-31-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.add

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsWithImePadding
import de.nilsdruyen.myboardgames.data.models.BoardGame
import de.nilsdruyen.myboardgames.data.models.BoardGameFactory
import de.nilsdruyen.myboardgames.data.models.GameType
import de.nilsdruyen.myboardgames.ui.theme.MyBoardGamesTheme
import de.nilsdruyen.myboardgames.ui.theme.toOutlineTextFieldStyle
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddGame(
    viewModel: AddGameViewModel,
    onBackPressed: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    if (state == GameAdded) onBackPressed()

    // TODO: onbackpress dialog discard changes?

    AddGamePane(
        state = state,
        addGame = { viewModel.setAction(AddAction(it)) },
        onBackPressed = onBackPressed
    )
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddGamePane(state: AddGameState, addGame: (BoardGame) -> Unit, onBackPressed: () -> Unit) {
    var game by remember { mutableStateOf(BoardGameFactory.buildNewGame("")) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Spiel hinzufügen") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Backpress"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { addGame(game) }) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Backpress"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .navigationBarsWithImePadding()
        ) {
            AutoFocusingText(
                value = game.name,
                label = "Name",
                isError = state is AddError,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                game = game.copy(name = it)
            }
            OutlinedTextField(
                value = game.manufacturer,
                label = { Text("Hersteller") },
                onValueChange = {
                    game = game.copy(manufacturer = it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                singleLine = true,
                colors = MaterialTheme.colorScheme.toOutlineTextFieldStyle()
            )
            GameTypeChooser(onGameTypeSelected = {
                game = game.copy(type = it)
            })
        }
    }
}

@Composable
fun AutoFocusingText(
    value: String,
    label: String,
    isError: Boolean = false,
    modifier: Modifier,
    changeValue: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        delay(300)
        focusRequester.requestFocus()
    }
    OutlinedTextField(
        value = value,
        label = { Text(label) },
        onValueChange = { changeValue(it) },
        isError = isError,
        modifier = modifier.focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        colors = MaterialTheme.colorScheme.toOutlineTextFieldStyle(),
    )
}

@Composable
fun GameTypeChooser(onGameTypeSelected: (GameType) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val items = GameType.values().toList()
    var selectedIndex by remember { mutableStateOf(0) }
    val focusRequester = remember { FocusRequester() }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
    ) {
        Text(
            text = items[selectedIndex].name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth(fraction = 0.5f)
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(0.dp, Color.Transparent, RoundedCornerShape(4.dp))
                .clickable(onClick = {
                    expanded = true
                    focusRequester.requestFocus()
                })
                .padding(16.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth(fraction = 0.5f)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                    onGameTypeSelected(items[index])
                }) {
                    Text(text = s.name, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAddGame() {
    MyBoardGamesTheme {
        AddGamePane(state = Empty, addGame = {}, onBackPressed = {})
    }
}

@Preview
@Composable
fun DarkPreviewAddGame() {
    MyBoardGamesTheme(darkTheme = true) {
        AddGamePane(state = Empty, addGame = {}, onBackPressed = {})
    }
}