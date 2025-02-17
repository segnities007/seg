package com.segnities007.seg.ui.navigation.hub

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.components.card.postcard.PostCardViewModel
import com.segnities007.seg.ui.components.tab.TabViewModel
import com.segnities007.seg.ui.navigation.hub.setting.NavigationSetting
import com.segnities007.seg.ui.screens.hub.Hub
import com.segnities007.seg.ui.screens.hub.HubViewModel
import com.segnities007.seg.ui.screens.hub.account.Account
import com.segnities007.seg.ui.screens.hub.account.AccountViewModel
import com.segnities007.seg.ui.screens.hub.accounts.Accounts
import com.segnities007.seg.ui.screens.hub.home.Home
import com.segnities007.seg.ui.screens.hub.home.HomeViewModel
import com.segnities007.seg.ui.screens.hub.home.comment.Comment
import com.segnities007.seg.ui.screens.hub.notify.Notify
import com.segnities007.seg.ui.screens.hub.post.Post
import com.segnities007.seg.ui.screens.hub.search.Search
import com.segnities007.seg.ui.screens.hub.search.SearchViewModel
import com.segnities007.seg.ui.screens.hub.trend.Trend

@Composable
fun NavigationHub(
    hubNavHostController: NavHostController = rememberNavController(),
    postCardViewModel: PostCardViewModel = hiltViewModel(),
    accountViewModel: AccountViewModel = hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    hubViewModel: HubViewModel = hiltViewModel(),
    tabViewModel: TabViewModel = viewModel(),
    onTopNavigate: (route: Route) -> Unit, // go to login
) {
    LaunchedEffect(Unit) {
        val hubUiAction = hubViewModel.getHubUiAction()
        hubUiAction.onGetUser()
        homeViewModel.onGetHomeUiAction().onGetNewPosts()
    }

    val navBackStackEntry by hubNavHostController.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry
            ?.destination
            ?.route
            ?.substringBefore("?") // クエリパラメータを除去
            ?.substringAfterLast(".") // 最後のドット以降を取得

    val onHubNavigate = { route: Route ->
        hubViewModel.getHubUiAction().onChangeCurrentRouteName(route.name)
        hubNavHostController.navigate(route)
    }

    Hub(
        currentRouteName = currentRoute.toString(),
        onHubNavigate = onHubNavigate,
        hubUiState = hubViewModel.hubUiState,
        hubUiAction = hubViewModel.getHubUiAction(),
        accountUiState = accountViewModel.accountUiState,
        searchUiAction = searchViewModel.onGetSearchUiAction(),
        tabUiAction = tabViewModel.onGetTabUiAction(),
        tabUiState = tabViewModel.tabUiState,
        topSearchBarUiState = searchViewModel.topSearchBarUiState,
        topSearchBarUiAction = searchViewModel.onGetTopSearchBarUiAction(),
    ) { modifier: Modifier ->
        NavHost(navController = hubNavHostController, startDestination = NavigationHubRoute.Home()) {
            composable<NavigationHubRoute.Home> {
                Home(
                    modifier = modifier,
                    hubUiState = hubViewModel.hubUiState,
                    hubUiAction = hubViewModel.getHubUiAction(),
                    homeUiState = homeViewModel.homeUiState,
                    homeUiAction = homeViewModel.onGetHomeUiAction(),
                    postCardUiAction = postCardViewModel.onGetPostCardUiAction(),
                    onHubNavigate = onHubNavigate,
                )
            }
            composable<NavigationHubRoute.Trend> {
                Trend(
                    modifier = modifier,
                    hubUiState = hubViewModel.hubUiState,
                    hubUiAction = hubViewModel.getHubUiAction(),
                    postCardUiAction = postCardViewModel.onGetPostCardUiAction(),
                    onHubNavigate = onHubNavigate,
                )
            }
            composable<NavigationHubRoute.Post> {
                Post(
                    modifier = modifier,
                    hubUiState = hubViewModel.hubUiState,
                    hubUiAction = hubViewModel.getHubUiAction(),
                    onNavigate = onHubNavigate,
                )
            }
            composable<NavigationHubRoute.Notify> {
                Notify(modifier = modifier)
            }
            composable<NavigationHubRoute.Setting> {
                NavigationSetting(
                    modifier = modifier,
                    hubUiState = hubViewModel.hubUiState,
                    hubUiAction = hubViewModel.getHubUiAction(),
                    onTopNavigate = onTopNavigate,
                    postCardUiAction = postCardViewModel.onGetPostCardUiAction(),
                    tabUiState = tabViewModel.tabUiState,
                    tabUiAction = tabViewModel.onGetTabUiAction(),
                    onHubNavigate = onHubNavigate,
                )
            }
            composable<NavigationHubRoute.Account> {
                Account(
                    modifier = modifier,
                    hubUiState = hubViewModel.hubUiState,
                    hubUiAction = hubViewModel.getHubUiAction(),
                    accountUiState = accountViewModel.accountUiState,
                    accountUiAction = accountViewModel.getAccountUiAction(),
                    postCardUiAction = postCardViewModel.onGetPostCardUiAction(),
                    onHubNavigate = onHubNavigate,
                )
            }
            composable<NavigationHubRoute.Accounts> {
                Accounts(
                    modifier = modifier,
                    hubUiAction = hubViewModel.getHubUiAction(),
                    accountUiAction = accountViewModel.getAccountUiAction(),
                    onHubNavigate = onHubNavigate,
                    hubUiState = hubViewModel.hubUiState,
                )
            }
            composable<NavigationHubRoute.Search> {
                Search(
                    modifier = modifier,
                    hubUiState = hubViewModel.hubUiState,
                    hubUiAction = hubViewModel.getHubUiAction(),
                    tabUiState = tabViewModel.tabUiState,
                    postCardUiAction = postCardViewModel.onGetPostCardUiAction(),
                    topSearchBarUiState = searchViewModel.topSearchBarUiState,
                    accountUiAction = accountViewModel.getAccountUiAction(),
                    searchUiState = searchViewModel.searchUiState,
                    searchUiAction = searchViewModel.onGetSearchUiAction(),
                    onHubNavigate = onHubNavigate,
                )
            }
            composable<NavigationHubRoute.Comment> {
                Comment(
                    modifier = modifier,
                    hubUiState = hubViewModel.hubUiState,
                    hubUiAction = hubViewModel.getHubUiAction(),
                    postCardUiAction = postCardViewModel.onGetPostCardUiAction(),
                    onHubNavigate = onHubNavigate,
                )
            }
        }
    }
}
