package com.segnities007.seg.ui.screens.hub.post

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.segnities007.seg.R
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.components.card.postcard.PostCard
import com.segnities007.seg.ui.components.card.postcard.PostCardUiAction
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState
import com.segnities007.seg.ui.screens.hub.comment.CommentViewModel
import com.segnities007.seg.ui.screens.hub.home.HomeUiAction

@Composable
fun PostForComment(
    modifier: Modifier = Modifier,
    homeUiAction: HomeUiAction,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Navigation) -> Unit, // go to home
) {
    val commentViewModel: CommentViewModel = hiltViewModel()
    val postViewModel: PostViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        val commentUiAction = commentViewModel.onGetCommentUiAction()
        commentUiAction.onGetComment(hubUiState.comment)
    }

    PostUi(
        modifier = modifier,
        homeUiAction = homeUiAction,
        hubUiState = hubUiState,
        hubUiAction = hubUiAction,
        postUiState = postViewModel.postUiState,
        postUiAction = postViewModel.onGetPostUiAction(),
        onHubNavigate = onHubNavigate,
    ) {
        Column {
            TopToolBar()
            Box(
                modifier = Modifier.clickable(false) {},
            ) {
                PostCard(
                    post = commentViewModel.commentUiState.comment,
                    hubUiState = hubUiState,
                    hubUiAction = hubUiAction,
                    postCardUiAction = postCardUiAction,
                    isIncrementView = false,
                    onHubNavigate = onHubNavigate,
                    onProcessOfEngagementAction = commentViewModel.onGetCommentUiAction().onProcessOfEngagementAction,
                )
            }
            Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
            InputField(
                modifier = Modifier.weight(1f),
                label = { Text(stringResource(R.string.post_comment_label)) },
            )
        }
    }
}

@Composable
@Preview
private fun PostPreview() {
    PostUi(
        modifier = Modifier,
        homeUiAction =
            HomeUiAction(
                onGetNewPosts = {},
                onGetBeforeNewPosts = {},
                onChangeHasNoMorePost = {},
                onProcessOfEngagementAction = {},
            ),
        hubUiState = HubUiState(),
        hubUiAction =
            HubUiAction(
                onUpdateSelf = {},
                onChangeIsHideTopBar = {},
                onResetIsHideTopBar = {},
                onGetUser = {},
                onSetComment = {},
                onSetUserID = {},
                onSetAccounts = {},
                onAddPostIDToMyLikes = {},
                onRemovePostIDFromMyLikes = {},
                onAddPostIDToMyReposts = {},
                onRemovePostIDFromMyReposts = {},
                onChangeCurrentRouteName = {},
            ),
        postUiState = PostUiState(),
        postUiAction =
            PostUiAction(
                onUpdateIsLoading = {},
                onUpdateInputText = {},
                onCreatePost = { a, b, c, d -> },
            ),
        onHubNavigate = {},
    ) {
        Column {
            TopToolBar()
            PostCard(
                post = Post(),
                hubUiState = hubUiState,
                hubUiAction = hubUiAction,
                postCardUiAction =
                    PostCardUiAction(
                        onDeletePost = { _, _, _, _ -> },
                        onClickIcon = {},
                        onClickPostCard = {},
                        onIncrementViewCount = {},
                        onLike = { _, _, _, _ -> },
                        onRepost = { _, _, _, _ -> },
                    ),
                onHubNavigate = {},
            ) { }
            Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
            InputField(
                modifier = Modifier.weight(1f),
                label = { Text(stringResource(R.string.post_comment_label)) },
            )
        }
    }
}
