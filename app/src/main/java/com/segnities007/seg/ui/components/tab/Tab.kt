package com.segnities007.seg.ui.components.tab

import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun Tab(modifier: Modifier = Modifier) {
    var state by remember { mutableIntStateOf(0) }
    val titles = listOf("Tab 1", "Tab 2", "Tab 3")

    TabRow(
        modifier = modifier,
        selectedTabIndex = state,
    ) {
        titles.forEachIndexed { index, title ->
            androidx.compose.material3.Tab(
                modifier = modifier,
                selected = state == index,
                onClick = { state = index },
                text = { Text(text = title, overflow = TextOverflow.Ellipsis) },
            )
        }
    }
}
