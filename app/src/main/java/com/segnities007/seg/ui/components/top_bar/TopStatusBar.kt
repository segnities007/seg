package com.segnities007.seg.ui.components.top_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import coil3.compose.AsyncImage
import com.segnities007.seg.R
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.navigation.hub.NavigationHubRoute
import com.segnities007.seg.navigation.hub.setting.NavigationSettingRoute

@Composable
fun TopStatusBar(
    user: User,
    currentRouteName: String,
    url: String = "https://avatars.githubusercontent.com/u/174174755?v=4",
    onClickFollowsButton: () -> Unit = {},
    onClickFollowersButton: () -> Unit = {},
    onHubNavigate: (Route) -> Unit,
    onSettingNavigate: (Route) -> Unit = {}, // UserInfoに遷移したい場合にのみ使用
) {
    Box(
        modifier =
            Modifier
                .shadow(
                    elevation = dimensionResource(R.dimen.elevation_large),
                    shape =
                        RoundedCornerShape(
                            bottomStart = dimensionResource(R.dimen.padding_large),
                            bottomEnd = dimensionResource(R.dimen.padding_large),
                        ),
                ).clip(
                    shape =
                        RoundedCornerShape(
                            bottomStart = dimensionResource(R.dimen.padding_large),
                            bottomEnd = dimensionResource(R.dimen.padding_large),
                        ),
                ).background(color = MaterialTheme.colorScheme.primaryContainer)
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_normal)),
    ) {
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
            Status(
                user = user,
                onClickFollowsButton = onClickFollowsButton,
                onClickFollowersButton = onClickFollowersButton,
                onHubNavigate = onHubNavigate,
            )
            Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
        }
        if (currentRouteName == NavigationHubRoute.Setting.routeName) {
            SettingIconButton(
                Modifier.align(alignment = Alignment.TopEnd),
                onSettingNavigate = onSettingNavigate,
            )
        }
    }
}

@Composable
private fun Status(
    modifier: Modifier = Modifier,
    user: User,
    commonPadding: Dp = dimensionResource(R.dimen.padding_smaller),
    fontColor: Color = MaterialTheme.colorScheme.primary,
    onHubNavigate: (Route) -> Unit,
    onClickFollowsButton: () -> Unit,
    onClickFollowersButton: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.Center) {
        Column(
            modifier = Modifier.padding(horizontal = commonPadding),
        ) {
            Text(text = "Name: ${user.name}", color = fontColor)
            Text(text = "UserID: ${user.userID}", color = fontColor)
        }
        Spacer(modifier = Modifier.padding(commonPadding))
        Row(horizontalArrangement = Arrangement.Center) {
            Box(
                modifier =
                    Modifier
                        .clip(RoundedCornerShape(commonPadding))
                        .clickable {
                            onClickFollowsButton()
                            onHubNavigate(NavigationHubRoute.Accounts)
                        }.padding(commonPadding),
            ) { Text(text = "Follow: ${user.followCount}", color = fontColor) }
            Spacer(modifier = Modifier.padding(commonPadding))
            Box(
                modifier =
                    Modifier
                        .clip(RoundedCornerShape(commonPadding))
                        .clickable {
                            onClickFollowersButton()
                            onHubNavigate(NavigationHubRoute.Accounts)
                        }.padding(commonPadding),
            ) { Text(text = "Follower: ${user.followerCount}", color = fontColor) }
        }
    }
}

@Composable
private fun SettingIconButton(
    modifier: Modifier = Modifier,
    onSettingNavigate: (Route) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Spacer(modifier = modifier.padding(dimensionResource(R.dimen.padding_normal)))
        IconButton(
            modifier = modifier,
            onClick = { onSettingNavigate(NavigationSettingRoute.UserInfo) },
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_settings_24),
                contentDescription = "",
            )
        }
    }
}
