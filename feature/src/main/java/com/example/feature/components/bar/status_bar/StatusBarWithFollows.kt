package com.example.feature.components.bar.status_bar

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
import com.example.domain.model.User
import com.example.domain.presentation.navigation.NavigationHubRoute
import com.example.feature.R
import com.example.feature.screens.hub.HubAction

@Composable
fun StatusBarWithFollows(
    modifier: Modifier = Modifier,
    user: User,
    onHubNavigate: (NavigationHubRoute) -> Unit,
    onHubAction: (HubAction) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        StatusBarUi(
            modifier = modifier,
            user = user,
        ) {
            Column {
                AboutFollow(onHubAction = onHubAction, onHubNavigate = onHubNavigate)
            }
        }
        Bottom()
    }
}

@Composable
private fun StatusBarScope.AboutFollow(
    onHubAction: (HubAction) -> Unit,
    onHubNavigate: (NavigationHubRoute) -> Unit,
) {
    val fontColor: Color = MaterialTheme.colorScheme.primary
    val followsText = stringResource(R.string.follows) + ": ${user.follows.count { it != "" }}"
    val followersText =
        stringResource(R.string.followers) + ": ${user.followers.count { it != "" }}"

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Box(
            modifier =
                Modifier
                    .clip(RoundedCornerShape(commonPadding))
                    .clickable {
                        onHubAction(HubAction.SetAccounts(user.follows))
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
                        onHubAction(HubAction.SetAccounts(user.followers))
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
        onHubAction = {},
    )
}
