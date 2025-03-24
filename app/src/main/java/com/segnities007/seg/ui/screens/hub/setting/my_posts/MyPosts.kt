package com.segnities007.seg.ui.screens.hub.setting.my_posts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.segnities007.seg.R
import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.components.card.postcard.PostCard
import com.segnities007.seg.ui.components.card.postcard.PostCardUiAction
import com.segnities007.seg.ui.components.card.postcard.PostCardWithDetailButton
import com.segnities007.seg.ui.components.indicator.LoadingUI
import com.segnities007.seg.ui.components.tab.TabUiAction
import com.segnities007.seg.ui.components.tab.TabUiState
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState
import com.segnities007.seg.ui.screens.hub.home.HomeAction

@Composable
fun MyPosts(
    homeAction: HomeAction,
    hubState: HubState,
    hubAction: HubAction,
    tabUiState: TabUiState,
    tabUiAction: TabUiAction,
    myPostsViewModel: MyPostsViewModel = hiltViewModel(),
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Navigation) -> Unit,
) {
    LaunchedEffect(Unit) {
        hubAction.onChangeIsHideTopBar()
        val action = myPostsViewModel.onGetMyPostsUiAction()
        action.onSetSelf(hubState.user)
        action.onInit()
    }

    DisposableEffect(Unit) {
        onDispose {
            tabUiAction.onUpdateIndex(0)
        }
    }

    Column {
        MyPostsUi(
            homeAction = homeAction,
            hubState = hubState,
            hubAction = hubAction,
            tabUiState = tabUiState,
            myPostsState = myPostsViewModel.myPostsState,
            myPostsAction = myPostsViewModel.onGetMyPostsUiAction(),
            postCardUiAction = postCardUiAction,
            onHubNavigate = onHubNavigate,
        )
    }
}

@Composable
private fun MyPostsUi(
    homeAction: HomeAction,
    hubState: HubState,
    hubAction: HubAction,
    myPostsState: MyPostsState,
    myPostsAction: MyPostsAction,
    tabUiState: TabUiState,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Navigation) -> Unit,
) {
    when (tabUiState.index) {
        0 ->
            Posts(
                homeAction = homeAction,
                myPostsState = myPostsState,
                myPostsAction = myPostsAction,
                hubState = hubState,
                hubAction = hubAction,
                postCardUiAction = postCardUiAction,
                onHubNavigate = onHubNavigate,
            )
        1 ->
            Likes(
                myPostsState = myPostsState,
                myPostsAction = myPostsAction,
                hubState = hubState,
                hubAction = hubAction,
                postCardUiAction = postCardUiAction,
                onHubNavigate = onHubNavigate,
            )
        2 ->
            Reposts(
                myPostsState = myPostsState,
                myPostsAction = myPostsAction,
                hubState = hubState,
                hubAction = hubAction,
                postCardUiAction = postCardUiAction,
                onHubNavigate = onHubNavigate,
            )
    }
}

@Composable
private fun Posts(
    homeAction: HomeAction,
    myPostsState: MyPostsState,
    myPostsAction: MyPostsAction,
    hubState: HubState,
    hubAction: HubAction,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Navigation) -> Unit,
) {
    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(
                    top = dimensionResource(R.dimen.padding_smallest),
                    start = dimensionResource(R.dimen.padding_small),
                    end = dimensionResource(R.dimen.padding_small),
                ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        if (myPostsState.posts.isNotEmpty()) {
            items(
                myPostsState.posts.size,
                key = { index: Int -> myPostsState.posts[index].id },
            ) { i ->
                PostCardWithDetailButton(
                    post = myPostsState.posts[i],
                    homeAction = homeAction,
                    hubState = hubState,
                    hubAction = hubAction,
                    myPostsAction = myPostsAction,
                    postCardUiAction = postCardUiAction,
                    onHubNavigate = onHubNavigate,
                    onProcessOfEngagementAction = myPostsAction.onProcessOfEngagementAction,
                )
                Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smallest)))
            }
        }
        if (!myPostsState.hasNoMorePosts) {
            item {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(onLoading = { myPostsAction.onGetPosts() })
                }
            }
        }
    }
}

@Composable
private fun Likes(
    myPostsState: MyPostsState,
    myPostsAction: MyPostsAction,
    hubState: HubState,
    hubAction: HubAction,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Navigation) -> Unit,
) {
    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(
                    top = dimensionResource(R.dimen.padding_smallest),
                    start = dimensionResource(R.dimen.padding_small),
                    end = dimensionResource(R.dimen.padding_small),
                ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        if (myPostsState.likedPosts.isNotEmpty()) {
            items(
                myPostsState.likedPosts.size,
                key = { index: Int -> myPostsState.likedPosts[index].id },
            ) { i ->
                PostCard(
                    post = myPostsState.likedPosts[i],
                    hubState = hubState,
                    isIncrementView = false,
                    onHubNavigate = onHubNavigate,
                    hubAction = hubAction,
                    postCardUiAction = postCardUiAction,
                    onProcessOfEngagementAction = myPostsAction.onProcessOfEngagementAction,
                )
                Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smallest)))
            }
        }
        if (!myPostsState.hasNoMoreLikedPosts) {
            item {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(onLoading = { myPostsAction.onGetLikedPosts() })
                }
            }
        }
    }
}

@Composable
private fun Reposts(
    myPostsState: MyPostsState,
    myPostsAction: MyPostsAction,
    hubState: HubState,
    hubAction: HubAction,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Navigation) -> Unit,
) {
    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(
                    top = dimensionResource(R.dimen.padding_smallest),
                    start = dimensionResource(R.dimen.padding_small),
                    end = dimensionResource(R.dimen.padding_small),
                ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        if (myPostsState.repostedPosts.isNotEmpty()) {
            items(
                myPostsState.repostedPosts.size,
                key = { index: Int -> myPostsState.repostedPosts[index].id },
            ) { i ->
                PostCard(
                    post = myPostsState.repostedPosts[i],
                    hubState = hubState,
                    isIncrementView = false,
                    onHubNavigate = onHubNavigate,
                    hubAction = hubAction,
                    postCardUiAction = postCardUiAction,
                    onProcessOfEngagementAction = myPostsAction.onProcessOfEngagementAction,
                )
                Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smallest)))
            }
        }
        if (!myPostsState.hasNoMoreRepostedPosts) {
            item {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(onLoading = { myPostsAction.onGetRepostedPosts() })
                }
            }
        }
    }
}
