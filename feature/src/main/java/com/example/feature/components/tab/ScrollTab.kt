package com.example.feature.components.tab

import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ScrollTab(
    modifier: Modifier = Modifier,
    tabUiState: TabUiState,
    onTabAction: (TabAction) -> Unit,
) {
    ScrollableTabRow(
        modifier = modifier,
        edgePadding = 0.dp,
        selectedTabIndex = tabUiState.index,
    ) {
        tabUiState.labels.forEachIndexed { index, title ->
            androidx.compose.material3.Tab(
                modifier = modifier,
                selected = tabUiState.index == index,
                onClick = {
                    onTabAction(TabAction.UpdateIndex(index))
                },
                text = { Text(text = title, overflow = TextOverflow.Ellipsis) },
            )
        }
    }
}
