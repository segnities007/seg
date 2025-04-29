package com.example.feature.screens.hub.home

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
import com.example.domain.presentation.Navigation
import com.example.feature.R
import com.example.feature.components.card.postcard.PostCard
import com.example.feature.components.card.postcard.PostCardAction
import com.example.feature.components.indicator.LoadingUI
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.HubState

@Composable
fun Home(
    modifier: Modifier,
    hubState: HubState,
    homeState: HomeState,
    onHubAction: (HubAction) -> Unit,
    onHomeAction: (HomeAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
    onHubNavigate: (Navigation) -> Unit,
) {
    HomeUi(
        modifier = modifier,
        hubState = hubState,
        homeState = homeState,
        onHubAction = onHubAction,
        onHomeAction = onHomeAction,
        onPostCardAction = onPostCardAction,
        onHubNavigate = onHubNavigate,
    )
}

@Composable
private fun HomeUi(
    modifier: Modifier,
    hubState: HubState,
    homeState: HomeState,
    onHubAction: (HubAction) -> Unit,
    onHomeAction: (HomeAction) -> Unit,
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
        items(
            homeState.posts.size,
            key = { index: Int -> homeState.posts[index].id },
        ) { i ->
            PostCard(
                post = homeState.posts[i],
                hubState = hubState,
                isIncrementView = true,
                onHubNavigate = onHubNavigate,
                onHubAction = onHubAction,
                onPostCardAction = onPostCardAction,
                onProcessOfEngagementAction = { newPost ->
                    onHomeAction(
                        HomeAction.ProcessOfEngagement(
                            newPost
                        )
                    )
                },
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
                                onHomeAction(HomeAction.GetBeforeNewPosts(homeState.posts.last().updateAt))
                            }
                        },
                    )
                }
            }
        }
    }
}
