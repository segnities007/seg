package com.example.feature.screens.hub.setting.preference

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
import com.example.domain.presentation.navigation.Navigation
import com.example.domain.presentation.navigation.NavigationRoute
import com.example.domain.presentation.navigation.NavigationSettingRoute
import com.example.feature.R
import com.example.feature.components.button.SmallButton
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.setting.SettingAction

@Composable
fun Preference(
    modifier: Modifier = Modifier,
    onHubAction: (HubAction) -> Unit,
    onSettingAction: (SettingAction) -> Unit,
    onSettingNavigate: (Navigation) -> Unit,
    onTopNavigate: (Navigation) -> Unit,
) {
    val commonPadding: Dp = dimensionResource(R.dimen.padding_normal)

    LaunchedEffect(Unit) {
        onHubAction(HubAction.ResetIsHideTopBar)
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
        LogoutButton(onSettingAction = onSettingAction, onTopNavigate = onTopNavigate)
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
    onSettingAction: (SettingAction) -> Unit,
    onTopNavigate: (Navigation) -> Unit,
) {
    SmallButton(
        modifier = Modifier.fillMaxWidth(),
        textID = R.string.logout,
        onClick = {
            onSettingAction(SettingAction.Logout)
            onTopNavigate(NavigationRoute.Login)
        },
    )
}
