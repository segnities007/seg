package com.example.feature.screens.hub.account

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
import androidx.compose.ui.res.stringResource
import com.example.domain.presentation.navigation.Navigation
import com.example.feature.R
import com.example.feature.components.button.rounded.RoundedButton
import com.example.feature.components.card.postcard.DefaultPostCard
import com.example.feature.components.card.postcard.PostCardAction
import com.example.feature.components.indicator.LoadingUI
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.HubState

@Composable
fun Account(
    modifier: Modifier = Modifier,
    hubState: HubState,
    accountState: AccountState,
    onHubAction: (HubAction) -> Unit,
    onAccountAction: (AccountAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
    onHubNavigate: (Navigation) -> Unit,
) {
    LaunchedEffect(Unit) {
        onAccountAction(AccountAction.InitAccountState(hubState.otherUserID))
    }

    DisposableEffect(Unit) {
        onDispose {
            onAccountAction(AccountAction.ResetState)
        }
    }

    AccountUi(
        modifier = modifier,
        hubState = hubState,
        onHubAction = onHubAction,
        accountState = accountState,
        onAccountAction = onAccountAction,
        onPostCardAction = onPostCardAction,
        onHubNavigate = onHubNavigate,
    )
}

@Composable
private fun AccountUi(
    modifier: Modifier = Modifier,
    hubState: HubState,
    accountState: AccountState,
    onHubAction: (HubAction) -> Unit,
    onAccountAction: (AccountAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
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
        item {
            FollowButton(
                hubState = hubState,
                accountState = accountState,
                onHubAction = onHubAction,
                onAccountAction = onAccountAction,
            )
        }
        items(
            accountState.posts.size,
            key = { index: Int -> accountState.posts[index].id },
        ) { i ->
            DefaultPostCard(
                post = accountState.posts[i],
                hubState = hubState,
                onHubNavigate = onHubNavigate,
                onHubAction = onHubAction,
                onProcessOfEngagementAction = {
                    onAccountAction(AccountAction.ProcessOfEngagementAction(it))
                },
                onPostCardAction = onPostCardAction,
            )
            Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smallest)))
        }
        if (!accountState.isCompletedFetchPosts) {
            item {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(
                        onLoading = {
                            onAccountAction(AccountAction.GetPosts)
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun FollowButton(
    modifier: Modifier = Modifier,
    hubState: HubState,
    accountState: AccountState,
    onHubAction: (HubAction) -> Unit,
    onAccountAction: (AccountAction) -> Unit,
) {
    if (hubState.self.userID != accountState.user.userID) {
        Row(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.padding_small)),
            horizontalArrangement = Arrangement.Center,
        ) {
            RoundedButton(
                modifier = Modifier.weight(1f),
                text =
                    stringResource(
                        if (hubState.self.follows.contains(accountState.user.userID)) R.string.followed else R.string.follow,
                    ),
                uiStatus = accountState.followButtonStatus,
                onHubAction = onHubAction,
                onClick = {
                    onAccountAction(
                        AccountAction.ClickFollowButton(
                            hubState.self.follows.contains(accountState.user.userID),
                            hubState.self,
                            accountState.user,
                        ) { onHubAction(HubAction.GetUser(onHubAction)) },
                    )
                },
            )
        }
    }
}
