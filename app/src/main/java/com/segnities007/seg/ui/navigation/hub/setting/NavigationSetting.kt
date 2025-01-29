package com.segnities007.seg.ui.navigation.hub.setting

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.components.card.postcard.EngagementIconAction
import com.segnities007.seg.ui.components.card.postcard.EngagementIconState
import com.segnities007.seg.ui.components.card.postcard.PostCardUiAction
import com.segnities007.seg.ui.components.card.postcard.PostCardUiState
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState
import com.segnities007.seg.ui.screens.hub.setting.Setting
import com.segnities007.seg.ui.screens.hub.setting.SettingViewModel
import com.segnities007.seg.ui.screens.hub.setting.posts.Posts
import com.segnities007.seg.ui.screens.hub.setting.preference.Preference
import com.segnities007.seg.ui.screens.hub.setting.userinfo.UserInfo

@Composable
fun NavigationSetting(
    modifier: Modifier = Modifier,
    settingNavHostController: NavHostController = rememberNavController(),
    settingViewModel: SettingViewModel = hiltViewModel(),
    postCardUiState: PostCardUiState,
    postCardUiAction: PostCardUiAction,
    engagementIconState: EngagementIconState,
    engagementIconAction: EngagementIconAction,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    onTopNavigate: (Route) -> Unit,
    onHubNavigate: (Route) -> Unit,
) {
    Setting(
        modifier = modifier,
        onSettingNavigate = { route: Route ->
            settingNavHostController.navigate(route)
        },
        hubUiAction = hubUiAction,
    ) {
        NavHost(navController = settingNavHostController, startDestination = NavigationSettingRoute.Preference()) {
            composable<NavigationSettingRoute.Preference> {
                Preference(
                    settingUiAction = settingViewModel.getSettingUiAction(),
                    onTopNavigate = onTopNavigate, // logout
                    hubUiState = hubUiState,
                    onSettingNavigate = { route: Route -> settingNavHostController.navigate(route) },
                    postCardUiAction = postCardUiAction,
                )
            }
            composable<NavigationSettingRoute.UserInfo> {
                UserInfo(
                    hubUiState = hubUiState,
                    hubUiAction = hubUiAction,
                    settingUiState = settingViewModel.settingUiState,
                    settingUiAction = settingViewModel.getSettingUiAction(),
                    onNavigate = { route: Route -> settingNavHostController.navigate(route) },
                )
            }
            composable<NavigationSettingRoute.Posts> {
                Posts(
                    hubUiState = hubUiState,
                    hubUiAction = hubUiAction,
                    engagementIconState = engagementIconState,
                    engagementIconAction = engagementIconAction,
                    postCardUiAction = postCardUiAction,
                    onHubNavigate = onHubNavigate,
                    postCardUiState = postCardUiState,
                )
            }
        }
    }
}
