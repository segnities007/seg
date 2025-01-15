package com.segnities007.seg.ui.screens.hub.setting

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.components.top_bar.TopStatusBar
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState
import com.segnities007.seg.ui.screens.hub.account.AccountUiAction

@Composable
fun Setting(
    modifier: Modifier,
    onNavigate: (Route) -> Unit,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    accountUiAction: AccountUiAction,
    content: @Composable () -> Unit,
){

    LaunchedEffect(Unit) {
        hubUiAction.onGetUser()
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopStatusBar(
            user = hubUiState.user,
            onClickFollowsButton = {
                if (!hubUiState.user.follows.isNullOrEmpty())
                    accountUiAction.onSetUsers(hubUiState.user.follows)
            },
            onClickFollowersButton = {
                if (!hubUiState.user.followers.isNullOrEmpty())
                    accountUiAction.onSetUsers(hubUiState.user.followers)
            },
            onNavigate = onNavigate,//go to userInfo
            currentRouteName = NavigationHubRoute.Setting.routeName,
        )

        content()
    }
}
