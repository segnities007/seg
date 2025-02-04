package com.segnities007.seg.ui.components.top_bar

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.segnities007.seg.R
import com.segnities007.seg.ui.navigation.login.NavigationLoginRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    titleContent: @Composable () -> Unit,
    routeName: String,
    onDrawerOpen: suspend () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    scope: CoroutineScope = rememberCoroutineScope(),
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.shadow(elevation = dimensionResource(R.dimen.elevation_nl)),
        title = { titleContent() },
        colors =
            TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
        navigationIcon = {
            when (routeName) {
                NavigationLoginRoute.CreateAccount.toString() -> Spacer(modifier = Modifier.padding(0.dp))
                NavigationLoginRoute.ConfirmEmail.toString() -> Spacer(modifier = Modifier.padding(0.dp))

                else ->
                    IconButton(onClick = { scope.launch { onDrawerOpen() } }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_menu_24),
                            contentDescription = routeName,
                        )
                    }
            }
        },
        scrollBehavior = scrollBehavior,
    )
}
