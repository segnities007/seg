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
        modifier = modifier
            .fillMaxSize()
            .padding(commonPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ){
        StatusCard()
        Spacer(modifier = Modifier.padding(commonPadding))
        FollowsButton()
        Spacer(modifier = Modifier.padding(commonPadding))
        LogoutButton(navController = navController, settingUiAction = settingUiAction)
    }
}

@Composable
private fun StatusCard(
    modifier: Modifier = Modifier,
    commonPadding: Dp = dimensionResource(R.dimen.padding_normal)
){
    Card(modifier = Modifier.fillMaxWidth()){
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
                Status()
            }
        }

    }
}

@Composable
private fun Status(
    modifier: Modifier = Modifier,
    name: String = stringResource(R.string.no_name),
    userID: String = stringResource(R.string.no_user_id),
    registration: LocalDate = LocalDate.now(),
    follow: Int = 0,
    follower: Int = 0,
){
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start){
        Text(text = "Name: $name")
        Text(text = "UserID: $userID")
        Text(text = "Registration: $registration")
        Text(text = "Follow: $follow")
        Text(text = "Follower: $follower")
    }
}

@Composable
private fun FollowsButton(
    modifier: Modifier = Modifier,
    commonPadding: Dp = dimensionResource(R.dimen.padding_normal),
    buttonSize: Dp = dimensionResource(R.dimen.button_height_size),
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = commonPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ){
        ElevatedButton(modifier = Modifier.weight(1f).height(buttonSize), onClick = {/*TODO*/}) {
            Text("About Follow")
        }
        Spacer(modifier = Modifier.padding(commonPadding))
        ElevatedButton(modifier = Modifier.weight(1f).height(buttonSize), onClick = {/*TODO*/}) {
            Text("About Follower")
        }
    }
}

@Composable
private fun LogoutButton(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    settingUiAction: SettingUiAction,
    commonPadding: Dp = dimensionResource(R.dimen.padding_normal),
    buttonSize: Dp = dimensionResource(R.dimen.button_height_size)
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = commonPadding),
        contentAlignment = Alignment.Center,
    ){
        ElevatedButton(
            modifier = Modifier.fillMaxWidth().height(buttonSize),
            onClick = { settingUiAction.onLogout(navController) }
        ) {
            Text(text = stringResource(R.string.logout))
        }
    }
}
