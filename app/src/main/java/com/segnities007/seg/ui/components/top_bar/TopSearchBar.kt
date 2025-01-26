package com.segnities007.seg.ui.components.top_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.segnities007.seg.R
import com.segnities007.seg.ui.components.bar.SearchInputForm
import com.segnities007.seg.ui.components.tab.Tab
import com.segnities007.seg.ui.screens.hub.search.SearchUiAction
import com.segnities007.seg.ui.screens.hub.search.TopSearchBarUiAction
import com.segnities007.seg.ui.screens.hub.search.TopSearchBarUiState

@Composable
fun TopSearchBar(
    modifier: Modifier = Modifier,
    searchUiAction: SearchUiAction,
    topSearchBarUiState: TopSearchBarUiState,
    topSearchBarUiAction: TopSearchBarUiAction,
    commonPadding: Dp = dimensionResource(R.dimen.padding_sn),
) {
    Column(
        modifier = modifier.background(color = MaterialTheme.colorScheme.primaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        SearchInputForm(
            searchUiAction = searchUiAction,
            topSearchBarUiState = topSearchBarUiState,
            topSearchBarUiAction = topSearchBarUiAction,
        )
        Spacer(Modifier.padding(commonPadding))
        Tab(
            modifier = modifier.background(color = MaterialTheme.colorScheme.primaryContainer),
            topSearchBarUiState = topSearchBarUiState,
            topSearchBarUiAction = topSearchBarUiAction,
        )
    }
}
