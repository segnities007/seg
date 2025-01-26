package com.segnities007.seg.ui.screens.hub.search

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
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.components.card.EngagementIconAction
import com.segnities007.seg.ui.components.card.EngagementIconState
import com.segnities007.seg.ui.components.card.PostCard
import com.segnities007.seg.ui.components.card.PostCardUiAction
import com.segnities007.seg.ui.components.indicator.LoadingUI
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState

@Composable
fun Search(
    modifier: Modifier = Modifier,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    postCardUiAction: PostCardUiAction,
    engagementIconState: EngagementIconState,
    engagementIconAction: EngagementIconAction,
    topSearchBarUiState: TopSearchBarUiState,
    searchUiState: SearchUiState,
    onHubNavigate: (Route) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(top = dimensionResource(R.dimen.padding_smaller)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        when (topSearchBarUiState.index) {
            0 -> {
                items(
                    searchUiState.postsSortedByViewCount.size,
                    key = { index: Int ->
                        searchUiState.postsSortedByViewCount[index].id
                    },
                ) { i ->
                    PostCard(
                        post = searchUiState.postsSortedByViewCount[i],
                        myself = hubUiState.user,
                        onHubNavigate = onHubNavigate,
                        hubUiAction = hubUiAction,
                        engagementIconState = engagementIconState,
                        engagementIconAction = engagementIconAction,
                        postCardUiAction = postCardUiAction,
                    )
                }
                // action for fetching before post
                item {
                    Column {
                        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                        LoadingUI(
                            onLoading = {
                                if (searchUiState.postsSortedByViewCount.isNotEmpty()) {
                                    // TODO
                                }
                            },
                        )
                    }
                }
            }
            1 -> {
                items(
                    searchUiState.posts.size,
                    key = { index: Int ->
                        searchUiState.posts[index].id
                    },
                ) { i ->
                    PostCard(
                        post = searchUiState.posts[i],
                        myself = hubUiState.user,
                        onHubNavigate = onHubNavigate,
                        hubUiAction = hubUiAction,
                        engagementIconState = engagementIconState,
                        engagementIconAction = engagementIconAction,
                        postCardUiAction = postCardUiAction,
                    )
                }
                // action for fetching before post
                item {
                    Column {
                        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                        LoadingUI(
                            onLoading = {
                                if (searchUiState.posts.isNotEmpty()) {
                                    // TODO
                                }
                            },
                        )
                    }
                }
            }
            2 -> {
                items(
                    searchUiState.users.size,
                    key = { index: Int ->
                        searchUiState.users[index].id
                    },
                ) { i ->
                }
                // action for fetching before post
                item {
                    Column {
                        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                        LoadingUI(
                            onLoading = {
                                if (searchUiState.users.isNotEmpty()) {
                                    // TODO
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}
