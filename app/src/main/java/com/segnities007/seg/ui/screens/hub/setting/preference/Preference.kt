package com.segnities007.seg.ui.screens.hub.setting.preference

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import com.segnities007.seg.R
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.navigation.NavigationRoute
import com.segnities007.seg.ui.components.button.SmallButton
import com.segnities007.seg.ui.screens.hub.setting.SettingUiAction

@Composable
fun Preference(
    modifier: Modifier = Modifier,
    settingUiAction: SettingUiAction,
    commonPadding: Dp = dimensionResource(R.dimen.padding_normal),
    onTopNavigate: (Route) -> Unit,
){
    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = commonPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ){
        Spacer(modifier = Modifier.padding(commonPadding))
        LogoutButton(settingUiAction = settingUiAction, onTopNavigate = onTopNavigate)
    }
}

@Composable
private fun LogoutButton(
    modifier: Modifier = Modifier,
    settingUiAction: SettingUiAction,
    onTopNavigate: (Route) -> Unit,
){
    SmallButton(
        modifier = Modifier.fillMaxWidth(),
        textID = R.string.logout,
        onClick = {
            settingUiAction.onLogout()
            onTopNavigate(NavigationRoute.Login)
        }
    )
}
