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
import com.segnities007.seg.ui.components.button.SmallButton
import com.segnities007.seg.ui.screens.hub.setting.SettingUiAction

@Composable
fun Preference(
    modifier: Modifier,
    navController: NavHostController,
    settingUiAction: SettingUiAction,
    commonPadding: Dp = dimensionResource(R.dimen.padding_normal),
){
    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = commonPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ){
        Spacer(modifier = Modifier.padding(commonPadding))
        FollowsButton()
        Spacer(modifier = Modifier.padding(commonPadding))
        LogoutButton(navController = navController, settingUiAction = settingUiAction)
    }
}

@Composable
private fun FollowsButton(
    modifier: Modifier = Modifier,
    commonPadding: Dp = dimensionResource(R.dimen.padding_small),
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ){
        SmallButton(modifier = Modifier.weight(1f), textID = R.string.follows, onClick = {/*TODO*/})
        Spacer(modifier = Modifier.padding(commonPadding))
        SmallButton(modifier = Modifier.weight(1f), textID = R.string.followers, onClick = {/*TODO*/})
    }
}

@Composable
private fun LogoutButton(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    settingUiAction: SettingUiAction,
){
    SmallButton(modifier = Modifier.fillMaxWidth(), textID = R.string.logout, onClick = {settingUiAction.onLogout(navController)})
}
