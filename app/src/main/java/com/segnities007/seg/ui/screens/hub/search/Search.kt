package com.segnities007.seg.ui.screens.hub.search

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
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
    searchUiAction: SearchUiAction,
    onHubNavigate: (Route) -> Unit,
) {
    LaunchedEffect(topSearchBarUiState.keyword) {
        if (topSearchBarUiState.keyword != "") {
            Log.d("search.kt launch", "${searchUiState.posts}, \n ${searchUiState.postsSortedByViewCount}")
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            searchUiAction.onResetListsOfSearchUiState()
        }
    }

    when (topSearchBarUiState.index) {
        0 ->
            MostViewPosts(
                modifier = modifier,
                hubUiState = hubUiState,
                hubUiAction = hubUiAction,
                postCardUiAction = postCardUiAction,
                engagementIconState = engagementIconState,
                engagementIconAction = engagementIconAction,
                topSearchBarUiState = topSearchBarUiState,
                searchUiState = searchUiState,
                searchUiAction = searchUiAction,
                onHubNavigate = onHubNavigate,
            )
        1 ->
            LatestPosts(
                modifier = modifier,
                hubUiState = hubUiState,
                hubUiAction = hubUiAction,
                postCardUiAction = postCardUiAction,
                engagementIconState = engagementIconState,
                engagementIconAction = engagementIconAction,
                topSearchBarUiState = topSearchBarUiState,
                searchUiState = searchUiState,
                searchUiAction = searchUiAction,
                onHubNavigate = onHubNavigate,
            )
        2 ->
            Users(
                modifier = modifier,
                hubUiState = hubUiState,
                hubUiAction = hubUiAction,
                postCardUiAction = postCardUiAction,
                engagementIconState = engagementIconState,
                engagementIconAction = engagementIconAction,
                topSearchBarUiState = topSearchBarUiState,
                searchUiState = searchUiState,
                searchUiAction = searchUiAction,
                onHubNavigate = onHubNavigate,
            )
    }
}

@Composable
private fun MostViewPosts(
    modifier: Modifier = Modifier,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    postCardUiAction: PostCardUiAction,
    engagementIconState: EngagementIconState,
    engagementIconAction: EngagementIconAction,
    topSearchBarUiState: TopSearchBarUiState,
    searchUiState: SearchUiState,
    searchUiAction: SearchUiAction,
    onHubNavigate: (Route) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(top = dimensionResource(R.dimen.padding_smaller)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
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
                isIncrementView = false,
                engagementIconState = engagementIconState,
                engagementIconAction = engagementIconAction,
                postCardUiAction = postCardUiAction,
            )
        }
        item {
            // TODO remove
            Text(searchUiState.postsSortedByViewCount.toString())
        }
        // action for fetching before post
        item {
            if (searchUiState.postsSortedByViewCount.isNotEmpty()) {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(
                        onLoading = {
                            searchUiAction.onGetBeforePostsByKeywordSortedByViewCount(
                                topSearchBarUiState.keyword,
                                searchUiState.postsSortedByViewCount.last().updateAt,
                            )
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun LatestPosts(
    modifier: Modifier = Modifier,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    postCardUiAction: PostCardUiAction,
    engagementIconState: EngagementIconState,
    engagementIconAction: EngagementIconAction,
    topSearchBarUiState: TopSearchBarUiState,
    searchUiState: SearchUiState,
    searchUiAction: SearchUiAction,
    onHubNavigate: (Route) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(top = dimensionResource(R.dimen.padding_smaller)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
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
                isIncrementView = false,
                engagementIconState = engagementIconState,
                engagementIconAction = engagementIconAction,
                postCardUiAction = postCardUiAction,
            )
        }
        item {
            // TODO remove
            Text(searchUiState.posts.toString())
        }
        // action for fetching before post
        item {
            if (searchUiState.posts.isNotEmpty()) {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(
                        onLoading = {
                            searchUiAction.onGetBeforePostsByKeywordSortedByViewCount(
                                topSearchBarUiState.keyword,
                                searchUiState.posts.last().updateAt,
                            )
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun Users(
    modifier: Modifier = Modifier,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    postCardUiAction: PostCardUiAction,
    engagementIconState: EngagementIconState,
    engagementIconAction: EngagementIconAction,
    topSearchBarUiState: TopSearchBarUiState,
    searchUiState: SearchUiState,
    searchUiAction: SearchUiAction,
    onHubNavigate: (Route) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(top = dimensionResource(R.dimen.padding_smaller)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        items(
            searchUiState.users.size,
            key = { index: Int ->
                searchUiState.users[index].id
            },
        ) { i ->
        }
        item {
            // TODO remove
            Text(searchUiState.users.toString())
        }
        // action for fetching before post
        item {
            if (searchUiState.users.isNotEmpty()) {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(
                        onLoading = {
                            searchUiAction.onGetBeforePostsByKeywordSortedByViewCount(
                                topSearchBarUiState.keyword,
                                searchUiState.users.last().updateAt,
                            )
                        },
                    )
                }
            }
        }
    }
}
