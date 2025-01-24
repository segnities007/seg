package com.segnities007.seg.ui.screens.hub.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.segnities007.seg.R
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.components.button.SmallButton
import com.segnities007.seg.ui.components.card.EngagementIconAction
import com.segnities007.seg.ui.components.card.EngagementIconState
import com.segnities007.seg.ui.components.card.PostCard
import com.segnities007.seg.ui.components.card.PostCardUiAction
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState

@Composable
fun Account(
    modifier: Modifier = Modifier,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    accountUiState: AccountUiState,
    accountUiAction: AccountUiAction,
    engagementIconState: EngagementIconState,
    engagementIconAction: EngagementIconAction,
    postCardUiAction: PostCardUiAction,
    commonPadding: Dp = dimensionResource(R.dimen.padding_normal),
    onHubNavigate: (Route) -> Unit,
) {
    LaunchedEffect(Unit) {
        accountUiAction.onGetOtherUser(hubUiState.userID)
        accountUiAction.onGetUserPosts(hubUiState.userID)
    }

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (hubUiState.user.userID != accountUiState.user.userID) {
            Spacer(modifier = Modifier.padding(commonPadding))
            FollowButtons(
                hubUiState = hubUiState,
                hubUiAction = hubUiAction,
                accountUiState = accountUiState,
                accountUiAction = accountUiAction,
            )
            Spacer(modifier = Modifier.padding(commonPadding))
        }

        for (i in 0 until accountUiState.posts.size) {
            PostCard(
                post = accountUiState.posts[i],
                myself = hubUiState.user,
                hubUiAction = hubUiAction,
                onHubNavigate = onHubNavigate,
                engagementIconState = engagementIconState,
                engagementIconAction = engagementIconAction,
                postCardUiAction = postCardUiAction,
            )
        }
    }
}

@Composable
private fun FollowButtons(
    modifier: Modifier = Modifier,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    accountUiState: AccountUiState,
    accountUiAction: AccountUiAction,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_normal)))
        SmallButton(
            modifier = Modifier.weight(1f),
            textID =
                if (
                    hubUiState.user.follows.contains(accountUiState.user.userID)
                ) {
                    R.string.followed
                } else {
                    R.string.follow
                },
            onClick = {
                // リストがNullじゃないかつ、IDがある場合unfollow
                if (hubUiState.user.follows.contains(accountUiState.user.userID)) {
                    accountUiAction.onUnFollow(hubUiState.user, accountUiState.user, hubUiAction.onGetUser)
                } else {
                    accountUiAction.onFollow(hubUiState.user, accountUiState.user, hubUiAction.onGetUser)
                }
            },
        )
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_normal)))
    }
}
