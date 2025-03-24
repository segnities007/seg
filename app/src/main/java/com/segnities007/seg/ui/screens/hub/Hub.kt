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
import com.segnities007.seg.data.model.bottom_bar.BottomBarHubItem
import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.domain.presentation.TopAction
import com.segnities007.seg.ui.components.bar.bottom_bar.BottomBar
import com.segnities007.seg.ui.components.bar.status_bar.StatusBarWithFollows
import com.segnities007.seg.ui.components.bar.status_bar.StatusBarWithTab
import com.segnities007.seg.ui.components.bar.top_bar.TopBar
import com.segnities007.seg.ui.components.bar.top_bar.TopSearchBar
import com.segnities007.seg.ui.components.button.FloatingButton
import com.segnities007.seg.ui.components.navigation_drawer.NavigationDrawerWithState
import com.segnities007.seg.ui.components.tab.TabUiAction
import com.segnities007.seg.ui.components.tab.TabUiState
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.account.AccountState
import com.segnities007.seg.ui.screens.hub.search.SearchAction
import com.segnities007.seg.ui.screens.hub.search.TopSearchBarUiAction
import com.segnities007.seg.ui.screens.hub.search.TopSearchBarUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Hub(
    hubViewModel: HubViewModel = hiltViewModel(),
    onHubNavigate: (Navigation) -> Unit,
    currentRouteName: String,
    hubState: HubState,
    hubAction: HubAction,
    tabUiState: TabUiState,
    tabUiAction: TabUiAction,
    searchUiAction: SearchAction,
    topSearchBarUiState: TopSearchBarUiState,
    topSearchBarUiAction: TopSearchBarUiAction,
    accountState: AccountState,
    content: @Composable (Modifier) -> Unit,
) {
    NavigationDrawerWithState(
        items = BottomBarHubItem(),
        drawerState = hubViewModel.topState.drawerState,
        onNavigate = onHubNavigate,
        onDrawerClose = hubViewModel.onGetTopAction().closeDrawer,
        user = hubState.user,
    ) {
        HubUi(
            topAction = hubViewModel.onGetTopAction(),
            content = content,
            currentRouteName = currentRouteName,
            onHubNavigate = onHubNavigate,
            hubState = hubState,
            hubAction = hubAction,
            searchUiAction = searchUiAction,
            tabUiState = tabUiState,
            tabUiAction = tabUiAction,
            topSearchBarUiState = topSearchBarUiState,
            topSearchBarUiAction = topSearchBarUiAction,
            accountState = accountState,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HubUi(
    topAction: TopAction,
    currentRouteName: String,
    hubState: HubState,
    hubAction: HubAction,
    accountState: AccountState,
    searchUiAction: SearchAction,
    tabUiState: TabUiState,
    tabUiAction: TabUiAction,
    topSearchBarUiState: TopSearchBarUiState,
    topSearchBarUiAction: TopSearchBarUiAction,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    onHubNavigate: (Navigation) -> Unit,
    content: @Composable (modifier: Modifier) -> Unit,
) {
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            when (currentRouteName) {
                NavigationHubRoute.Home.name ->
                    TopBar(
                        titleContent = { Text(text = currentRouteName) },
                        onDrawerOpen = topAction.openDrawer,
                        scrollBehavior = scrollBehavior,
                        routeName = currentRouteName,
                    )
                NavigationHubRoute.Trend.name ->
                    TopBar(
                        titleContent = { Text(text = currentRouteName) },
                        onDrawerOpen = topAction.openDrawer,
                        routeName = currentRouteName,
                    )
                NavigationHubRoute.Search.name ->
                    TopSearchBar(
                        searchUiAction = searchUiAction,
                        tabUiState = tabUiState,
                        tabUiAction = tabUiAction,
                        topSearchBarUiState = topSearchBarUiState,
                        topSearchBarUiAction = topSearchBarUiAction,
                    )
                NavigationHubRoute.Post.name ->
                    TopBar(
                        titleContent = { Text(text = currentRouteName) },
                        onDrawerOpen = topAction.openDrawer,
                        routeName = currentRouteName,
                    )
                NavigationHubRoute.Notify.name ->
                    TopBar(
                        titleContent = { Text(text = currentRouteName) },
                        onDrawerOpen = topAction.openDrawer,
                        routeName = currentRouteName,
                    )
                NavigationHubRoute.Account.name ->
                    StatusBarWithFollows(
                        user = accountState.user,
                        onHubNavigate = onHubNavigate,
                        hubAction = hubAction,
                    )
                NavigationHubRoute.Accounts.name ->
                    TopBar(
                        titleContent = { Text(text = currentRouteName) },
                        onDrawerOpen = topAction.openDrawer,
                        routeName = currentRouteName,
                    )
                NavigationHubRoute.Setting.name ->
                    if (!hubState.isHideTopBar) {
                        StatusBarWithFollows(
                            user = hubState.user,
                            onHubNavigate = onHubNavigate,
                            hubAction = hubAction,
                        )
                    } else {
                        StatusBarWithTab(
                            user = hubState.user,
                            tabUiState = tabUiState,
                            tabUiAction = tabUiAction,
                        )
                    }
                NavigationHubRoute.Comment.name ->
                    TopBar(
                        titleContent = { Text(text = currentRouteName) },
                        onDrawerOpen = topAction.openDrawer,
                        routeName = currentRouteName,
                    )
                NavigationHubRoute.PostForComment.name ->
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
                items = BottomBarHubItem(),
                currentRouteName = currentRouteName,
                onNavigate = onHubNavigate,
            )
        },
        floatingActionButton = {
            when (currentRouteName) {
                NavigationHubRoute.Trend.name ->
                    FloatingButton(
                        iconID = R.drawable.baseline_search_24,
                        onClick = { onHubNavigate(NavigationHubRoute.Search) },
                    )
                NavigationHubRoute.Home.name ->
                    FloatingButton(
                        iconID = R.drawable.baseline_search_24,
                        onClick = { onHubNavigate(NavigationHubRoute.Search) },
                    )
                NavigationHubRoute.Comment.name ->
                    FloatingButton(
                        iconID = R.drawable.baseline_create_24,
                        onClick = { onHubNavigate(NavigationHubRoute.PostForComment) },
                    )
                else -> Spacer(modifier = Modifier.padding(0.dp))
            }
        },
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}
