package com.segnities007.seg.ui.screens.hub.setting.my_posts

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.segnities007.seg.R
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.components.card.postcard.PostCard
import com.segnities007.seg.ui.components.card.postcard.PostCardUiAction
import com.segnities007.seg.ui.components.indicator.LoadingUI
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState

@Composable
fun MyPosts(
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    myPostsViewModel: MyPostsViewModel = hiltViewModel(),
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Route) -> Unit,
) {
    LaunchedEffect(Unit) {
        hubUiAction.onChangeIsHideTopBar()
        val action = myPostsViewModel.onGetMyPostsUiAction()
        action.onGetSelf(hubUiState.user)
        action.onInit()
    }

    Column {
        Tabs(
            myPostsUiState = myPostsViewModel.myPostsUiState,
            myPostsUiAction = myPostsViewModel.onGetMyPostsUiAction(),
        )
        MyPostsUi(
            hubUiState = hubUiState,
            hubUiAction = hubUiAction,
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
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Route) -> Unit,
) {
    when (myPostsUiState.selectedTabIndex) {
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
private fun Tabs(
    myPostsUiState: MyPostsUiState,
    myPostsUiAction: MyPostsUiAction,
) {
    Column {
        TabRow(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedTabIndex = myPostsUiState.selectedTabIndex,
        ) {
            myPostsUiState.titles.forEachIndexed { index, title ->
                Tab(
                    selected = myPostsUiState.selectedTabIndex == index,
                    onClick = { myPostsUiAction.onUpdateSelectedTabIndex(index) },
                    text = { Text(text = title) },
                )
            }
        }
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
                PostCard(
                    post = myPostsUiState.posts[i],
                    hubUiState = hubUiState,
                    isShowDetailButton = true,
                    isIncrementView = false,
                    onHubNavigate = onHubNavigate,
                    hubUiAction = hubUiAction,
                    postCardUiAction = postCardUiAction,
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
                    isShowDetailButton = true,
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
                    isShowDetailButton = true,
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
