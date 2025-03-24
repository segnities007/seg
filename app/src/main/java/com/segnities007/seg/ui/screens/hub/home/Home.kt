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
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState

@Composable
fun Home(
    modifier: Modifier,
    hubState: HubState,
    hubAction: HubAction,
    homeState: HomeState,
    homeAction: HomeAction,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Navigation) -> Unit,
) {
    HomeUi(
        modifier = modifier,
        hubState = hubState,
        hubAction = hubAction,
        homeState = homeState,
        homeAction = homeAction,
        postCardUiAction = postCardUiAction,
        onHubNavigate = onHubNavigate,
    )
}

@Composable
private fun HomeUi(
    modifier: Modifier,
    hubState: HubState,
    hubAction: HubAction,
    homeState: HomeState,
    homeAction: HomeAction,
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
        items(
            homeState.posts.size,
            key = { index: Int -> homeState.posts[index].id },
        ) { i ->
            PostCard(
                post = homeState.posts[i],
                hubState = hubState,
                onHubNavigate = onHubNavigate,
                hubAction = hubAction,
                postCardUiAction = postCardUiAction,
                isIncrementView = true,
                onProcessOfEngagementAction = homeAction.onProcessOfEngagementAction,
            )
            Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smallest)))
        }
        // action for fetching before post
        item {
            if (!homeState.hasNoMorePost && homeState.posts.isNotEmpty()) {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(
                        onLoading = {
                            if (homeState.posts.isNotEmpty()) {
                                homeAction.onGetBeforeNewPosts(homeState.posts.last().updateAt)
                            }
                        },
                    )
                }
            }
        }
    }
}
