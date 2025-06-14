package com.example.feature.screens.hub.search

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
import com.example.domain.presentation.navigation.Navigation
import com.example.domain.presentation.navigation.NavigationHubRoute
import com.example.feature.R
import com.example.feature.components.bar.top_search_bar.TopSearchBarState
import com.example.feature.components.card.AvatarCard
import com.example.feature.components.card.postcard.PostCard
import com.example.feature.components.card.postcard.PostCardAction
import com.example.feature.components.indicator.LoadingUI
import com.example.feature.components.tab.TabState
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.HubState
import com.example.feature.screens.hub.account.AccountAction

@Composable
fun Search(
    modifier: Modifier = Modifier,
    hubState: HubState,
    tabState: TabState,
    topSearchBarState: TopSearchBarState,
    searchState: SearchState,
    onHubAction: (HubAction) -> Unit,
    onSearchAction: (SearchAction) -> Unit,
    onAccountAction: (AccountAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
    onHubNavigate: (Navigation) -> Unit,
) {
    DisposableEffect(Unit) {
        onDispose {
            onSearchAction(SearchAction.ResetSearchState)
        }
    }

    when (tabState.index) {
        0 ->
            MostViewPosts(
                modifier = modifier,
                hubState = hubState,
                topSearchBarState = topSearchBarState,
                searchState = searchState,
                onHubNavigate = onHubNavigate,
                onHubAction = onHubAction,
                onSearchAction = onSearchAction,
                onPostCardAction = onPostCardAction,
            )

        1 ->
            LatestPosts(
                modifier = modifier,
                hubState = hubState,
                topSearchBarState = topSearchBarState,
                searchState = searchState,
                onHubNavigate = onHubNavigate,
                onHubAction = onHubAction,
                onSearchAction = onSearchAction,
                onPostCardAction = onPostCardAction,
            )

        2 ->
            Users(
                modifier = modifier,
                topSearchBarState = topSearchBarState,
                searchState = searchState,
                onHubNavigate = onHubNavigate,
                onHubAction = onHubAction,
                onSearchAction = onSearchAction,
                onAccountAction = onAccountAction,
            )
    }
}

@Composable
private fun MostViewPosts(
    modifier: Modifier = Modifier,
    hubState: HubState,
    topSearchBarState: TopSearchBarState,
    searchState: SearchState,
    onHubAction: (HubAction) -> Unit,
    onSearchAction: (SearchAction) -> Unit,
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
            searchState.postsSortedByViewCount.size,
            key = { index: Int ->
                searchState.postsSortedByViewCount[index].id
            },
        ) { i ->
            PostCard(
                post = searchState.postsSortedByViewCount[i],
                hubState = hubState,
                onHubNavigate = onHubNavigate,
                isIncrementView = false,
                onProcessOfEngagementAction = { newPost ->
                    (
                        SearchAction.ProcessOfEngagementAction(
                            newPost,
                        )
                    )
                },
                onHubAction = onHubAction,
                onPostCardAction = onPostCardAction,
            )
            Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smallest)))
        }
        item {
            if (searchState.postsSortedByViewCount.isNotEmpty() && topSearchBarState.isCompletedLoadingPostsSortedByViewCount) {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(
                        onLoading = {
                            onSearchAction(
                                SearchAction.GetBeforePostsByKeywordSortedByViewCount(
                                    keyword = topSearchBarState.keyword,
                                    viewCount = searchState.postsSortedByViewCount.last().viewCount,
                                    onHubAction = onHubAction,
                                ),
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
    topSearchBarState: TopSearchBarState,
    searchState: SearchState,
    onHubAction: (HubAction) -> Unit,
    onSearchAction: (SearchAction) -> Unit,
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
            searchState.posts.size,
            key = { index: Int ->
                searchState.posts[index].id
            },
        ) { i ->
            PostCard(
                post = searchState.posts[i],
                hubState = hubState,
                onHubNavigate = onHubNavigate,
                isIncrementView = false,
                onHubAction = onHubAction,
                onPostCardAction = onPostCardAction,
                onProcessOfEngagementAction = { newPost ->
                    onSearchAction(
                        SearchAction.ProcessOfEngagementAction(
                            newPost,
                        ),
                    )
                },
            )
            Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smallest)))
        }
        item {
            if (searchState.posts.isNotEmpty() && topSearchBarState.isCompletedLoadingPosts) {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(
                        onLoading = {
                            onSearchAction(
                                SearchAction.GetBeforePostsByKeyword(
                                    keyword = topSearchBarState.keyword,
                                    afterPostCreatedAt = searchState.posts.last().createAt,
                                    onHubAction = onHubAction,
                                ),
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
    topSearchBarState: TopSearchBarState,
    searchState: SearchState,
    onHubAction: (HubAction) -> Unit,
    onSearchAction: (SearchAction) -> Unit,
    onAccountAction: (AccountAction) -> Unit,
    onHubNavigate: (Navigation) -> Unit,
) {
    LazyColumn(
        modifier =
            modifier
                .fillMaxSize()
                .padding(top = dimensionResource(R.dimen.padding_smaller)),
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
                    onAccountAction(AccountAction.GetUserPosts(searchState.users[i].userID))
                    onHubAction(HubAction.SetUserID(searchState.users[i].userID))
                    onHubAction(HubAction.ChangeCurrentRouteName(NavigationHubRoute.Account.name))
                    onHubNavigate(NavigationHubRoute.Account)
                },
                user = searchState.users[i],
            )
        }
        item {
            if (searchState.users.isNotEmpty() && topSearchBarState.isCompletedLoadingUsers) {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(
                        onLoading = {
                            onSearchAction(
                                SearchAction.GetBeforeUsersByKeyword(
                                    keyword = topSearchBarState.keyword,
                                    afterPostCreatedAt = searchState.users.last().createAt,
                                    onHubAction = onHubAction,
                                ),
                            )
                        },
                    )
                }
            }
        }
    }
}
