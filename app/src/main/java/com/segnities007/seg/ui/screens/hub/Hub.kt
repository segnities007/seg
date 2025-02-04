package com.segnities007.seg.ui.screens.hub

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.segnities007.seg.R
import com.segnities007.seg.data.model.bottom_bar.HubItem
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.domain.presentation.TopAction
import com.segnities007.seg.ui.components.bottom_bar.BottomBar
import com.segnities007.seg.ui.components.floating_button.FloatingButton
import com.segnities007.seg.ui.components.navigation_drawer.NavigationDrawer
import com.segnities007.seg.ui.components.tab.TabUiAction
import com.segnities007.seg.ui.components.tab.TabUiState
import com.segnities007.seg.ui.components.top_bar.TopBar
import com.segnities007.seg.ui.components.top_bar.TopSearchBar
import com.segnities007.seg.ui.components.top_bar.status_bar.TopStatusBarWithFollows
import com.segnities007.seg.ui.components.top_bar.status_bar.TopStatusBarWithTab
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.account.AccountUiState
import com.segnities007.seg.ui.screens.hub.search.SearchUiAction
import com.segnities007.seg.ui.screens.hub.search.TopSearchBarUiAction
import com.segnities007.seg.ui.screens.hub.search.TopSearchBarUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Hub(
    hubViewModel: HubViewModel = hiltViewModel(),
    onHubNavigate: (Route) -> Unit,
    currentRouteName: String,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    tabUiState: TabUiState,
    tabUiAction: TabUiAction,
    searchUiAction: SearchUiAction,
    topSearchBarUiState: TopSearchBarUiState,
    topSearchBarUiAction: TopSearchBarUiAction,
    accountUiState: AccountUiState,
    content: @Composable (Modifier) -> Unit,
) {
    NavigationDrawer(
        items = HubItem(),
        drawerState = hubViewModel.topState.drawerState,
        onNavigate = onHubNavigate,
        onDrawerClose = hubViewModel.onGetTopAction().closeDrawer,
    ) {
        HubUi(
            topAction = hubViewModel.onGetTopAction(),
            content = content,
            currentRouteName = currentRouteName,
            onHubNavigate = onHubNavigate,
            hubUiState = hubUiState,
            hubUiAction = hubUiAction,
            searchUiAction = searchUiAction,
            tabUiState = tabUiState,
            tabUiAction = tabUiAction,
            topSearchBarUiState = topSearchBarUiState,
            topSearchBarUiAction = topSearchBarUiAction,
            accountUiState = accountUiState,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HubUi(
    topAction: TopAction,
    currentRouteName: String,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    accountUiState: AccountUiState,
    searchUiAction: SearchUiAction,
    tabUiState: TabUiState,
    tabUiAction: TabUiAction,
    topSearchBarUiState: TopSearchBarUiState,
    topSearchBarUiAction: TopSearchBarUiAction,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    onHubNavigate: (Route) -> Unit,
    content: @Composable (modifier: Modifier) -> Unit,
) {
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            when (currentRouteName) {
                NavigationHubRoute.Home().name ->
                    TopBar(
                        titleContent = { Text(text = currentRouteName) },
                        onDrawerOpen = topAction.openDrawer,
                        scrollBehavior = scrollBehavior,
                        routeName = currentRouteName,
                    )
                NavigationHubRoute.Trend().name ->
                    TopBar(
                        titleContent = { Text(text = currentRouteName) },
                        onDrawerOpen = topAction.openDrawer,
                        routeName = currentRouteName,
                    )
                NavigationHubRoute.Search().name ->
                    TopSearchBar(
                        searchUiAction = searchUiAction,
                        tabUiState = tabUiState,
                        tabUiAction = tabUiAction,
                        topSearchBarUiState = topSearchBarUiState,
                        topSearchBarUiAction = topSearchBarUiAction,
                    )
                NavigationHubRoute.Post().name ->
                    TopBar(
                        titleContent = { Text(text = currentRouteName) },
                        onDrawerOpen = topAction.openDrawer,
                        routeName = currentRouteName,
                    )
                NavigationHubRoute.Notify().name ->
                    TopBar(
                        titleContent = { Text(text = currentRouteName) },
                        onDrawerOpen = topAction.openDrawer,
                        routeName = currentRouteName,
                    )
                NavigationHubRoute.Account().name ->
                    TopStatusBarWithFollows(
                        user = accountUiState.user,
                        onHubNavigate = onHubNavigate,
                        hubUiAction = hubUiAction,
                    )
                NavigationHubRoute.Accounts().name ->
                    TopBar(
                        titleContent = { Text(text = currentRouteName) },
                        onDrawerOpen = topAction.openDrawer,
                        routeName = currentRouteName,
                    )
                NavigationHubRoute.Setting().name ->
                    if (!hubUiState.isHideTopBar) {
                        TopStatusBarWithFollows(
                            user = hubUiState.user,
                            onHubNavigate = onHubNavigate,
                            hubUiAction = hubUiAction,
                        )
                    } else {
                        TopStatusBarWithTab(
                            user = hubUiState.user,
                            tabUiState = tabUiState,
                            tabUiAction = tabUiAction,
                        )
                    }
                NavigationHubRoute.Comment().name ->
                    TopBar(
                        titleContent = { Text(text = currentRouteName) },
                        onDrawerOpen = topAction.openDrawer,
                        routeName = currentRouteName,
                    )
                else -> Spacer(modifier = Modifier.padding(0.dp))
            }
        },
        bottomBar = {
            BottomBar(
                items = HubItem(),
                currentRouteName = currentRouteName,
                onNavigate = onHubNavigate,
            )
        },
        floatingActionButton = {
            when (currentRouteName) {
                NavigationHubRoute.Trend().name ->
                    FloatingButton(
                        iconID = R.drawable.baseline_search_24,
                        onClick = { onHubNavigate(NavigationHubRoute.Search()) },
                    )
                NavigationHubRoute.Home().name ->
                    FloatingButton(
                        iconID = R.drawable.baseline_search_24,
                        onClick = { onHubNavigate(NavigationHubRoute.Search()) },
                    )
                else -> Spacer(modifier = Modifier.padding(0.dp))
            }
        },
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}
