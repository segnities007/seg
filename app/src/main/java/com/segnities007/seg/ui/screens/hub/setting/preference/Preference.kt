package com.segnities007.seg.ui.screens.hub.setting.preference

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.segnities007.seg.R
import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.components.button.SmallButton
import com.segnities007.seg.ui.navigation.NavigationRoute
import com.segnities007.seg.ui.navigation.hub.setting.NavigationSettingRoute
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.setting.SettingUiAction

@Composable
fun Preference(
    modifier: Modifier = Modifier,
    settingUiAction: SettingUiAction,
    hubUiAction: HubUiAction,
    commonPadding: Dp = dimensionResource(R.dimen.padding_normal),
    onSettingNavigate: (Navigation) -> Unit,
    onTopNavigate: (Navigation) -> Unit,
) {
    LaunchedEffect(Unit) {
        hubUiAction.onResetIsHideTopBar()
    }

    Column(
        modifier = modifier.padding(horizontal = dimensionResource(R.dimen.padding_nl)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Spacer(modifier = Modifier.padding(commonPadding))
        ShowMyPostsButton(onSettingNavigate = onSettingNavigate)
        Spacer(modifier = Modifier.padding(commonPadding))
        ModifyUserInfoButton(onSettingNavigate = onSettingNavigate)
        Spacer(modifier = Modifier.padding(commonPadding))
        LogoutButton(settingUiAction = settingUiAction, onTopNavigate = onTopNavigate)
    }
}

@Composable
private fun ShowMyPostsButton(onSettingNavigate: (Navigation) -> Unit) {
    SmallButton(
        modifier = Modifier.fillMaxWidth(),
        textID = R.string.my_posts,
        onClick = {
            onSettingNavigate(NavigationSettingRoute.Posts)
        },
    )
}

@Composable
private fun ModifyUserInfoButton(onSettingNavigate: (Navigation) -> Unit) {
    SmallButton(
        modifier = Modifier.fillMaxWidth(),
        textID = R.string.user_info,
        onClick = {
            onSettingNavigate(NavigationSettingRoute.UserInfo)
        },
    )
}

@Composable
private fun LogoutButton(
    settingUiAction: SettingUiAction,
    onTopNavigate: (Navigation) -> Unit,
) {
    SmallButton(
        modifier = Modifier.fillMaxWidth(),
        textID = R.string.logout,
        onClick = {
            settingUiAction.onLogout()
            onTopNavigate(NavigationRoute.Login)
        },
    )
}
