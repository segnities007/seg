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
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.components.card.postcard.PostCard
import com.segnities007.seg.ui.components.card.postcard.PostCardUiAction
import com.segnities007.seg.ui.components.card.postcard.PostCardWithDetailButton
import com.segnities007.seg.ui.components.indicator.LoadingUI
import com.segnities007.seg.ui.components.tab.TabUiAction
import com.segnities007.seg.ui.components.tab.TabUiState
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState

@Composable
fun MyPosts(
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    tabUiState: TabUiState,
    tabUiAction: TabUiAction,
    myPostsViewModel: MyPostsViewModel = hiltViewModel(),
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Route) -> Unit,
) {
    LaunchedEffect(Unit) {
        hubUiAction.onChangeIsHideTopBar()
        val action = myPostsViewModel.onGetMyPostsUiAction()
        action.onSetSelf(hubUiState.user)
        action.onInit()
    }

    DisposableEffect(Unit) {
        onDispose {
            tabUiAction.onUpdateIndex(0)
        }
    }

    Column {
        MyPostsUi(
            hubUiState = hubUiState,
            hubUiAction = hubUiAction,
            tabUiState = tabUiState,
            myPostsUiState = myPostsViewModel.myPostsUiState,
            myPostsUiAction = myPostsViewModel.onGetMyPostsUiAction(),
            postCardUiAction = postCardUiAction,
            onHubNavigate = onHubNavigate,
        )
    }
}

@Composable
private fun MyPostsUi(
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    myPostsUiState: MyPostsUiState,
    myPostsUiAction: MyPostsUiAction,
    tabUiState: TabUiState,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Route) -> Unit,
) {
    when (tabUiState.index) {
        0 ->
            Posts(
                myPostsUiState = myPostsUiState,
                myPostsUiAction = myPostsUiAction,
                hubUiState = hubUiState,
                hubUiAction = hubUiAction,
                postCardUiAction = postCardUiAction,
                onHubNavigate = onHubNavigate,
            )
        1 ->
            Likes(
                myPostsUiState = myPostsUiState,
                myPostsUiAction = myPostsUiAction,
                hubUiState = hubUiState,
                hubUiAction = hubUiAction,
                postCardUiAction = postCardUiAction,
                onHubNavigate = onHubNavigate,
            )
        2 ->
            Reposts(
                myPostsUiState = myPostsUiState,
                myPostsUiAction = myPostsUiAction,
                hubUiState = hubUiState,
                hubUiAction = hubUiAction,
                postCardUiAction = postCardUiAction,
                onHubNavigate = onHubNavigate,
            )
    }
}

@Composable
private fun Posts(
    myPostsUiState: MyPostsUiState,
    myPostsUiAction: MyPostsUiAction,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Route) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(top = dimensionResource(R.dimen.padding_smallest)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        if (myPostsUiState.posts.isNotEmpty()) {
            items(
                myPostsUiState.posts.size,
                key = { index: Int -> myPostsUiState.posts[index].id },
            ) { i ->
                PostCardWithDetailButton(
                    post = myPostsUiState.posts[i],
                    hubUiState = hubUiState,
                    hubUiAction = hubUiAction,
                    myPostsUiAction = myPostsUiAction,
                    postCardUiAction = postCardUiAction,
                    onHubNavigate = onHubNavigate,
                    onProcessOfEngagementAction = myPostsUiAction.onProcessOfEngagementAction,
                )
            }
        }
        if (!myPostsUiState.hasNoMorePosts) {
            item {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(onLoading = { myPostsUiAction.onGetPosts() })
                }
            }
        }
    }
}

@Composable
private fun Likes(
    myPostsUiState: MyPostsUiState,
    myPostsUiAction: MyPostsUiAction,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Route) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(top = dimensionResource(R.dimen.padding_smallest)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        if (myPostsUiState.likedPosts.isNotEmpty()) {
            items(
                myPostsUiState.likedPosts.size,
                key = { index: Int -> myPostsUiState.likedPosts[index].id },
            ) { i ->
                PostCard(
                    post = myPostsUiState.likedPosts[i],
                    hubUiState = hubUiState,
                    isIncrementView = false,
                    onHubNavigate = onHubNavigate,
                    hubUiAction = hubUiAction,
                    postCardUiAction = postCardUiAction,
                    onProcessOfEngagementAction = myPostsUiAction.onProcessOfEngagementAction,
                )
            }
        }
        if (!myPostsUiState.hasNoMoreLikedPosts) {
            item {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(onLoading = { myPostsUiAction.onGetLikedPosts() })
                }
            }
        }
    }
}

@Composable
private fun Reposts(
    myPostsUiState: MyPostsUiState,
    myPostsUiAction: MyPostsUiAction,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Route) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(top = dimensionResource(R.dimen.padding_smallest)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        if (myPostsUiState.repostedPosts.isNotEmpty()) {
            items(
                myPostsUiState.repostedPosts.size,
                key = { index: Int -> myPostsUiState.repostedPosts[index].id },
            ) { i ->
                PostCard(
                    post = myPostsUiState.repostedPosts[i],
                    hubUiState = hubUiState,
                    isIncrementView = false,
                    onHubNavigate = onHubNavigate,
                    hubUiAction = hubUiAction,
                    postCardUiAction = postCardUiAction,
                    onProcessOfEngagementAction = myPostsUiAction.onProcessOfEngagementAction,
                )
            }
        }
        if (!myPostsUiState.hasNoMoreRepostedPosts) {
            item {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(onLoading = { myPostsUiAction.onGetRepostedPosts() })
                }
            }
        }
    }
}
