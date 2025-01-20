package com.segnities007.seg.ui.screens.hub

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.segnities007.seg.R
import com.segnities007.seg.data.model.bottom_bar.HubItem
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.domain.presentation.TopAction
import com.segnities007.seg.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.components.bottom_bar.BottomBar
import com.segnities007.seg.ui.components.floating_button.FloatingButton
import com.segnities007.seg.ui.components.navigation_drawer.NavigationDrawer
import com.segnities007.seg.ui.components.top_bar.TopBar

// drawerとnavigationを使用するためにこのようなツリー構造にしてる
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Hub(
    hubViewModel: HubViewModel = hiltViewModel(),
    onNavigate: (Route) -> Unit,
    currentRouteName: String,
    content: @Composable (Modifier) -> Unit,
) {
    NavigationDrawer(
        items = HubItem(),
        drawerState = hubViewModel.topState.drawerState,
        onNavigate = onNavigate,
        onDrawerClose = hubViewModel.onGetTopAction().closeDrawer,
    ) {
        HubUi(
            topAction = hubViewModel.onGetTopAction(),
            content = content,
            currentRouteName = currentRouteName,
            onNavigate = onNavigate,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HubUi(
    topAction: TopAction,
    currentRouteName: String,
    onNavigate: (Route) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    content: @Composable (modifier: Modifier) -> Unit,
) {
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            when (currentRouteName) {
                NavigationHubRoute.Home().name ->
                    TopBar(
                        title = stringResource(R.string.app_name),
                        onDrawerOpen = topAction.openDrawer,
                        scrollBehavior = scrollBehavior,
                        routeName = NavigationHubRoute.Home().name,
                    )
                NavigationHubRoute.Trend().name ->
                    TopBar(
                        title = stringResource(R.string.app_name),
                        onDrawerOpen = topAction.openDrawer,
                        routeName = NavigationHubRoute.Trend().name,
                    )
                NavigationHubRoute.Post().name ->
                    TopBar(
                        title = stringResource(R.string.app_name),
                        onDrawerOpen = topAction.openDrawer,
                        routeName = NavigationHubRoute.Notify().name,
                    )
                NavigationHubRoute.Notify().name ->
                    TopBar(
                        title = stringResource(R.string.app_name),
                        onDrawerOpen = topAction.openDrawer,
                        routeName = NavigationHubRoute.Notify().name,
                    )
                NavigationHubRoute.Accounts().name ->
                    TopBar(
                        title = stringResource(R.string.app_name),
                        onDrawerOpen = topAction.openDrawer,
                        routeName = NavigationHubRoute.Accounts().name,
                    )
                else -> Spacer(modifier = Modifier.padding(0.dp))
            }
        },
        bottomBar = {
            BottomBar(
                items = HubItem(),
                currentRouteName = currentRouteName,
                onNavigate = onNavigate,
            )
        },
        floatingActionButton = {
            when (currentRouteName) {
                NavigationHubRoute.Home().name -> FloatingButton(iconID = R.drawable.baseline_search_24) { }
                NavigationHubRoute.Trend().name -> FloatingButton(iconID = R.drawable.baseline_search_24) { }
                else -> Spacer(modifier = Modifier.padding(0.dp))
            }
        },
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}
