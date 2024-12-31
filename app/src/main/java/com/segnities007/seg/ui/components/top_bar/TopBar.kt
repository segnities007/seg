package com.segnities007.seg.ui.components.top_bar

import com.segnities007.seg.R
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String,
    screenIndex: Int = 0,
    contentDescription: String,
    onDrawerOpen: suspend () -> Unit,
    scrollBehavior:  TopAppBarScrollBehavior? = null,
    scope: CoroutineScope = rememberCoroutineScope(),
){

    CenterAlignedTopAppBar(
            modifier = modifier,
            title = { Text(text = title, maxLines = 1,) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            navigationIcon = {
                if(screenIndex != 2 && screenIndex != 3)
                    IconButton(onClick = { scope.launch{ onDrawerOpen() } }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_menu_24),
                            contentDescription = contentDescription,
                        )
                    }
            },
        scrollBehavior = scrollBehavior
        )
}