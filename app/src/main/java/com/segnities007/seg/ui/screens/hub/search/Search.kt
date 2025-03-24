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
import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.components.card.AvatarCard
import com.segnities007.seg.ui.components.card.postcard.PostCard
import com.segnities007.seg.ui.components.card.postcard.PostCardUiAction
import com.segnities007.seg.ui.components.indicator.LoadingUI
import com.segnities007.seg.ui.components.tab.TabUiState
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState
import com.segnities007.seg.ui.screens.hub.account.AccountAction

@Composable
fun Search(
    modifier: Modifier = Modifier,
    hubState: HubState,
    hubAction: HubAction,
    postCardUiAction: PostCardUiAction,
    tabUiState: TabUiState,
    topSearchBarUiState: TopSearchBarUiState,
    accountAction: AccountAction,
    searchState: SearchState,
    searchUiAction: SearchAction,
    onHubNavigate: (Navigation) -> Unit,
) {
    DisposableEffect(Unit) {
        onDispose {
            searchUiAction.onResetListsOfSearchUiState()
        }
    }

    when (tabUiState.index) {
        0 ->
            MostViewPosts(
                modifier = modifier,
                hubState = hubState,
                hubAction = hubAction,
                postCardUiAction = postCardUiAction,
                topSearchBarUiState = topSearchBarUiState,
                searchState = searchState,
                searchUiAction = searchUiAction,
                onHubNavigate = onHubNavigate,
            )
        1 ->
            LatestPosts(
                modifier = modifier,
                hubState = hubState,
                hubAction = hubAction,
                postCardUiAction = postCardUiAction,
                topSearchBarUiState = topSearchBarUiState,
                searchState = searchState,
                searchUiAction = searchUiAction,
                onHubNavigate = onHubNavigate,
            )
        2 ->
            Users(
                modifier = modifier,
                hubAction = hubAction,
                topSearchBarUiState = topSearchBarUiState,
                accountAction = accountAction,
                searchState = searchState,
                searchUiAction = searchUiAction,
                onHubNavigate = onHubNavigate,
            )
    }
}

@Composable
private fun MostViewPosts(
    modifier: Modifier = Modifier,
    hubState: HubState,
    hubAction: HubAction,
    postCardUiAction: PostCardUiAction,
    topSearchBarUiState: TopSearchBarUiState,
    searchState: SearchState,
    searchUiAction: SearchAction,
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
            searchState.postsSortedByViewCount.size,
            key = { index: Int ->
                searchState.postsSortedByViewCount[index].id
            },
        ) { i ->
            PostCard(
                post = searchState.postsSortedByViewCount[i],
                hubState = hubState,
                onHubNavigate = onHubNavigate,
                hubAction = hubAction,
                isIncrementView = false,
                postCardUiAction = postCardUiAction,
                onProcessOfEngagementAction = searchUiAction.onProcessOfEngagementAction,
            )
            Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smallest)))
        }
        // action for fetching before post
        item {
            if (searchState.postsSortedByViewCount.isNotEmpty() && topSearchBarUiState.isCompletedLoadingPostsSortedByViewCount) {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(
                        onLoading = {
                            searchUiAction.onGetBeforePostsByKeywordSortedByViewCount(
                                topSearchBarUiState.keyword,
                                searchState.postsSortedByViewCount.last().viewCount,
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
    hubState: HubState,
    hubAction: HubAction,
    postCardUiAction: PostCardUiAction,
    topSearchBarUiState: TopSearchBarUiState,
    searchState: SearchState,
    searchUiAction: SearchAction,
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
            searchState.posts.size,
            key = { index: Int ->
                searchState.posts[index].id
            },
        ) { i ->
            PostCard(
                post = searchState.posts[i],
                hubState = hubState,
                onHubNavigate = onHubNavigate,
                hubAction = hubAction,
                isIncrementView = false,
                postCardUiAction = postCardUiAction,
                onProcessOfEngagementAction = searchUiAction.onProcessOfEngagementAction,
            )
            Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smallest)))
        }
        // action for fetching before post
        item {
            if (searchState.posts.isNotEmpty() && topSearchBarUiState.isCompletedLoadingPosts) {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(
                        onLoading = {
                            searchUiAction.onGetBeforePostsByKeyword(
                                topSearchBarUiState.keyword,
                                searchState.posts.last().createAt,
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
    hubAction: HubAction,
    topSearchBarUiState: TopSearchBarUiState,
    accountAction: AccountAction,
    searchState: SearchState,
    searchUiAction: SearchAction,
    onHubNavigate: (Navigation) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(top = dimensionResource(R.dimen.padding_smaller)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        items(
            searchState.users.size,
            key = { index: Int ->
                searchState.users[index].id
            },
        ) { i ->
            AvatarCard(
                onCardClick = {
                    accountAction.onGetUserPosts(searchState.users[i].userID)
                    hubAction.onSetUserID(searchState.users[i].userID)
                    hubAction.onChangeCurrentRouteName(NavigationHubRoute.Account.name)
                    onHubNavigate(NavigationHubRoute.Account)
                },
                user = searchState.users[i],
            )
        }
        // action for fetching before post
        item {
            if (searchState.users.isNotEmpty() && topSearchBarUiState.isCompletedLoadingUsers) {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(
                        onLoading = {
                            searchUiAction.onGetBeforeUsersByKeyword(
                                topSearchBarUiState.keyword,
                                searchState.users.last().createAt,
                            )
                        },
                    )
                }
            }
        }
    }
}
