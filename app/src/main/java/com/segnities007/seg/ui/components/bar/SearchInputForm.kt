package com.segnities007.seg.ui.components.bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import com.segnities007.seg.R
import com.segnities007.seg.ui.screens.hub.search.SearchUiAction
import com.segnities007.seg.ui.screens.hub.search.TopSearchBarUiAction
import com.segnities007.seg.ui.screens.hub.search.TopSearchBarUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchInputForm(
    modifier: Modifier = Modifier,
    searchUiAction: SearchUiAction,
    topSearchBarUiState: TopSearchBarUiState,
    topSearchBarUiAction: TopSearchBarUiAction,
    focusManager: FocusManager = LocalFocusManager.current,
) {
    SearchBar(
        modifier = modifier,
        expanded = false,
        onExpandedChange = { },
        content = {},
        inputField = {
            SearchBarDefaults.InputField(
                query = topSearchBarUiState.keyword,
                expanded = false,
                onQueryChange = { topSearchBarUiAction.onUpdateKeyword(it) },
                onSearch = {
                    focusManager.clearFocus()
                    searchUiAction.onGetPostsByKeyword(topSearchBarUiState.keyword)
                    searchUiAction.onGetPostsByKeywordSortedByViewCount(topSearchBarUiState.keyword)
                    searchUiAction.onGetUsersByKeyword(topSearchBarUiState.keyword)
                },
                onExpandedChange = {},
                placeholder = { Text(stringResource(R.string.search)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            )
        },
    )
}
