package com.example.feature.screens.hub

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.example.domain.model.post.Genre
import com.example.domain.presentation.bottombar.BottomBarHubItem
import com.example.domain.presentation.navigation.Navigation
import com.example.domain.presentation.navigation.NavigationHubRoute
import com.example.feature.R
import com.example.feature.components.bar.bottom_bar.BottomBar
import com.example.feature.components.bar.status_bar.StatusBarWithFollows
import com.example.feature.components.bar.status_bar.StatusBarWithTab
import com.example.feature.components.bar.top_bar.TopBar
import com.example.feature.components.bar.top_home_bar.TopHomeBar
import com.example.feature.components.bar.top_search_bar.TopSearchBar
import com.example.feature.components.bar.top_search_bar.TopSearchBarAction
import com.example.feature.components.bar.top_search_bar.TopSearchBarState
import com.example.feature.components.button.FloatingButton
import com.example.feature.components.navigation_drawer.NavigationDrawerWithState
import com.example.feature.components.tab.TabAction
import com.example.feature.components.tab.TabUiState
import com.example.feature.navigation.TopAction
import com.example.feature.navigation.TopState
import com.example.feature.screens.hub.account.AccountState
import com.example.feature.screens.hub.home.HomeAction
import com.example.feature.screens.hub.search.SearchAction

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
    onHomeAction: (HomeAction) -> Unit,
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
            onHomeAction = onHomeAction,
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
    onHomeAction: (HomeAction) -> Unit,
    content: @Composable (modifier: Modifier) -> Unit,
) {
    val scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HubTopBar(
                currentRouteName = currentRouteName,
                hubState = hubState,
                accountState = accountState,
                tabUiState = tabUiState,
                topSearchBarState = topSearchBarState,
                onHubNavigate = onHubNavigate,
                onHubAction = onHubAction,
                onTopAction = onTopAction,
                onSearchAction = onSearchAction,
                onTabAction = onTabAction,
                onTopSearchBarAction = onTopSearchBarAction,
                onHomeAction = onHomeAction,
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            BottomBar(
                items = BottomBarHubItem(),
                currentRouteName = currentRouteName,
                onNavigate = onHubNavigate,
            )
        },
        floatingActionButton = {
            HubFloatingActionButton(
                currentRouteName = currentRouteName,
                onHubNavigate = onHubNavigate,
            )
        },
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HubTopBar(
    currentRouteName: String,
    hubState: HubState,
    accountState: AccountState,
    tabUiState: TabUiState,
    topSearchBarState: TopSearchBarState,
    onHubNavigate: (Navigation) -> Unit,
    onHubAction: (HubAction) -> Unit,
    onTopAction: (TopAction) -> Unit,
    onSearchAction: (SearchAction) -> Unit,
    onTabAction: (TabAction) -> Unit,
    onTopSearchBarAction: (TopSearchBarAction) -> Unit,
    onHomeAction: (HomeAction) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    LaunchedEffect(Unit) {
        val labels =
            listOf(
                Genre.NORMAL.name,
                Genre.HAIKU.name,
                Genre.TANKA.name,
                Genre.SEDOUKA.name,
                Genre.KATAUTA.name,
            )
        onTabAction(TabAction.SetLabels(labels))
    }

    when (currentRouteName) {
        NavigationHubRoute.Home.name ->
            TopHomeBar(
                tabUiState = tabUiState,
                routeName = currentRouteName,
                onTabAction = onTabAction,
                titleContent = { Text(text = currentRouteName) },
                onDrawerOpen = { onTopAction(TopAction.OpenDrawer) },
                scrollBehavior = scrollBehavior,
                onHomeAction = onHomeAction,
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
}

@Composable
private fun HubFloatingActionButton(
    currentRouteName: String,
    onHubNavigate: (Navigation) -> Unit,
) {
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
}
