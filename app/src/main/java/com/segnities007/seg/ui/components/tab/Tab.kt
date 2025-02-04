package com.segnities007.seg.ui.components.tab

import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun Tab(
    modifier: Modifier = Modifier,
    tabUiState: TabUiState,
    tabUiAction: TabUiAction,
) {
    TabRow(
        modifier = modifier,
        selectedTabIndex = tabUiState.index,
    ) {
        tabUiState.labels.forEachIndexed { index, title ->
            androidx.compose.material3.Tab(
                modifier = modifier,
                selected = tabUiState.index == index,
                onClick = {
                    tabUiAction.onUpdateIndex(index)
                },
                text = { Text(text = title, overflow = TextOverflow.Ellipsis) },
            )
        }
    }
}
