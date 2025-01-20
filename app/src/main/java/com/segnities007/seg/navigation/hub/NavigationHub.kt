package com.segnities007.seg.navigation.hub

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.navigation.hub.setting.NavigationSetting
import com.segnities007.seg.ui.components.card.PostCardViewModel
import com.segnities007.seg.ui.screens.hub.Hub
import com.segnities007.seg.ui.screens.hub.HubViewModel
import com.segnities007.seg.ui.screens.hub.account.Account
import com.segnities007.seg.ui.screens.hub.account.AccountViewModel
import com.segnities007.seg.ui.screens.hub.account.Accounts
import com.segnities007.seg.ui.screens.hub.home.Home
import com.segnities007.seg.ui.screens.hub.home.comment.Comment
import com.segnities007.seg.ui.screens.hub.notify.Notify
import com.segnities007.seg.ui.screens.hub.post.Post
import com.segnities007.seg.ui.screens.hub.search.Search
import com.segnities007.seg.ui.screens.hub.trend.Trend

@Composable
fun NavigationHub(
    hubNavHostController: NavHostController = rememberNavController(),
    postCardViewModel: PostCardViewModel = hiltViewModel(), // GetPostの実行を必要最低限にするためここに設置
    accountViewModel: AccountViewModel = hiltViewModel(),
    hubViewModel: HubViewModel = hiltViewModel(),
    onTopNavigate: (route: Route) -> Unit, // go to login
) {
    LaunchedEffect(Unit) {
        val postCardUiAction = postCardViewModel.onGetPostCardUiAction()
        postCardUiAction.onGetNewPosts()
    }

    val onHubNavigate = { route: Route ->
        hubViewModel.getHubUiAction().onChangeCurrentRouteName(route.name)
        hubNavHostController.navigate(route)
    }

    Hub(
        currentRouteName = hubViewModel.hubUiState.currentRouteName,
        onHubNavigate = onHubNavigate,
        hubUiState = hubViewModel.hubUiState,
        accountUiState = accountViewModel.accountUiState,
        accountUiAction = accountViewModel.getAccountUiAction(),
    ) { modifier: Modifier ->
        NavHost(navController = hubNavHostController, startDestination = NavigationHubRoute.Home()) {
            composable<NavigationHubRoute.Home> {
                Home(
                    modifier = modifier,
                    hubUiState = hubViewModel.hubUiState,
                    hubUiAction = hubViewModel.getHubUiAction(),
                    postCardUiState = postCardViewModel.postCardUiState,
                    postCardUiAction = postCardViewModel.onGetPostCardUiAction(),
                    hubNavController = hubNavHostController,
                )
            }
            composable<NavigationHubRoute.Trend> {
                Trend(modifier = modifier)
            }
            composable<NavigationHubRoute.Post> {
                Post(
                    modifier = modifier,
                    hubUiState = hubViewModel.hubUiState,
                    postCardUiAction = postCardViewModel.onGetPostCardUiAction(),
                    onNavigate = { route: Route ->
                        hubNavHostController.navigate(route)
                        hubViewModel.getHubUiAction().onChangeCurrentRouteName(route.name)
                        postCardViewModel.onGetPostCardUiAction().onGetNewPosts()
                    },
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
                )
            }
            composable<NavigationHubRoute.Account> {
                Account(
                    modifier = modifier,
                    hubUiState = hubViewModel.hubUiState,
                    hubUiAction = hubViewModel.getHubUiAction(),
                    accountUiState = accountViewModel.accountUiState,
                    accountUiAction = accountViewModel.getAccountUiAction(),
                    onHubNavigate = onHubNavigate,
                    postCardUiAction = accountViewModel.getPostUiAction(),
                )
            }
            composable<NavigationHubRoute.Accounts> {
                Accounts(
                    modifier = modifier,
                    hubUiAction = hubViewModel.getHubUiAction(),
                    accountUiState = accountViewModel.accountUiState,
                    accountUiAction = accountViewModel.getAccountUiAction(),
                    onNavigate = onHubNavigate,
                )
            }
            composable<NavigationHubRoute.Search> {
                Search(modifier = modifier)
            }
            composable<NavigationHubRoute.Search> {
                Search(modifier = modifier)
            }
            composable<NavigationHubRoute.Comment> {
                Comment(
                    modifier = modifier,
                    hubUiState = hubViewModel.hubUiState,
                    hubUiAction = hubViewModel.getHubUiAction(),
                    postCardUiState = postCardViewModel.postCardUiState,
                    postCardUiAction = postCardViewModel.onGetPostCardUiAction(),
                    onHubNavigate = onHubNavigate,
                )
            }
        }
    }
}