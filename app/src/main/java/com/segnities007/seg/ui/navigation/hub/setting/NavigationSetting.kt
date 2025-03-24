package com.segnities007.seg.ui.navigation.hub.setting

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.components.card.postcard.PostCardUiAction
import com.segnities007.seg.ui.components.tab.TabUiAction
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
    settingNavHostController: NavHostController = rememberNavController(),
    settingViewModel: SettingViewModel = hiltViewModel(),
    postCardUiAction: PostCardUiAction,
    tabUiState: TabUiState,
    tabUiAction: TabUiAction,
    homeAction: HomeAction,
    hubState: HubState,
    hubAction: HubAction,
    onTopNavigate: (Navigation) -> Unit,
    onHubNavigate: (Navigation) -> Unit,
) {
    Setting(
        modifier = modifier,
        hubAction = hubAction,
    ) {
        NavHost(navController = settingNavHostController, startDestination = NavigationSettingRoute.Preference) {
            composable<NavigationSettingRoute.Preference> {
                Preference(
                    settingAction = settingViewModel.getSettingUiAction(),
                    hubAction = hubAction,
                    onTopNavigate = onTopNavigate, // logout
                    onSettingNavigate = { route: Navigation -> settingNavHostController.navigate(route) },
                )
            }
            composable<NavigationSettingRoute.UserInfo> {
                UserInfo(
                    hubState = hubState,
                    hubAction = hubAction,
                    settingState = settingViewModel.settingState,
                    settingAction = settingViewModel.getSettingUiAction(),
                    onNavigate = { route: Navigation -> settingNavHostController.navigate(route) },
                )
            }
            composable<NavigationSettingRoute.Posts> {
                MyPosts(
                    homeAction = homeAction,
                    hubState = hubState,
                    hubAction = hubAction,
                    tabUiState = tabUiState,
                    tabUiAction = tabUiAction,
                    postCardUiAction = postCardUiAction,
                    onHubNavigate = onHubNavigate,
                )
            }
        }
    }
}
