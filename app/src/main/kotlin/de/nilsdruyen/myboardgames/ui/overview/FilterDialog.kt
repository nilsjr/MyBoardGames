/*
 * Created by Nils Druyen on 01-24-2022
 * Copyright © 2022 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.ui.overview

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.nilsdruyen.myboardgames.data.models.GameType
import de.nilsdruyen.myboardgames.data.models.toName
import de.nilsdruyen.myboardgames.ui.components.CustomSlider
import de.nilsdruyen.myboardgames.ui.components.SliderItem
import de.nilsdruyen.myboardgames.ui.theme.MyBoardGamesTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilterDialog(
    state: FilterState,
    changeFilter: (FilterState) -> Unit = {},
    applyFilter: () -> Unit = {},
    resetFilter: () -> Unit = {},
) {
    val selectedMap = remember {
        mutableStateMapOf<GameType, Boolean>().apply {
            putAll(state.isGameType)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            "Filter & Sortierung",
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 8.dp, bottom = 8.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.headlineMedium,
        )
//        FlowRow {
//            selectedMap.forEach { (type, selected) ->
//                SelectableChip(
//                    label = type.toName(),
//                    contentDescription = "",
//                    selected = selected,
//                    onClick = {
//                        selectedMap[type] = it
//                        changeFilter(state.copy(isGameType = selectedMap))
//                    }
//                )
//            }
//        }
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            selectedMap.forEach { (type, selected) ->
                item {
                    Row {
                        Checkbox(
                            checked = selected,
                            onCheckedChange = { checked ->
                                selectedMap[type] = checked
                                changeFilter(state.copy(isGameType = selectedMap))
                            })
                        Text(
                            text = type.toName(),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "Spieleranzahl",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        CustomSlider(
            values = listOf(
                SliderItem(1, "1"),
                SliderItem(2, "2"),
                SliderItem(3, "3"),
                SliderItem(4, ">4"),
            ),
            value = state.isPlayerCount,
            onValueChange = {
                changeFilter(state.copy(isPlayerCount = it))
            },
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        )

        Row(modifier = Modifier.padding(16.dp)) {
            Button(
                onClick = {
                    applyFilter()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Filter anwenden")
            }
            IconButton(onClick = {
                resetFilter()
            }) {
                Icon(
                    imageVector = Icons.Filled.RestartAlt,
                    contentDescription = "Reset filter",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Preview(backgroundColor = 0xFF414941, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewDialog() {
    MyBoardGamesTheme(darkTheme = true) {
        FilterDialog(
            FilterState(
                isGameType = GameType.values().toList().associateWith { true },
                isPlayerCount = 2,
                isPlayTimeInMin = 10
            )
        )
    }
}