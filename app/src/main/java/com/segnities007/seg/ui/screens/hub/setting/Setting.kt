package com.segnities007.seg.ui.screens.hub.setting

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState
import com.segnities007.seg.ui.screens.hub.setting.preference.Preference
import com.segnities007.seg.ui.screens.hub.setting.userinfo.UserInformation

@Composable
fun Setting(
    modifier: Modifier,
    navController: NavHostController,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    settingViewModel: SettingViewModel = hiltViewModel(),
){

    when(settingViewModel.settingUiState.index){// TODO change index to enum
        0 -> Preference(
            modifier = modifier,
            navController = navController,
            settingUiAction = settingViewModel.getSettingUiAction()
        )
        1 -> UserInformation(
            modifier = modifier,
            settingUiState = settingViewModel.settingUiState,
            settingUiAction = settingViewModel.getSettingUiAction(),
            hubUiState = hubUiState,
            hubUiAction = hubUiAction,
        )
    }


}
