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
import com.segnities007.seg.ui.components.button.SmallButton
import com.segnities007.seg.ui.components.card.PostCard
import com.segnities007.seg.ui.components.top_bar.TopStatusBar
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState
import com.segnities007.seg.R
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.navigation.hub.NavigationHubRoute


@Composable
fun Account(
    modifier: Modifier = Modifier,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    accountUiState: AccountUiState,
    accountUiAction: AccountUiAction,
    onNavigate: (route: Route) -> Unit,
){

    LaunchedEffect(Unit) {
        accountUiAction.onGetOtherUser(hubUiState.userID)
        accountUiAction.onGetUserPosts(hubUiState.userID)
    }

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        TopStatusBar(
            user = accountUiState.user,
            onClickFollowsButton = {
                if (!accountUiState.user.follows.isNullOrEmpty()){
                    accountUiAction.onSetUsers(accountUiState.user.follows)
                }
                onNavigate(NavigationHubRoute.Accounts)
            },
            onClickFollowersButton = {
                if (!accountUiState.user.followers.isNullOrEmpty()){
                    accountUiAction.onSetUsers(accountUiState.user.followers)
                }
                onNavigate(NavigationHubRoute.Accounts)
            },
            currentRouteName = hubUiState.currentRouteName,
            onNavigate = onNavigate,
        )

        if(hubUiState.user.userID != accountUiState.user.userID)
            FollowButtons(hubUiState = hubUiState, accountUiState = accountUiState, accountUiAction = accountUiAction)

        for (i in 0 until accountUiState.posts.size) {

            LaunchedEffect(Unit) {
                accountUiAction.onGetIcon(accountUiState.posts[i].iconID)
            }
            PostCard(
                onCardClick = {},
                onAvatarClick = {},
                post = accountUiState.posts[i],
                images = accountUiState.images[i],
                icon = accountUiState.icon,
                onInitializeAction = {}
            )
        }

    }
}

@Composable
private fun FollowButtons(
    modifier: Modifier = Modifier,
    hubUiState: HubUiState,
    accountUiState: AccountUiState,
    accountUiAction: AccountUiAction,
){
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_normal)))
        SmallButton(
            modifier = Modifier.weight(1f),
            textID = R.string.follows,
            onClick = {
                accountUiAction.onFollow(hubUiState.user, accountUiState.user)
            })
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_normal)))
    }
}