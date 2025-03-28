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
import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.components.card.postcard.PostCardViewModel
import com.segnities007.seg.ui.components.tab.TabViewModel
import com.segnities007.seg.ui.navigation.hub.setting.NavigationSetting
import com.segnities007.seg.ui.screens.hub.Hub
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubViewModel
import com.segnities007.seg.ui.screens.hub.account.Account
import com.segnities007.seg.ui.screens.hub.account.AccountViewModel
import com.segnities007.seg.ui.screens.hub.accounts.Accounts
import com.segnities007.seg.ui.screens.hub.comment.Comment
import com.segnities007.seg.ui.screens.hub.home.Home
import com.segnities007.seg.ui.screens.hub.home.HomeAction
import com.segnities007.seg.ui.screens.hub.home.HomeViewModel
import com.segnities007.seg.ui.screens.hub.notify.Notify
import com.segnities007.seg.ui.screens.hub.post.Post
import com.segnities007.seg.ui.screens.hub.post.PostForComment
import com.segnities007.seg.ui.screens.hub.search.Search
import com.segnities007.seg.ui.screens.hub.search.SearchViewModel
import com.segnities007.seg.ui.screens.hub.trend.Trend

@Composable
fun NavigationHub(onTopNavigate: (route: Navigation) -> Unit) {
    val hubNavHostController: NavHostController = rememberNavController()
    val postCardViewModel: PostCardViewModel = hiltViewModel()
    val accountViewModel: AccountViewModel = hiltViewModel()
    val searchViewModel: SearchViewModel = hiltViewModel()
    val homeViewModel: HomeViewModel = hiltViewModel()
    val hubViewModel: HubViewModel = hiltViewModel()
    val tabViewModel: TabViewModel = viewModel()

    LaunchedEffect(Unit) {
        hubViewModel.onHubAction(HubAction.GetUser)
        homeViewModel.onHomeAction(HomeAction.GetNewPosts)
    }

    val navBackStackEntry by hubNavHostController.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry
            ?.destination
            ?.route
            ?.substringBefore("?") // クエリパラメータを除去
            ?.substringAfterLast(".") // 最後のドット以降を取得

    val onHubNavigate = { route: Navigation ->
        hubViewModel.onHubAction(HubAction.ChangeCurrentRouteName(route.name))
        hubNavHostController.navigate(route)
    }

    Hub(
        currentRouteName = currentRoute.toString(),
        onHubNavigate = onHubNavigate,
        hubState = hubViewModel.hubState,
        accountState = accountViewModel.accountUiState,
        tabUiState = tabViewModel.tabUiState,
        topSearchBarState = searchViewModel.topSearchBarUiState,
        onHubAction = hubViewModel::onHubAction,
        onSearchAction = searchViewModel::onSearchAction,
        onTabAction = tabViewModel::onTabAction,
        onTopSearchBarAction = searchViewModel::onTopSearchBarAction,
        topState = hubViewModel.topState,
        onTopAction = hubViewModel::onTopAction,
    ) { modifier: Modifier ->
        NavHost(navController = hubNavHostController, startDestination = NavigationHubRoute.Home) {
            composable<NavigationHubRoute.Home> {
                Home(
                    modifier = modifier,
                    hubState = hubViewModel.hubState,
                    homeState = homeViewModel.homeState,
                    onHubNavigate = onHubNavigate,
                    onHubAction = hubViewModel::onHubAction,
                    onHomeAction = homeViewModel::onHomeAction,
                    onPostCardAction = postCardViewModel::onPostCardAction,
                )
            }
            composable<NavigationHubRoute.Trend> {
                Trend(
                    modifier = modifier,
                    hubState = hubViewModel.hubState,
                    onHubNavigate = onHubNavigate,
                    onHubAction = hubViewModel::onHubAction,
                    onPostCardAction = postCardViewModel::onPostCardAction,
                )
            }
            composable<NavigationHubRoute.Post> {
                Post(
                    modifier = modifier,
                    hubState = hubViewModel.hubState,
                    onHubNavigate = onHubNavigate,
                    onHubAction = hubViewModel::onHubAction,
                    onHomeAction = homeViewModel::onHomeAction,
                )
            }
            composable<NavigationHubRoute.Notify> {
                Notify(modifier = modifier)
            }
            composable<NavigationHubRoute.Setting> {
                NavigationSetting(
                    modifier = modifier,
                    hubState = hubViewModel.hubState,
                    onTopNavigate = onTopNavigate,
                    tabUiState = tabViewModel.tabUiState,
                    onHubNavigate = onHubNavigate,
                    onHubAction = hubViewModel::onHubAction,
                    onHomeAction = homeViewModel::onHomeAction,
                    onTabAction = tabViewModel::onTabAction,
                    onPostCardAction = postCardViewModel::onPostCardAction,
                )
            }
            composable<NavigationHubRoute.Account> {
                Account(
                    modifier = modifier,
                    hubState = hubViewModel.hubState,
                    accountUiFlagState = accountViewModel.accountUiFlagState,
                    accountState = accountViewModel.accountUiState,
                    onHubNavigate = onHubNavigate,
                    onHubAction = hubViewModel::onHubAction,
                    onAccountAction = accountViewModel::onAccountAction,
                    onPostCardAction = postCardViewModel::onPostCardAction,
                )
            }
            composable<NavigationHubRoute.Accounts> {
                Accounts(
                    modifier = modifier,
                    onHubNavigate = onHubNavigate,
                    hubState = hubViewModel.hubState,
                    onAccountAction = accountViewModel::onAccountAction,
                    onHubAction = hubViewModel::onHubAction,
                )
            }
            composable<NavigationHubRoute.Search> {
                Search(
                    modifier = modifier,
                    hubState = hubViewModel.hubState,
                    tabUiState = tabViewModel.tabUiState,
                    searchState = searchViewModel.searchState,
                    onHubNavigate = onHubNavigate,
                    topSearchBarState = searchViewModel.topSearchBarUiState,
                    onHubAction = hubViewModel::onHubAction,
                    onSearchAction = searchViewModel::onSearchAction,
                    onAccountAction = accountViewModel::onAccountAction,
                    onPostCardAction = postCardViewModel::onPostCardAction,
                )
            }
            composable<NavigationHubRoute.Comment> {
                Comment(
                    modifier = modifier,
                    hubState = hubViewModel.hubState,
                    onHubNavigate = onHubNavigate,
                    onHubAction = hubViewModel::onHubAction,
                    onPostCardAction = postCardViewModel::onPostCardAction,
                )
            }
            composable<NavigationHubRoute.PostForComment> {
                PostForComment(
                    modifier = modifier,
                    hubState = hubViewModel.hubState,
                    onBackHubNavigate = { hubNavHostController.navigateUp() },
                    onHubNavigate = onHubNavigate,
                    onHubAction = hubViewModel::onHubAction,
                    onHomeAction = homeViewModel::onHomeAction,
                    onPostCardAction = postCardViewModel::onPostCardAction,
                )
            }
        }
    }
}
