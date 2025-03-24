package com.segnities007.seg.ui.screens.hub.post

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.segnities007.seg.R
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.components.button.SmallButton
import com.segnities007.seg.ui.components.card.postcard.PostCard
import com.segnities007.seg.ui.components.card.postcard.PostCardUiAction
import com.segnities007.seg.ui.components.card.postcard.PostSimpleCard
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState
import com.segnities007.seg.ui.screens.hub.comment.CommentViewModel
import com.segnities007.seg.ui.screens.hub.home.HomeAction

@Composable
fun PostForComment(
    modifier: Modifier = Modifier,
    homeAction: HomeAction,
    hubState: HubState,
    hubAction: HubAction,
    postCardUiAction: PostCardUiAction,
    onBackHubNavigate: () -> Unit,
    onHubNavigate: (Navigation) -> Unit, // go to home
) {
    val commentViewModel: CommentViewModel = hiltViewModel()
    val postViewModel: PostViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        val commentUiAction = commentViewModel.onGetCommentUiAction()
        commentUiAction.onGetComments(hubState.comment)
    }

    PostUi(
        modifier = modifier,
        homeAction = homeAction,
        hubState = hubState,
        hubAction = hubAction,
        postState = postViewModel.postState,
        postAction = postViewModel.onGetPostUiAction(),
        onHubNavigate = onHubNavigate,
    ) {
        Column {
            Box {
                PostCard(
                    post = hubState.comment,
                    hubState = hubState,
                    hubAction = hubAction,
                    postCardUiAction = postCardUiAction,
                    isIncrementView = false,
                    onHubNavigate = onHubNavigate,
                    onProcessOfEngagementAction = commentViewModel.onGetCommentUiAction().onProcessOfEngagementAction,
                )
                Spacer( // Prevent to click PostCard
                    modifier =
                        Modifier
                            .matchParentSize()
                            .clickable(enabled = false, onClick = {}),
                )
            }
            TopToolBarForCommentForComment(
                onBackHubNavigate = onBackHubNavigate,
            )
            InputField(
                modifier = Modifier.weight(1f),
                label = { Text(stringResource(R.string.post_comment_label)) },
            )
        }
    }
}

@Composable
fun PostScope.TopToolBarForCommentForComment(
    modifier: Modifier = Modifier,
    onBackHubNavigate: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .padding(vertical = dimensionResource(R.dimen.padding_normal))
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SmallButton(textID = R.string.back, onClick = onBackHubNavigate)
        Spacer(modifier = Modifier.weight(1f))
        SmallButton(
            textID = R.string.post,
            onClick = {
                postAction.onCreateComment(
                    hubState,
                    hubAction,
                    postAction.onUpdateIsLoading,
                ) {
                    postAction.onUpdateInputText("")
                    homeAction.onGetNewPosts()
                    onBackHubNavigate()
                }
            },
        )
    }
}

@Composable
@Preview
private fun PostPreview() {
    PostUi(
        modifier = Modifier,
        homeAction =
            HomeAction(
                onGetNewPosts = {},
                onGetBeforeNewPosts = {},
                onChangeHasNoMorePost = {},
                onProcessOfEngagementAction = {},
            ),
        hubState = HubState(),
        hubAction =
            HubAction(
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
        postState = PostState(),
        postAction =
            PostAction(
                onUpdateIsLoading = {},
                onUpdateInputText = {},
                onCreatePost = { a, b, c, d -> },
                onCreateComment = { _, _, _, _ -> },
            ),
        onHubNavigate = {},
    ) {
        Column {
            PostSimpleCard(
                post = Post(),
                hubState = hubState,
                hubAction = hubAction,
                postCardUiAction =
                    PostCardUiAction(
                        onDeletePost = {_, _, _, _, _ -> },
                        onClickIcon = {},
                        onClickPostCard = {},
                        onIncrementViewCount = {},
                        onLike = { _, _, _, _ -> },
                        onRepost = { _, _, _, _ -> },
                    ),
                onHubNavigate = {},
            )
            TopToolBarForCommentForComment {}
            InputField(
                modifier = Modifier.weight(1f),
                label = { Text(stringResource(R.string.post_comment_label)) },
            )
        }
    }
}
