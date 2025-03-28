package com.segnities007.seg.ui.navigation.hub.setting

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.components.card.postcard.PostCardAction
import com.segnities007.seg.ui.components.tab.TabAction
import com.segnities007.seg.ui.components.tab.TabUiState
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState
import com.segnities007.seg.ui.screens.hub.home.HomeAction
import com.segnities007.seg.ui.screens.hub.setting.Setting
import com.segnities007.seg.ui.screens.hub.setting.SettingViewModel
import com.segnities007.seg.ui.screens.hub.setting.my_posts.MyPosts
import com.segnities007.seg.ui.screens.hub.setting.preference.Preference
import com.segnities007.seg.ui.screens.hub.setting.userinfo.UserInfo

@Composable
fun NavigationSetting(
    modifier: Modifier = Modifier,
    tabUiState: TabUiState,
    hubState: HubState,
    onHubAction: (HubAction) -> Unit,
    onHomeAction: (HomeAction) -> Unit,
    onTabAction: (TabAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
    onTopNavigate: (Navigation) -> Unit,
    onHubNavigate: (Navigation) -> Unit,
) {
    val settingNavHostController: NavHostController = rememberNavController()
    val settingViewModel: SettingViewModel = hiltViewModel()

    Setting(
        modifier = modifier,
        onHubAction = onHubAction,
    ) {
        NavHost(
            navController = settingNavHostController,
            startDestination = NavigationSettingRoute.Preference,
        ) {
            composable<NavigationSettingRoute.Preference> {
                Preference(
                    onTopNavigate = onTopNavigate, // logout
                    onSettingNavigate = { route: Navigation ->
                        settingNavHostController.navigate(
                            route,
                        )
                    },
                    onHubAction = onHubAction,
                    onSettingAction = settingViewModel::onSettingAction,
                )
            }
            composable<NavigationSettingRoute.UserInfo> {
                UserInfo(
                    hubState = hubState,
                    settingState = settingViewModel.settingState,
                    onNavigate = { route: Navigation -> settingNavHostController.navigate(route) },
                    onHubAction = onHubAction,
                    onSettingAction = settingViewModel::onSettingAction,
                )
            }
            composable<NavigationSettingRoute.Posts> {
                MyPosts(
                    hubState = hubState,
                    tabUiState = tabUiState,
                    onHubNavigate = onHubNavigate,
                    onTabAction = onTabAction,
                    onHubAction = onHubAction,
                    onHomeAction = onHomeAction,
                    onPostCardAction = onPostCardAction,
                )
            }
        }
    }
}
