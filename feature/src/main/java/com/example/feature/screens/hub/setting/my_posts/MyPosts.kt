package com.example.feature.screens.hub.setting.my_posts

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
import com.example.domain.presentation.navigation.Navigation
import com.example.feature.R
import com.example.feature.components.card.postcard.PostCard
import com.example.feature.components.card.postcard.PostCardAction
import com.example.feature.components.card.postcard.PostCardWithDetailButton
import com.example.feature.components.indicator.LoadingUI
import com.example.feature.components.tab.TabAction
import com.example.feature.components.tab.TabState
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.HubState
import com.example.feature.screens.hub.home.HomeAction

@Composable
fun MyPosts(
    hubState: HubState,
    tabState: TabState,
    onTabAction: (TabAction) -> Unit,
    onHubAction: (HubAction) -> Unit,
    onHomeAction: (HomeAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
    onHubNavigate: (Navigation) -> Unit,
) {
    val myPostsViewModel: MyPostsViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        onHubAction(HubAction.ChangeIsHideTopBar)
        myPostsViewModel.onMyPostsAction(MyPostsAction.SetSelf(hubState.self))
        myPostsViewModel.onMyPostsAction(MyPostsAction.Init)
    }

    DisposableEffect(Unit) {
        onDispose {
            onTabAction(TabAction.UpdateIndex(0))
        }
    }

    Column {
        MyPostsUi(
            hubState = hubState,
            tabState = tabState,
            myPostsState = myPostsViewModel.myPostsState,
            onHubNavigate = onHubNavigate,
            onHubAction = onHubAction,
            onHomeAction = onHomeAction,
            onMyPostsAction = myPostsViewModel::onMyPostsAction,
            onPostCardAction = onPostCardAction,
        )
    }
}

@Composable
private fun MyPostsUi(
    hubState: HubState,
    myPostsState: MyPostsState,
    tabState: TabState,
    onHubAction: (HubAction) -> Unit,
    onHomeAction: (HomeAction) -> Unit,
    onMyPostsAction: (MyPostsAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
    onHubNavigate: (Navigation) -> Unit,
) {
    when (tabState.index) {
        0 ->
            Posts(
                myPostsState = myPostsState,
                hubState = hubState,
                onHubNavigate = onHubNavigate,
                onHubAction = onHubAction,
                onHomeAction = onHomeAction,
                onMyPostsAction = onMyPostsAction,
                onPostCardAction = onPostCardAction,
            )

        1 ->
            Likes(
                myPostsState = myPostsState,
                hubState = hubState,
                onHubNavigate = onHubNavigate,
                onHubAction = onHubAction,
                onMyPostsAction = onMyPostsAction,
                onPostCardAction = onPostCardAction,
            )

        2 ->
            Reposts(
                myPostsState = myPostsState,
                hubState = hubState,
                onHubNavigate = onHubNavigate,
                onHubAction = onHubAction,
                onMyPostsAction = onMyPostsAction,
                onPostCardAction = onPostCardAction,
            )
    }
}

@Composable
private fun Posts(
    myPostsState: MyPostsState,
    hubState: HubState,
    onHubAction: (HubAction) -> Unit,
    onHomeAction: (HomeAction) -> Unit,
    onMyPostsAction: (MyPostsAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
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
                    hubState = hubState,
                    onHubNavigate = onHubNavigate,
                    onPostCardAction = onPostCardAction,
                    onHubAction = onHubAction,
                    onHomeAction = onHomeAction,
                    onMyPostsAction = onMyPostsAction,
                )
                Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smallest)))
            }
        }
        if (!myPostsState.hasNoMorePosts) {
            item {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(onLoading = { onMyPostsAction(MyPostsAction.GetPosts(onHubAction)) })
                }
            }
        }
    }
}

@Composable
private fun Likes(
    hubState: HubState,
    myPostsState: MyPostsState,
    onHubAction: (HubAction) -> Unit,
    onMyPostsAction: (MyPostsAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
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
                    onHubNavigate = onHubNavigate,
                    onPostCardAction = onPostCardAction,
                    onHubAction = onHubAction,
                    isIncrementView = false,
                    onProcessOfEngagementAction = { newPost ->
                        onMyPostsAction(
                            MyPostsAction.ProcessOfEngagement(
                                newPost,
                            ),
                        )
                    },
                )
                Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smallest)))
            }
        }
        if (!myPostsState.hasNoMoreLikedPosts) {
            item {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(onLoading = { onMyPostsAction(MyPostsAction.GetLikedPosts(onHubAction)) })
                }
            }
        }
    }
}

@Composable
private fun Reposts(
    myPostsState: MyPostsState,
    hubState: HubState,
    onHubAction: (HubAction) -> Unit,
    onMyPostsAction: (MyPostsAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
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
                    onHubNavigate = onHubNavigate,
                    onPostCardAction = onPostCardAction,
                    onHubAction = onHubAction,
                    isIncrementView = false,
                    onProcessOfEngagementAction = { newPost ->
                        onMyPostsAction(
                            MyPostsAction.ProcessOfEngagement(
                                newPost,
                            ),
                        )
                    },
                )
                Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smallest)))
            }
        }
        if (!myPostsState.hasNoMoreRepostedPosts) {
            item {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(onLoading = { onMyPostsAction(MyPostsAction.GetRepostedPosts(onHubAction)) })
                }
            }
        }
    }
}
