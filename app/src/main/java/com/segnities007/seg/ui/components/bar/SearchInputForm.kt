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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.segnities007.seg.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun SearchInputForm(
    modifier: Modifier = Modifier,
    commonPadding: Dp = dimensionResource(R.dimen.padding_sn),
    onHubBackNavigate: () -> Unit = {},
) {
    var query by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    SearchBar(
        modifier = modifier,
        expanded = false,
        onExpandedChange = { },
        content = {},
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                expanded = false,
                onQueryChange = { query = it },
                onSearch = { focusManager.clearFocus() },
                onExpandedChange = {},
                placeholder = { Text("Search") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            )
        },
    )
}
