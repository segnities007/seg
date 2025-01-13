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
import com.segnities007.seg.data.model.Post
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
        action.onGetOtherUser(hubUiState.userID)
        action.onGetUserPosts(hubUiState.userID)
    }

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        TopStatusBar(accountViewModel.accountUiState.user, hubUiAction = hubUiAction)
        for (i in 0 until accountViewModel.accountUiState.posts.size) {

            val accountUiState = accountViewModel.accountUiState

            LaunchedEffect(Unit) {
                val action = accountViewModel.getAccountUiAction()
                action.onGetIcon(accountUiState.posts[i].iconID)
            }

            PostCard(
                onCardClick = {},
                onAvatarClick = {},
                post = accountUiState.posts[i],
                images = accountUiState.images[i],
                icon = accountUiState.icon,
                onInitializeAction = {post: Post ->
                }
            )
        }

    }
}