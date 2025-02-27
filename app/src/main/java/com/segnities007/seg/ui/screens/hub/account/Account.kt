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
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState

@Composable
fun Account(
    modifier: Modifier = Modifier,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    accountUiFlagState: AccountUiFlagState,
    accountUiState: AccountUiState,
    accountUiAction: AccountUiAction,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Navigation) -> Unit,
) {
    LaunchedEffect(Unit) {
        accountUiAction.onInitAccountUiState(hubUiState.userID)
    }

    DisposableEffect(Unit) {
        onDispose {
            accountUiAction.onReset()
        }
    }

    AccountUi(
        modifier = modifier,
        hubUiState = hubUiState,
        hubUiAction = hubUiAction,
        accountUiFlagState = accountUiFlagState,
        accountUiState = accountUiState,
        accountUiAction = accountUiAction,
        postCardUiAction = postCardUiAction,
        onHubNavigate = onHubNavigate,
    )
}

@Composable
private fun AccountUi(
    modifier: Modifier = Modifier,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    accountUiFlagState: AccountUiFlagState,
    accountUiState: AccountUiState,
    accountUiAction: AccountUiAction,
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
        if (hubUiState.user.userID != accountUiState.user.userID) {
            item {
                FollowButtons(
                    hubUiState = hubUiState,
                    hubUiAction = hubUiAction,
                    accountUiFlagState = accountUiFlagState,
                    accountUiState = accountUiState,
                    accountUiAction = accountUiAction,
                )
            }
        }
        items(
            accountUiState.posts.size,
            key = { index: Int -> accountUiState.posts[index].id },
        ) { i ->
            PostCard(
                post = accountUiState.posts[i],
                hubUiState = hubUiState,
                onHubNavigate = onHubNavigate,
                hubUiAction = hubUiAction,
                postCardUiAction = postCardUiAction,
                onProcessOfEngagementAction = accountUiAction.onProcessOfEngagementAction,
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
                            accountUiAction.onGetPosts()
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
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    accountUiFlagState: AccountUiFlagState,
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
            isLoading = accountUiFlagState.isLoading,
            textID =
                if (hubUiState.user.follows.contains(accountUiState.user.userID)) R.string.followed else R.string.follow,
            onClick = {
                accountUiAction.onToggleIsLoading()
                accountUiAction.onFollow(
                    hubUiState.user.follows.contains(accountUiState.user.userID),
                    hubUiState.user,
                    accountUiState.user,
                    hubUiAction.onGetUser,
                    accountUiAction.onToggleIsLoading,
                )
            },
        )
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_normal)))
    }
}
