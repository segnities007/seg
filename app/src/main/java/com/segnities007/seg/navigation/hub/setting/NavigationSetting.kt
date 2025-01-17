package com.segnities007.seg.navigation.hub.setting

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState
import com.segnities007.seg.ui.screens.hub.account.AccountUiAction
import com.segnities007.seg.ui.screens.hub.setting.Setting
import com.segnities007.seg.ui.screens.hub.setting.SettingViewModel
import com.segnities007.seg.ui.screens.hub.setting.preference.Preference
import com.segnities007.seg.ui.screens.hub.setting.userinfo.UserInfo

@Composable
fun NavigationSetting(
    modifier: Modifier = Modifier,
    settingNavHostController: NavHostController = rememberNavController(),
    settingViewModel: SettingViewModel = hiltViewModel(),
    accountUiAction: AccountUiAction,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    onHubNavigate: (Route) -> Unit,
    onTopNavigate: (Route) -> Unit, // go to login
) {
    Setting(
        modifier = modifier,
        onSettingNavigate = { route: Route -> settingNavHostController.navigate(route) },
        onHubNavigate = onHubNavigate,
        hubUiState = hubUiState,
        hubUiAction = hubUiAction,
        accountUiAction = accountUiAction,
    ) {
        NavHost(navController = settingNavHostController, startDestination = NavigationSettingRoute.Preference) {
            composable<NavigationSettingRoute.Preference> {
                Preference(
                    settingUiAction = settingViewModel.getSettingUiAction(),
                    onTopNavigate = onTopNavigate, // logout
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
        }
    }
}
