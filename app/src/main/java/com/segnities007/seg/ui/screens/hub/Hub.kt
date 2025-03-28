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
import com.segnities007.seg.R
import com.segnities007.seg.data.model.bottom_bar.BottomBarHubItem
import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.components.bar.bottom_bar.BottomBar
import com.segnities007.seg.ui.components.bar.status_bar.StatusBarWithFollows
import com.segnities007.seg.ui.components.bar.status_bar.StatusBarWithTab
import com.segnities007.seg.ui.components.bar.top_bar.TopBar
import com.segnities007.seg.ui.components.bar.top_search_bar.TopSearchBar
import com.segnities007.seg.ui.components.bar.top_search_bar.TopSearchBarAction
import com.segnities007.seg.ui.components.bar.top_search_bar.TopSearchBarState
import com.segnities007.seg.ui.components.button.FloatingButton
import com.segnities007.seg.ui.components.navigation_drawer.NavigationDrawerWithState
import com.segnities007.seg.ui.components.tab.TabAction
import com.segnities007.seg.ui.components.tab.TabUiState
import com.segnities007.seg.ui.navigation.TopAction
import com.segnities007.seg.ui.navigation.TopState
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.account.AccountState
import com.segnities007.seg.ui.screens.hub.search.SearchAction

@Composable
fun Hub(
    currentRouteName: String,
    hubState: HubState,
    tabUiState: TabUiState,
    topState: TopState,
    topSearchBarState: TopSearchBarState,
    accountState: AccountState,
    onTabAction: (TabAction) -> Unit,
    onHubAction: (HubAction) -> Unit,
    onTopAction: (TopAction) -> Unit,
    onSearchAction: (SearchAction) -> Unit,
    onTopSearchBarAction: (TopSearchBarAction) -> Unit,
    onHubNavigate: (Navigation) -> Unit,
    content: @Composable (Modifier) -> Unit,
) {
    NavigationDrawerWithState(
        items = BottomBarHubItem(),
        drawerState = topState.drawerState,
        onNavigate = onHubNavigate,
        onDrawerClose = { onTopAction(TopAction.CloseDrawer) },
        user = hubState.user,
    ) {
        HubUi(
            content = content,
            currentRouteName = currentRouteName,
            onHubNavigate = onHubNavigate,
            hubState = hubState,
            tabUiState = tabUiState,
            accountState = accountState,
            topSearchBarState = topSearchBarState,
            onHubAction = onHubAction,
            onTopAction = onTopAction,
            onSearchAction = onSearchAction,
            onTabAction = onTabAction,
            onTopSearchBarAction = onTopSearchBarAction,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HubUi(
    currentRouteName: String,
    hubState: HubState,
    accountState: AccountState,
    tabUiState: TabUiState,
    topSearchBarState: TopSearchBarState,
    onHubAction: (HubAction) -> Unit,
    onTopAction: (TopAction) -> Unit,
    onSearchAction: (SearchAction) -> Unit,
    onTabAction: (TabAction) -> Unit,
    onTopSearchBarAction: (TopSearchBarAction) -> Unit,
    onHubNavigate: (Navigation) -> Unit,
    content: @Composable (modifier: Modifier) -> Unit,
) {
    val scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            when (currentRouteName) {
                NavigationHubRoute.Home.name ->
                    TopBar(
                        titleContent = { Text(text = currentRouteName) },
                        onDrawerOpen = { onTopAction(TopAction.OpenDrawer) },
                        scrollBehavior = scrollBehavior,
                        routeName = currentRouteName,
                    )

                NavigationHubRoute.Trend.name ->
                    TopBar(
                        titleContent = { Text(text = currentRouteName) },
                        onDrawerOpen = { onTopAction(TopAction.OpenDrawer) },
                        routeName = currentRouteName,
                    )

                NavigationHubRoute.Search.name ->
                    TopSearchBar(
                        tabUiState = tabUiState,
                        topSearchBarState = topSearchBarState,
                        onTabAction = onTabAction,
                        onSearchAction = onSearchAction,
                        onTopSearchBarAction = onTopSearchBarAction,
                    )

                NavigationHubRoute.Post.name ->
                    TopBar(
                        titleContent = { Text(text = currentRouteName) },
                        onDrawerOpen = { onTopAction(TopAction.OpenDrawer) },
                        routeName = currentRouteName,
                    )

                NavigationHubRoute.Notify.name ->
                    TopBar(
                        titleContent = { Text(text = currentRouteName) },
                        onDrawerOpen = { onTopAction(TopAction.OpenDrawer) },
                        routeName = currentRouteName,
                    )

                NavigationHubRoute.Account.name ->
                    StatusBarWithFollows(
                        user = accountState.user,
                        onHubNavigate = onHubNavigate,
                        onHubAction = onHubAction,
                    )

                NavigationHubRoute.Accounts.name ->
                    TopBar(
                        titleContent = { Text(text = currentRouteName) },
                        onDrawerOpen = { onTopAction(TopAction.OpenDrawer) },
                        routeName = currentRouteName,
                    )

                NavigationHubRoute.Setting.name ->
                    if (!hubState.isHideTopBar) {
                        StatusBarWithFollows(
                            user = hubState.user,
                            onHubNavigate = onHubNavigate,
                            onHubAction = onHubAction,
                        )
                    } else {
                        StatusBarWithTab(
                            user = hubState.user,
                            tabUiState = tabUiState,
                            onTabAction = onTabAction,
                        )
                    }

                NavigationHubRoute.Comment.name ->
                    TopBar(
                        titleContent = { Text(text = currentRouteName) },
                        onDrawerOpen = { onTopAction(TopAction.OpenDrawer) },
                        routeName = currentRouteName,
                    )

                NavigationHubRoute.PostForComment.name ->
                    TopBar(
                        titleContent = { Text(text = currentRouteName) },
                        onDrawerOpen = { onTopAction(TopAction.OpenDrawer) },
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
