package com.segnities007.seg.ui.screens.hub.setting.my_posts

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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.segnities007.seg.R
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.components.card.postcard.EngagementIconAction
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
            engagementIconAction = myPostsViewModel.onGetEngagementIconAction(),
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
    engagementIconAction: EngagementIconAction,
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
                engagementIconAction = engagementIconAction,
                postCardUiAction = postCardUiAction,
                onHubNavigate = onHubNavigate,
            )
        1 ->
            Likes(
                myPostsUiState = myPostsUiState,
                myPostsUiAction = myPostsUiAction,
                hubUiState = hubUiState,
                hubUiAction = hubUiAction,
                engagementIconAction = engagementIconAction,
                postCardUiAction = postCardUiAction,
                onHubNavigate = onHubNavigate,
            )
        2 ->
            Reposts(
                myPostsUiState = myPostsUiState,
                myPostsUiAction = myPostsUiAction,
                hubUiState = hubUiState,
                hubUiAction = hubUiAction,
                engagementIconAction = engagementIconAction,
                postCardUiAction = postCardUiAction,
                onHubNavigate = onHubNavigate,
            )
    }
}

@Composable
private fun Tabs(
    modifier: Modifier = Modifier,
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
    modifier: Modifier = Modifier,
    myPostsUiState: MyPostsUiState,
    myPostsUiAction: MyPostsUiAction,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    engagementIconAction: EngagementIconAction,
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
                    myself = hubUiState.user,
                    isShowDetailButton = true,
                    isIncrementView = false,
                    onHubNavigate = onHubNavigate,
                    hubUiAction = hubUiAction,
                    engagementIconAction = engagementIconAction,
                    postCardUiAction = postCardUiAction,
                )
            }
        }
        // action for fetching before post
        if (!myPostsUiState.hasNoMorePosts) {
            item {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(
                        onLoading = {
                            if(myPostsUiState.posts.isNotEmpty())
                                myPostsUiAction.onGetBeforePosts(myPostsUiState.posts.last().createAt)
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun Likes(
    modifier: Modifier = Modifier,
    myPostsUiState: MyPostsUiState,
    myPostsUiAction: MyPostsUiAction,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    engagementIconAction: EngagementIconAction,
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
                    myself = hubUiState.user,
                    isShowDetailButton = true,
                    isIncrementView = false,
                    onHubNavigate = onHubNavigate,
                    hubUiAction = hubUiAction,
                    engagementIconAction = engagementIconAction,
                    postCardUiAction = postCardUiAction,
                )
            }
        }
        // action for fetching before post
        if (!myPostsUiState.hasNoMoreLikedPosts) {
            item {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(
                        onLoading = {
                            if(myPostsUiState.posts.isNotEmpty())
                                myPostsUiAction.onGetBeforeLikedPosts(myPostsUiState.self.likes, myPostsUiState.likedPosts.last().id)
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun Reposts(
    modifier: Modifier = Modifier,
    myPostsUiState: MyPostsUiState,
    myPostsUiAction: MyPostsUiAction,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    engagementIconAction: EngagementIconAction,
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
                    myself = hubUiState.user,
                    isShowDetailButton = true,
                    isIncrementView = false,
                    onHubNavigate = onHubNavigate,
                    hubUiAction = hubUiAction,
                    engagementIconAction = engagementIconAction,
                    postCardUiAction = postCardUiAction,
                )
            }
        }
        // action for fetching before post
        if(!myPostsUiState.hasNoMoreRepostedPosts){
            item {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(
                        onLoading = {
                            if(myPostsUiState.repostedPosts.isNotEmpty())
                                myPostsUiAction.onGetBeforeRepostedPosts(myPostsUiState.self.reposts, myPostsUiState.repostedPosts.last().id)
                        },
                    )
                }
            }
        }
    }
}
