package com.example.feature.components.bar.top_search_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.example.feature.R
import com.example.feature.components.tab.Tab
import com.example.feature.components.tab.TabAction
import com.example.feature.components.tab.TabState
import com.example.feature.screens.hub.search.SearchAction

@Composable
fun TopSearchBar(
    modifier: Modifier = Modifier,
    tabState: TabState,
    topSearchBarState: TopSearchBarState,
    onTabAction: (TabAction) -> Unit,
    onSearchAction: (SearchAction) -> Unit,
    onTopSearchBarAction: (TopSearchBarAction) -> Unit,
) {
    val commonPadding: Dp = dimensionResource(R.dimen.padding_sn)

    LaunchedEffect(Unit) {
        val labels =
            listOf(
                "Most View",
                "Latest",
                "Users",
            )
        onTabAction(TabAction.SetLabels(labels))
    }

    Column(
        modifier = modifier.background(color = MaterialTheme.colorScheme.primaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        SearchInputForm(
            topSearchBarState = topSearchBarState,
            onSearchAction = onSearchAction,
            onTopSearchBarAction = onTopSearchBarAction,
        )
        Spacer(Modifier.padding(commonPadding))
        Tab(
            modifier = modifier.background(color = MaterialTheme.colorScheme.primaryContainer),
            tabState = tabState,
            onTabAction = onTabAction,
        )
    }
}
