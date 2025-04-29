package com.example.feature.components.bar

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.example.feature.R
import com.example.feature.components.bar.top_search_bar.TopSearchBarAction
import com.example.feature.components.bar.top_search_bar.TopSearchBarState
import com.example.feature.screens.hub.search.SearchAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchInputForm(
    modifier: Modifier = Modifier,
    topSearchBarState: TopSearchBarState,
    onSearchAction: (SearchAction) -> Unit,
    onTopSearchBarAction: (TopSearchBarAction) -> Unit,
) {
    val focusManager: FocusManager = LocalFocusManager.current
    val iconSize = dimensionResource(R.dimen.icon_smaller)

    val onQueryChange = { it: String ->
        onTopSearchBarAction(TopSearchBarAction.UpdateKeyword(it))
    }

    val onSearch = { _: String ->
        focusManager.clearFocus()
        onSearchAction(SearchAction.Search(topSearchBarState.keyword))
    }

    SearchBar(
        modifier = modifier,
        expanded = false,
        onExpandedChange = {},
        content = {},
        inputField = {
            SearchBarDefaults.InputField(
                query = topSearchBarState.keyword,
                expanded = false,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                onExpandedChange = {},
                placeholder = { Text(stringResource(R.string.search)) },
                leadingIcon = {
                    Icon(
                        modifier =
                            Modifier
                                .padding(dimensionResource(R.dimen.padding_small))
                                .size(iconSize),
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                    )
                },
                trailingIcon = { DeleteButton(iconSize, onTopSearchBarAction) },
            )
        },
    )
}

@Composable
private fun DeleteButton(
    iconSize: Dp,
    onTopSearchBarAction: (TopSearchBarAction) -> Unit,
) {
    val buttonSize = dimensionResource(R.dimen.button_height_normal_size)

    val clickAction = {
        onTopSearchBarAction(TopSearchBarAction.UpdateKeyword(""))
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
