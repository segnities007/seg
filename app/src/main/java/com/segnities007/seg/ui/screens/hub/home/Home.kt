package com.segnities007.seg.ui.screens.hub.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.components.card.PostCard
import com.segnities007.seg.ui.components.card.PostCardUiAction
import com.segnities007.seg.ui.components.card.PostCardUiState
import com.segnities007.seg.ui.screens.hub.HubUiAction

@Composable
fun Home(
    modifier: Modifier,
    hubNavController: NavController,
    hubUiAction: HubUiAction,
    postCardUiState: PostCardUiState,
    postCardUiAction: PostCardUiAction,
){
    Column (
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ){
        for (i in 0 until postCardUiState.posts.size) {

            PostCard(
                onCardClick = {/*TODO*/},
                post = postCardUiState.posts[i],
                images = postCardUiState.imageLists[i],
                icon = postCardUiState.icons[i],
                onInitializeAction = { post: Post ->
                    postCardUiAction.onIncrementViewCount(post)
                },
                onAvatarClick = { userID: String ->
                    hubUiAction.onGetUserID(userID)
                    hubNavController.navigate(NavigationHubRoute.Account)
                },
            )
        }
    }

}