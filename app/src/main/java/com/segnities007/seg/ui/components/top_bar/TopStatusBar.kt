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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.segnities007.seg.R
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubUiAction

@Composable
fun TopStatusBar(
    user: User,
    hubUiAction: HubUiAction,
    isHideFollows: Boolean = false,
    onClickFollowsButton: () -> Unit = {},
    onClickFollowersButton: () -> Unit = {},
    onHubNavigate: (Route) -> Unit,
    commonPadding: Dp = dimensionResource(R.dimen.padding_small),
) {
    TopStatusCard(
        isHideFollows = isHideFollows,
    ) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_nl)))
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    modifier = Modifier.size(dimensionResource(R.dimen.icon_large)).clip(CircleShape),
                    model = user.iconURL,
                    contentDescription = user.iconURL,
                )
                Spacer(modifier = Modifier.padding(commonPadding))
                Status(user = user)
            }
            Spacer(modifier = Modifier.padding(commonPadding))
            if (!isHideFollows) {
                AboutFollow(
                    user = user,
                    onHubNavigate = onHubNavigate,
                    hubUiAction = hubUiAction,
                    onClickFollowsButton = onClickFollowsButton,
                    onClickFollowersButton = onClickFollowersButton,
                )
                Spacer(modifier = Modifier.padding(commonPadding))
            }
        }
    }
}

@Composable
private fun TopStatusCard(
    isHideFollows: Boolean,
    content: @Composable () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .run {
                    if (!isHideFollows) {
                        this
                            .shadow(
                                elevation = dimensionResource(R.dimen.elevation_normal),
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
                            )
                    } else {
                        this
                    }
                }.background(color = MaterialTheme.colorScheme.primaryContainer),
    ) {
        content()
    }
}

@Composable
private fun Status(
    modifier: Modifier = Modifier,
    user: User,
    commonFontSize: TextUnit = 24.sp,
    commonPadding: Dp = dimensionResource(R.dimen.padding_smaller),
    fontColor: Color = MaterialTheme.colorScheme.primary,
) {
    Row(
        modifier = Modifier.padding(horizontal = commonPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = user.name, color = fontColor, fontSize = commonFontSize)
//        Spacer(Modifier.padding(commonPadding))
        Text(text = "@${user.userID}", color = fontColor, fontSize = commonFontSize)
    }
}

@Composable
private fun AboutFollow(
    modifier: Modifier = Modifier,
    user: User,
    commonPadding: Dp = dimensionResource(R.dimen.padding_smaller),
    hubUiAction: HubUiAction,
    fontColor: Color = MaterialTheme.colorScheme.primary,
    onHubNavigate: (Route) -> Unit,
    onClickFollowsButton: () -> Unit,
    onClickFollowersButton: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Box(
            modifier =
                Modifier
                    .clip(RoundedCornerShape(commonPadding))
                    .clickable {
                        onClickFollowsButton()
                        hubUiAction.onSetAccounts(user.follows)
                        onHubNavigate(NavigationHubRoute.Accounts())
                    }.padding(commonPadding),
        ) { Text(text = "Follow: ${user.followCount}", color = fontColor) }
        Box(
            modifier =
                Modifier
                    .clip(RoundedCornerShape(commonPadding))
                    .clickable {
                        onClickFollowersButton()
                        hubUiAction.onSetAccounts(user.followers)
                        onHubNavigate(NavigationHubRoute.Accounts())
                    }.padding(commonPadding),
        ) { Text(text = "Follower: ${user.followerCount}", color = fontColor) }
    }
}
