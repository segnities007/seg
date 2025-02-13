package com.segnities007.seg.ui.components.bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
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
    val iconSize = dimensionResource(R.dimen.icon_smaller)

    val onQueryChange = { it: String ->
        topSearchBarUiAction.onUpdateKeyword(it)
    }

    val onSearch = { _: String ->
        focusManager.clearFocus()
        searchUiAction.onEnter(topSearchBarUiState.keyword)
    }

    SearchBar(
        modifier = modifier,
        expanded = false,
        onExpandedChange = {},
        content = {},
        inputField = {
            SearchBarDefaults.InputField(
                query = topSearchBarUiState.keyword,
                expanded = false,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                onExpandedChange = {},
                placeholder = { Text(stringResource(R.string.search)) },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)).size(iconSize),
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                    )
                },
                trailingIcon = { DeleteButton(iconSize, topSearchBarUiAction) },
            )
        },
    )
}

@Composable
private fun DeleteButton(
    iconSize: Dp,
    topSearchBarUiAction: TopSearchBarUiAction,
) {
    val buttonSize = dimensionResource(R.dimen.button_height_normal_size)

    val clickAction = {
        topSearchBarUiAction.onUpdateKeyword("") // reset keyword
    }

    Box(
        modifier =
            Modifier
                .size(buttonSize)
                .padding(dimensionResource(R.dimen.padding_small))
                .clip(CircleShape)
                .clickable(onClick = clickAction),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.size(iconSize),
            painter = painterResource(R.drawable.baseline_close_24),
            contentDescription = null,
        )
    }
}
