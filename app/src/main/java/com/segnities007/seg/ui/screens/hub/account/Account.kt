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
import androidx.hilt.navigation.compose.hiltViewModel
import com.segnities007.seg.ui.components.button.SmallButton
import com.segnities007.seg.ui.components.card.PostCard
import com.segnities007.seg.ui.components.top_bar.TopStatusBar
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState
import com.segnities007.seg.R
import com.segnities007.seg.domain.model.NavigationIndex

@Composable
fun Account(
    modifier: Modifier = Modifier,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    accountViewModel: AccountViewModel = hiltViewModel(),
){

    LaunchedEffect(Unit) {
        val action = accountViewModel.getAccountUiAction()
        action.getOtherUser(hubUiState.userID)
        action.getUserPosts(hubUiState.userID)
    }

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        TopStatusBar(accountViewModel.accountUiState.user, onSettingClick = {/*TODO*/})
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
        FollowButtons(hubUiAction = hubUiAction,)
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
        for (post in accountViewModel.accountUiState.posts) {
            PostCard(
                onCardClick = {},
                onAvatarClick = {},
                hubUiState = hubUiState,
                hubUiAction = hubUiAction,
                post = post,
            )
        }

    }
}

@Composable
private fun FollowButtons(
    modifier: Modifier = Modifier,
    hubUiAction: HubUiAction,
){
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
        SmallButton(
            modifier = Modifier.weight(1f),
            textID = R.string.follows,
            onClick = {
            hubUiAction.onNavigate(NavigationIndex.HubAccounts)
        /*TODO*/
        })
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
        SmallButton(
            modifier = Modifier.weight(1f),
            textID = R.string.followers,
            onClick = {
            hubUiAction.onNavigate(NavigationIndex.HubAccounts)
        /*TODO*/
        })
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
    }
}