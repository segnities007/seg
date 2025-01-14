package com.segnities007.seg.navigation.hub

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.segnities007.seg.ui.components.card.PostCardViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.navigation.hub.setting.NavigationSetting
import com.segnities007.seg.ui.screens.hub.Hub
import com.segnities007.seg.ui.screens.hub.HubViewModel
import com.segnities007.seg.ui.screens.hub.account.Account
import com.segnities007.seg.ui.screens.hub.account.AccountViewModel
import com.segnities007.seg.ui.screens.hub.account.Accounts
import com.segnities007.seg.ui.screens.hub.home.Home
import com.segnities007.seg.ui.screens.hub.notify.Notify
import com.segnities007.seg.ui.screens.hub.post.Post
import com.segnities007.seg.ui.screens.hub.search.Search
import com.segnities007.seg.ui.screens.hub.trend.Trend

@Composable
fun NavigationHub(
    hubNavHostController: NavHostController = rememberNavController(),
    postCardViewModel: PostCardViewModel = hiltViewModel(),//GetPostの実行を必要最低限にするためここに設置
    accountViewModel: AccountViewModel = hiltViewModel(),
    hubViewModel: HubViewModel = hiltViewModel(),
    onTopNavigate: (route: Route) -> Unit,//go to login
){

    LaunchedEffect(Unit) {
        val postCardUiAction = postCardViewModel.onGetPostCardUiAction()
        postCardUiAction.onGetNewPosts()
    }

    Hub(
        currentRouteName = hubViewModel.hubUiState.currentRouteName,
        onNavigate = {route: Route ->
            hubNavHostController.navigate(route)
            hubViewModel.getHubUiAction().onChangeCurrentRouteName(route::class.simpleName.toString())
        }
    ){ modifier: Modifier ->
        NavHost(navController = hubNavHostController, startDestination = NavigationHubRoute.Home,) {
            composable<NavigationHubRoute.Home> {
                Home(
                    modifier = modifier,
                    hubUiAction = hubViewModel.getHubUiAction(),
                    postCardUiState = postCardViewModel.postCardUiState,
                    postCardUiAction = postCardViewModel.onGetPostCardUiAction(),
                    hubNavController = hubNavHostController,
                )
            }
            composable<NavigationHubRoute.Trend> {
                Trend(modifier = modifier,)
            }
            composable<NavigationHubRoute.Post>{
                Post(
                    modifier = modifier,
                    hubUiState = hubViewModel.hubUiState,
                    onNavigate = {route: Route ->
                        hubNavHostController.navigate(route)
                        hubViewModel.getHubUiAction().onChangeCurrentRouteName(route::class.simpleName.toString())
                        postCardViewModel.onGetPostCardUiAction().onGetNewPosts()
                    }
                )
            }
            composable<NavigationHubRoute.Notify>{
                Notify(modifier = modifier,)
            }
            composable<NavigationHubRoute.Setting>{
                NavigationSetting(
                    modifier = modifier,
                    accountUiAction = accountViewModel.getAccountUiAction(),
                    hubUiState = hubViewModel.hubUiState,
                    hubUiAction = hubViewModel.getHubUiAction(),
                    onHubNavigate = { route: Route ->
                        hubViewModel.getHubUiAction().onChangeCurrentRouteName(NavigationHubRoute.Accounts.routeName)
                        hubNavHostController.navigate(route)
                    },
                    onTopNavigate = onTopNavigate
                )
            }
            composable<NavigationHubRoute.Account>{
                Account(
                    modifier = modifier,
                    hubUiState = hubViewModel.hubUiState,
                    hubUiAction = hubViewModel.getHubUiAction(),
                    accountUiState = accountViewModel.accountUiState,
                    accountUiAction = accountViewModel.getAccountUiAction(),
                    onHubNavigate = {route: Route ->
                        hubViewModel.getHubUiAction().onChangeCurrentRouteName(NavigationHubRoute.Accounts.routeName)
                        hubNavHostController.navigate(route)
                    },
                )
            }
            composable<NavigationHubRoute.Accounts>{
                Accounts(
                    modifier = modifier,
                    hubUiAction = hubViewModel.getHubUiAction(),
                    accountUiState = accountViewModel.accountUiState,
                    accountUiAction = accountViewModel.getAccountUiAction(),
                    onNavigate = { route: Route ->
                            hubViewModel.getHubUiAction().onChangeCurrentRouteName(NavigationHubRoute.Account.routeName)
                            hubNavHostController.navigate(route)
                        }
                    )
            }
            composable<NavigationHubRoute.Search>{
                Search(modifier = modifier,)
            }
        }
    }
}