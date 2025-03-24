package com.segnities007.seg.ui.screens.hub.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.segnities007.seg.R
import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.components.button.SmallButton
import com.segnities007.seg.ui.components.card.postcard.PostCard
import com.segnities007.seg.ui.components.card.postcard.PostCardUiAction
import com.segnities007.seg.ui.components.indicator.LoadingUI
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState

@Composable
fun Account(
    modifier: Modifier = Modifier,
    hubState: HubState,
    hubAction: HubAction,
    accountUiFlagState: AccountUiFlagState,
    accountState: AccountState,
    accountAction: AccountAction,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Navigation) -> Unit,
) {
    LaunchedEffect(Unit) {
        accountAction.onInitAccountUiState(hubState.userID)
    }

    DisposableEffect(Unit) {
        onDispose {
            accountAction.onReset()
        }
    }

    AccountUi(
        modifier = modifier,
        hubState = hubState,
        hubAction = hubAction,
        accountUiFlagState = accountUiFlagState,
        accountState = accountState,
        accountAction = accountAction,
        postCardUiAction = postCardUiAction,
        onHubNavigate = onHubNavigate,
    )
}

@Composable
private fun AccountUi(
    modifier: Modifier = Modifier,
    hubState: HubState,
    hubAction: HubAction,
    accountUiFlagState: AccountUiFlagState,
    accountState: AccountState,
    accountAction: AccountAction,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Navigation) -> Unit,
) {
    LazyColumn(
        modifier =
            modifier
                .fillMaxSize()
                .padding(
                    top = dimensionResource(R.dimen.padding_smallest),
                    start = dimensionResource(R.dimen.padding_small),
                    end = dimensionResource(R.dimen.padding_small),
                ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        if (hubState.user.userID != accountState.user.userID) {
            item {
                FollowButtons(
                    hubState = hubState,
                    hubAction = hubAction,
                    accountUiFlagState = accountUiFlagState,
                    accountState = accountState,
                    accountAction = accountAction,
                )
            }
        }
        items(
            accountState.posts.size,
            key = { index: Int -> accountState.posts[index].id },
        ) { i ->
            PostCard(
                post = accountState.posts[i],
                hubState = hubState,
                onHubNavigate = onHubNavigate,
                hubAction = hubAction,
                postCardUiAction = postCardUiAction,
                onProcessOfEngagementAction = accountAction.onProcessOfEngagementAction,
            )
            Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smallest)))
        }
        // action for fetching before-post
        if (!accountUiFlagState.isCompletedFetchPosts) {
            item {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(
                        onLoading = {
                            accountAction.onGetPosts()
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun FollowButtons(
    modifier: Modifier = Modifier,
    hubState: HubState,
    hubAction: HubAction,
    accountUiFlagState: AccountUiFlagState,
    accountState: AccountState,
    accountAction: AccountAction,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_normal)))
        SmallButton(
            modifier = Modifier.weight(1f),
            isLoading = accountUiFlagState.isLoading,
            textID =
                if (hubState.user.follows.contains(accountState.user.userID)) R.string.followed else R.string.follow,
            onClick = {
                accountAction.onToggleIsLoading()
                accountAction.onFollow(
                    hubState.user.follows.contains(accountState.user.userID),
                    hubState.user,
                    accountState.user,
                    hubAction.onGetUser,
                    accountAction.onToggleIsLoading,
                )
            },
        )
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_normal)))
    }
}
