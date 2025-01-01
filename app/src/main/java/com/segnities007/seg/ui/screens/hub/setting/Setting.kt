package com.segnities007.seg.ui.screens.hub.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import com.segnities007.seg.R
import com.segnities007.seg.ui.components.button.BasicButton
import com.segnities007.seg.ui.screens.hub.HubViewModel
import com.segnities007.seg.ui.screens.hub.SettingUiAction
import com.segnities007.seg.ui.screens.hub.SettingUiState
import java.time.LocalDate

@Composable
fun Setting(
    modifier: Modifier,
    navController: NavHostController,
    settingUiState: SettingUiState,
    settingUiAction: SettingUiAction,
    commonPadding: Dp = dimensionResource(R.dimen.padding_normal),
){
    Column(
        modifier = modifier.fillMaxSize().padding(commonPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ){
        StatusCard(settingUiState = settingUiState)
        Spacer(modifier = Modifier.padding(commonPadding))
        FollowsButton()
        Spacer(modifier = Modifier.padding(commonPadding))
        LogoutButton(navController = navController, settingUiAction = settingUiAction)
    }
}

@Composable
private fun StatusCard(
    modifier: Modifier = Modifier,
    settingUiState: SettingUiState,
    commonPadding: Dp = dimensionResource(R.dimen.padding_normal)
){
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation_large)),
    ){
        Column(
            modifier = Modifier.padding(commonPadding),
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Image(
                    modifier = Modifier.clip(CircleShape),
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = "TODO"
                )
                Spacer(modifier = Modifier.padding(commonPadding))
                Status(settingUiState = settingUiState)
            }
        }

    }
}

@Composable
private fun Status(
    modifier: Modifier = Modifier,
    settingUiState: SettingUiState,
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ){
        Text(text = "Name: ${settingUiState.user.name}")
        Text(text = "UserID: ${settingUiState.user.userID}")
        Text(text = "Registration: ${settingUiState.user.createAt}")
        Text(text = "Follow: ${settingUiState.user.followCount}")
        Text(text = "Follower: ${settingUiState.user.followerCount}")
    }
}

@Composable
private fun FollowsButton(
    modifier: Modifier = Modifier,
    commonPadding: Dp = dimensionResource(R.dimen.padding_small),
    buttonSize: Dp = dimensionResource(R.dimen.button_height_size),
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ){
        BasicButton(modifier = Modifier.weight(1f).height(buttonSize), textID = R.string.follows, onClick = {/*TODO*/})
        Spacer(modifier = Modifier.padding(commonPadding))
        BasicButton(modifier = Modifier.weight(1f).height(buttonSize), textID = R.string.followers, onClick = {/*TODO*/})
    }
}

@Composable
private fun LogoutButton(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    settingUiAction: SettingUiAction,
){
    BasicButton(modifier = Modifier.fillMaxWidth(), textID = R.string.logout, onClick = {settingUiAction.onLogout(navController)})
}
