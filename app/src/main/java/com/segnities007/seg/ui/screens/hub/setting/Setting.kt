package com.segnities007.seg.ui.screens.hub.setting

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.segnities007.seg.domain.model.NavigationIndex
import com.segnities007.seg.ui.components.top_bar.TopStatusBar
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState
import com.segnities007.seg.ui.screens.hub.account.AccountUiAction
import com.segnities007.seg.ui.screens.hub.account.AccountUiState
import com.segnities007.seg.ui.screens.hub.setting.preference.Preference
import com.segnities007.seg.ui.screens.hub.setting.userinfo.UserInformation

@Composable
fun Setting(
    modifier: Modifier,
    navController: NavHostController,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    accountUiAction: AccountUiAction,
    settingViewModel: SettingViewModel = hiltViewModel(),
){

    LaunchedEffect(Unit) {
        hubUiAction.onGetUser()
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        TopStatusBar(
            user = hubUiState.user,
            onSettingClick = { index: Int ->
                settingViewModel.getSettingUiAction().onIndexChange(index)
            },
            onClickFollowsButton = {
                if (!hubUiState.user.follows.isNullOrEmpty())
                    accountUiAction.onSetUsers(hubUiState.user.follows)
                Log.d("setting", "${hubUiState.user.follows}")
            },
            onClickFollowersButton = {
                if (!hubUiState.user.followers.isNullOrEmpty())
                    accountUiAction.onSetUsers(hubUiState.user.followers)
            },
            navigationIndex = NavigationIndex.HubSetting,
            hubUiAction = hubUiAction,
        )
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


}
