package com.segnities007.seg.ui.components.top_bar.status_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.segnities007.seg.data.model.User
import com.segnities007.seg.ui.components.tab.Tab
import com.segnities007.seg.ui.components.tab.TabUiAction
import com.segnities007.seg.ui.components.tab.TabUiState

@Composable
fun TopStatusBarWithTab(
    user: User,
    tabUiState: TabUiState,
    tabUiAction: TabUiAction,
) {
    LaunchedEffect(Unit) {
        val labels =
            listOf(
                "Post",
                "Like",
                "Repost",
            )
        tabUiAction.onSetLabels(labels)
    }

    TopStatusBar(modifier = Modifier.fillMaxWidth(), user = user) {
        Tab(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.primaryContainer),
            tabUiState = tabUiState,
            tabUiAction = tabUiAction,
        )
    }
}
