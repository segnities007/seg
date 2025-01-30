package com.segnities007.seg.ui.screens.hub.setting.my_posts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
        val action = myPostsViewModel.onGetMyPostsUiAction()
        action.onGetSelf(hubUiState.user)
        action.onGetPosts()
    }

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
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(top = dimensionResource(R.dimen.padding_smaller)),
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
        if (myPostsUiState.posts.isNotEmpty() && !myPostsUiState.hasNoMorePost) {
            item {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(
                        onLoading = {
                            myPostsUiAction.onGetBeforePosts(myPostsUiState.posts.last().createAt)
                        },
                    )
                }
            }
        }
    }
}
