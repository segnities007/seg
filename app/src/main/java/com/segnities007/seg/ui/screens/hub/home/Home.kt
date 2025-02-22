package com.segnities007.seg.ui.screens.hub.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.segnities007.seg.R
import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.components.card.postcard.PostCard
import com.segnities007.seg.ui.components.card.postcard.PostCardUiAction
import com.segnities007.seg.ui.components.indicator.LoadingUI
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState

@Composable
fun Home(
    modifier: Modifier,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    homeUiState: HomeUiState,
    homeUiAction: HomeUiAction,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Navigation) -> Unit,
) {
    HomeUi(
        modifier = modifier,
        hubUiState = hubUiState,
        hubUiAction = hubUiAction,
        homeUiState = homeUiState,
        homeUiAction = homeUiAction,
        postCardUiAction = postCardUiAction,
        onHubNavigate = onHubNavigate,
    )
}

@Composable
private fun HomeUi(
    modifier: Modifier,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    homeUiState: HomeUiState,
    homeUiAction: HomeUiAction,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Navigation) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(top = dimensionResource(R.dimen.padding_smaller)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        items(
            homeUiState.posts.size,
            key = { index: Int -> homeUiState.posts[index].id },
        ) { i ->
            PostCard(
                post = homeUiState.posts[i],
                hubUiState = hubUiState,
                onHubNavigate = onHubNavigate,
                hubUiAction = hubUiAction,
                postCardUiAction = postCardUiAction,
                isIncrementView = true,
                onProcessOfEngagementAction = homeUiAction.onProcessOfEngagementAction,
            )
        }
        // action for fetching before post
        item {
            if (!homeUiState.hasNoMorePost && homeUiState.posts.isNotEmpty()) {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(
                        onLoading = {
                            if (homeUiState.posts.isNotEmpty()) {
                                homeUiAction.onGetBeforeNewPosts(homeUiState.posts.last().updateAt)
                            }
                        },
                    )
                }
            }
        }
    }
}
