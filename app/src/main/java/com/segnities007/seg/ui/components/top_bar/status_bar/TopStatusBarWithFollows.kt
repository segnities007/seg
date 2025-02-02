package com.segnities007.seg.ui.components.top_bar.status_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.segnities007.seg.R
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubUiAction

@Composable
fun TopStatusBarWithFollows(
    user: User,
    onHubNavigate: (Route) -> Unit,
    hubUiAction: HubUiAction,
    commonPadding: Dp = dimensionResource(R.dimen.padding_small),
) {
    val shape = dimensionResource(R.dimen.padding_large)

    TopStatusBar(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                shape = RoundedCornerShape(
                    bottomStart = shape,
                    bottomEnd = shape,
                ),
                elevation = dimensionResource(R.dimen.elevation_nl),
            )
            .clip(
                shape = RoundedCornerShape(
                    bottomStart = shape,
                    bottomEnd = shape,
                )
            ),
        user = user,
        ) {
        AboutFollow(user = user, onHubNavigate = onHubNavigate, hubUiAction = hubUiAction)
        Spacer(modifier = Modifier.padding(commonPadding))
    }
}

@Composable
private fun AboutFollow(
    user: User,
    commonPadding: Dp = dimensionResource(R.dimen.padding_smaller),
    hubUiAction: HubUiAction,
    fontColor: Color = MaterialTheme.colorScheme.primary,
    onHubNavigate: (Route) -> Unit,
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
                        hubUiAction.onSetAccounts(user.follows)
                        onHubNavigate(NavigationHubRoute.Accounts())
                    }.padding(commonPadding),
        ) {
            Text(text = "Follow: ${user.followCount}", color = fontColor)
        }
        Box(
            modifier =
                Modifier
                    .clip(RoundedCornerShape(commonPadding))
                    .clickable {
                        hubUiAction.onSetAccounts(user.followers)
                        onHubNavigate(NavigationHubRoute.Accounts())
                    }.padding(commonPadding),
        ) {
            Text(text = "Follower: ${user.followerCount}", color = fontColor)
        }
    }
}
