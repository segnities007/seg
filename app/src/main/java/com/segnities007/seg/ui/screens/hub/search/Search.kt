package com.segnities007.seg.ui.screens.hub.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.segnities007.seg.R
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.components.card.AvatarCard
import com.segnities007.seg.ui.components.card.postcard.EngagementIconAction
import com.segnities007.seg.ui.components.card.postcard.PostCard
import com.segnities007.seg.ui.components.card.postcard.PostCardUiAction
import com.segnities007.seg.ui.components.indicator.LoadingUI
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState
import com.segnities007.seg.ui.screens.hub.account.AccountUiAction

@Composable
fun Search(
    modifier: Modifier = Modifier,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    postCardUiAction: PostCardUiAction,
    engagementIconActionForPosts: EngagementIconAction,
    engagementIconActionForPostsSortedByViewCount: EngagementIconAction,
    topSearchBarUiState: TopSearchBarUiState,
    accountUiAction: AccountUiAction,
    searchUiState: SearchUiState,
    searchUiAction: SearchUiAction,
    onHubNavigate: (Route) -> Unit,
) {
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
                engagementIconAction = engagementIconActionForPostsSortedByViewCount,
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
                engagementIconAction = engagementIconActionForPosts,
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
                topSearchBarUiState = topSearchBarUiState,
                accountUiAction = accountUiAction,
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
                engagementIconAction = engagementIconAction,
                postCardUiAction = postCardUiAction,
            )
        }
        // action for fetching before post
        item {
            if (searchUiState.postsSortedByViewCount.isNotEmpty() && topSearchBarUiState.isCompletedLoadingPostsSortedByViewCount) {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(
                        onLoading = {
                            searchUiAction.onGetBeforePostsByKeywordSortedByViewCount(
                                topSearchBarUiState.keyword,
                                searchUiState.postsSortedByViewCount.last().viewCount,
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
                engagementIconAction = engagementIconAction,
                postCardUiAction = postCardUiAction,
            )
        }
        // action for fetching before post
        item {
            if (searchUiState.posts.isNotEmpty() && topSearchBarUiState.isCompletedLoadingPosts) {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(
                        onLoading = {
                            searchUiAction.onGetBeforePostsByKeyword(
                                topSearchBarUiState.keyword,
                                searchUiState.posts.last().createAt,
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
    topSearchBarUiState: TopSearchBarUiState,
    accountUiAction: AccountUiAction,
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
            AvatarCard(
                onCardClick = {
                    accountUiAction.onGetUserPosts(searchUiState.users[i].userID)
                    hubUiAction.onSetUserID(searchUiState.users[i].userID)
                    hubUiAction.onChangeCurrentRouteName(NavigationHubRoute.Account().name)
                    onHubNavigate(NavigationHubRoute.Account())
                },
                user = searchUiState.users[i],
            )
        }
        // action for fetching before post
        item {
            if (searchUiState.users.isNotEmpty() && topSearchBarUiState.isCompletedLoadingUsers) {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(
                        onLoading = {
                            searchUiAction.onGetBeforeUsersByKeyword(
                                topSearchBarUiState.keyword,
                                searchUiState.users.last().createAt,
                            )
                        },
                    )
                }
            }
        }
    }
}
