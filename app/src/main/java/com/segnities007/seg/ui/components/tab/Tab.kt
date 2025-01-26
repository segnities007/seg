package com.segnities007.seg.ui.components.tab

import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.segnities007.seg.ui.screens.hub.search.TopSearchBarUiAction
import com.segnities007.seg.ui.screens.hub.search.TopSearchBarUiState

@Composable
fun Tab(
    modifier: Modifier = Modifier,
    topSearchBarUiState: TopSearchBarUiState,
    topSearchBarUiAction: TopSearchBarUiAction,
) {
    TabRow(
        modifier = modifier,
        selectedTabIndex = topSearchBarUiState.index,
    ) {
        topSearchBarUiState.titles.forEachIndexed { index, title ->
            androidx.compose.material3.Tab(
                modifier = modifier,
                selected = topSearchBarUiState.index == index,
                onClick = {
                    topSearchBarUiAction.onUpdateIndex(index)
                },
                text = { Text(text = title, overflow = TextOverflow.Ellipsis) },
            )
        }
    }
}
