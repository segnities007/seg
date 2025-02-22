package com.segnities007.seg.ui.components.bar.status_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.segnities007.seg.R
import com.segnities007.seg.data.model.User
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubUiAction

@Composable
fun StatusBarWithFollows(
    modifier: Modifier = Modifier,
    user: User,
    onHubNavigate: (NavigationHubRoute) -> Unit,
    hubUiAction: HubUiAction,
) {
    Column(
        modifier = modifier,
    ) {
        StatusBarUi(
            modifier = modifier,
            user = user,
        ) {
            Column {
                AboutFollow(hubUiAction = hubUiAction, onHubNavigate = onHubNavigate)
            }
        }
        Bottom()
    }
}

@Composable
private fun StatusBarScope.AboutFollow(
    hubUiAction: HubUiAction,
    onHubNavigate: (NavigationHubRoute) -> Unit,
) {
    val fontColor: Color = MaterialTheme.colorScheme.primary
    val followsText = stringResource(R.string.follows) + ": ${user.followCount}"
    val followersText = stringResource(R.string.followers) + ": ${user.followerCount}"

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Box(
            modifier =
                Modifier
                    .clip(RoundedCornerShape(commonPadding))
                    .clickable {
                        hubUiAction.onSetAccounts(user.follows)
                        onHubNavigate(NavigationHubRoute.Accounts)
                    }.padding(commonPadding),
        ) {
            Text(text = followsText, color = fontColor)
        }
        Box(
            modifier =
                Modifier
                    .clip(RoundedCornerShape(commonPadding))
                    .clickable {
                        hubUiAction.onSetAccounts(user.followers)
                        onHubNavigate(NavigationHubRoute.Accounts)
                    }.padding(commonPadding),
        ) {
            Text(text = followersText, color = fontColor)
        }
    }
}

@Composable
@Preview
private fun StatusBarWithFollowsPreview() {
    StatusBarWithFollows(
        modifier = Modifier,
        user = User(),
        onHubNavigate = {},
        hubUiAction =
            HubUiAction(
                onUpdateSelf = {},
                onChangeIsHideTopBar = {},
                onResetIsHideTopBar = {},
                onGetUser = {},
                onSetComment = {},
                onSetUserID = {},
                onSetAccounts = {},
                onAddPostIDToMyLikes = {},
                onRemovePostIDFromMyLikes = {},
                onAddPostIDToMyReposts = {},
                onRemovePostIDFromMyReposts = {},
                onChangeCurrentRouteName = {},
            ),
    )
}
