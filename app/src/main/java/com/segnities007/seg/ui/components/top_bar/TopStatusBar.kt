package com.segnities007.seg.ui.components.top_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.segnities007.seg.R
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.model.NavigationIndex
import com.segnities007.seg.ui.screens.hub.HubUiAction

@Composable
fun TopStatusBar(
    user: User,
    hubUiAction: HubUiAction,
    onClickFollowsButton: () -> Unit = {},
    onClickFollowersButton: () -> Unit = {},
    url: String = "https://avatars.githubusercontent.com/u/174174755?v=4",
    navigationIndex: NavigationIndex = NavigationIndex.No,
    onSettingClick: (index: Int) -> Unit = {},
){

    Box(
        modifier = Modifier
            .shadow(
                elevation = dimensionResource(R.dimen.elevation_large),
                shape = RoundedCornerShape(
                    bottomStart = dimensionResource(R.dimen.padding_large),
                    bottomEnd = dimensionResource(R.dimen.padding_large),
                )
            )
            .clip(
                shape = RoundedCornerShape(
                    bottomStart = dimensionResource(R.dimen.padding_large),
                    bottomEnd = dimensionResource(R.dimen.padding_large),
                )
            )
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_normal)),
    ){
        Column(
            Modifier.align(alignment = Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_nl)))
            AsyncImage(
                modifier = Modifier.size(dimensionResource(R.dimen.icon_large)).clip(CircleShape),
                model = url,
                contentDescription = url,
            )
            Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
            Status(user = user, hubUiAction = hubUiAction,  onClickFollowsButton = onClickFollowsButton, onClickFollowersButton = onClickFollowersButton, )
            Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
        }
        if(navigationIndex == NavigationIndex.HubSetting)
            SettingIconButton(Modifier.align(alignment = Alignment.TopEnd), onSettingClick = onSettingClick)
    }

}

@Composable
private fun Status(
    modifier: Modifier = Modifier,
    hubUiAction: HubUiAction,
    user: User,
    onClickFollowsButton: () -> Unit,
    onClickFollowersButton: () -> Unit,
    commonPadding: Dp = dimensionResource(R.dimen.padding_smaller),
    fontColor: Color = MaterialTheme.colorScheme.primary,
){

    Column(verticalArrangement = Arrangement.Center,){
        Column(
            modifier = Modifier.padding(horizontal = commonPadding)
        ){
                Text(text = "Name: ${user.name}", color = fontColor,)
                Text(text = "UserID: ${user.userID}", color = fontColor,)
        }
        Spacer(modifier = Modifier.padding(commonPadding))
        Row(horizontalArrangement = Arrangement.Center,){
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(commonPadding))
                    .clickable {
                        onClickFollowsButton()
                        hubUiAction.onNavigate(NavigationIndex.HubAccounts) }
                    .padding(commonPadding)
            ){ Text(text = "Follow: ${user.followCount}", color = fontColor,) }
            Spacer(modifier = Modifier.padding(commonPadding))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(commonPadding))
                    .clickable {
                        onClickFollowersButton()
                        hubUiAction.onNavigate(NavigationIndex.HubAccounts) }
                    .padding(commonPadding)
            ) { Text(text = "Follower: ${user.followerCount}", color = fontColor) }
        }
    }

}

@Composable
private fun SettingIconButton(
    modifier: Modifier = Modifier,
    onSettingClick: (index: Int) -> Unit,
){
    Column(
        modifier = modifier,
    ){
        Spacer(modifier = modifier.padding(dimensionResource(R.dimen.padding_normal)))
        IconButton(
            modifier = modifier,
            onClick  = {onSettingClick(1)},
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_settings_24),
                contentDescription = ""
            )
        }
    }

}