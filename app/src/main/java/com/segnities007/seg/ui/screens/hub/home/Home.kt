package com.segnities007.seg.ui.screens.hub.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.domain.model.NavigationIndex
import com.segnities007.seg.ui.components.card.PostCard
import com.segnities007.seg.ui.components.card.PostCardViewModel
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState


@Composable
fun Home(
    modifier: Modifier,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    postCardViewModel: PostCardViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
){

    LaunchedEffect(Unit) {
        val action = homeViewModel.getHomeUiAction()
        action.onGetNewPosts()
    }

    Column (
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ){
        for (i in 0 until homeViewModel.homeUiState.posts.size) {
            val homeUiState = homeViewModel.homeUiState

            LaunchedEffect(Unit) {
                val action = homeViewModel.getHomeUiAction()
                action.onGetIcon(homeViewModel.homeUiState.posts[i].iconID)
            }

            PostCard(
                onCardClick = {},
                hubUiState = hubUiState,
                hubUiAction = hubUiAction,
                post = homeUiState.posts[i],
                images = homeUiState.images[i],
                icon = homeUiState.icon,
                onInitializeAction = { post: Post ->
                    postCardViewModel.onGetPostCardUiAction().onIncrementViewCount(post)
                },
                onAvatarClick = { userID: String ->
                    hubUiAction.onGetUserID(userID)
                    hubUiAction.onNavigate(NavigationIndex.HubAccount)
                },
            )
        }
    }

}