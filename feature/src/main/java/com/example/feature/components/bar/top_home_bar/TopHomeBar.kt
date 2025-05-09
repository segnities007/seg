package com.example.feature.components.bar.top_home_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.domain.model.post.Genre
import com.example.feature.components.bar.top_bar.TopBar
import com.example.feature.components.tab.ScrollTab
import com.example.feature.components.tab.TabAction
import com.example.feature.components.tab.TabUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopHomeBar(
    modifier: Modifier = Modifier,
    tabUiState: TabUiState,
    routeName: String,
    onTabAction: (TabAction) -> Unit,
    titleContent: @Composable () -> Unit,
    onDrawerOpen: suspend () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    LaunchedEffect(Unit) {
        val labels =
            listOf(
                Genre.NORMAL.name,
                Genre.HAIKU.name,
                Genre.TANKA.name,
                Genre.SEDOUKA.name,
                Genre.DODOITSU.name,
            )
        onTabAction(TabAction.SetLabels(labels))
    }

    Column(
        modifier = modifier.background(color = MaterialTheme.colorScheme.primaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        TopBar(
            titleContent = titleContent,
            routeName = routeName,
            onDrawerOpen = onDrawerOpen,
            scrollBehavior = scrollBehavior,
        )
        ScrollTab(
            modifier = modifier.background(color = MaterialTheme.colorScheme.primaryContainer),
            tabUiState = tabUiState,
            onTabAction = onTabAction,
        )
    }
}
