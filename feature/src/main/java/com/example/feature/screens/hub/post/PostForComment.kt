package com.example.feature.screens.hub.post

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
import com.example.domain.model.post.Post
import com.example.domain.presentation.navigation.Navigation
import com.example.feature.R
import com.example.feature.components.button.SmallButton
import com.example.feature.components.card.postcard.PostCard
import com.example.feature.components.card.postcard.PostCardAction
import com.example.feature.components.card.postcard.PostSimpleCard
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.HubState
import com.example.feature.screens.hub.comment.CommentAction
import com.example.feature.screens.hub.comment.CommentViewModel
import com.example.feature.screens.hub.home.HomeAction

@Composable
fun PostForComment(
    modifier: Modifier = Modifier,
    hubState: HubState,
    onHubAction: (HubAction) -> Unit,
    onHomeAction: (HomeAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
    onBackHubNavigate: () -> Unit,
    onHubNavigate: (Navigation) -> Unit,
) {
    val commentViewModel: CommentViewModel = hiltViewModel()
    val postViewModel: PostViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        commentViewModel.onCommentAction(CommentAction.GetComments(hubState.comment))
    }

    PostUi(
        modifier = modifier,
        hubState = hubState,
        postState = postViewModel.postState,
        onHubAction = onHubAction,
        onHomeAction = onHomeAction,
        onPostAction = postViewModel::onPostAction,
        onHubNavigate = onHubNavigate,
    ) {
        Column {
            Box {
                PostCard(
                    post = hubState.comment,
                    hubState = hubState,
                    isIncrementView = false,
                    onPostCardAction = onPostCardAction,
                    onHubAction = onHubAction,
                    onHubNavigate = onHubNavigate,
                    onProcessOfEngagementAction = {
                        commentViewModel.onCommentAction(
                            CommentAction.ProcessOfEngagementAction(
                                it,
                            ),
                        )
                    },
                )
                Spacer(
                    // Prevent to click PostCard
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
                onPostAction(
                    PostAction.CreateComment(
                        hubState = hubState,
                        onHubAction = onHubAction,
                        onUpdateIsLoading = { onPostAction(PostAction.UpdateIsLoading(it)) },
                        onNavigate = onHubNavigate,
                    ),
                )
            },
        )
    }
}

@Composable
@Preview
private fun PostPreview() {
    PostUi(
        modifier = Modifier,
        hubState = HubState(),
        postState = PostState(),
        onHubAction = {},
        onHomeAction = {},
        onPostAction = {},
        onHubNavigate = {},
    ) {
        Column {
            PostSimpleCard(
                post = Post(),
                hubState = hubState,
                onHubNavigate = {},
                onHubAction = {},
                onPostCardAction = {},
            )
            TopToolBarForCommentForComment {}
            InputField(
                modifier = Modifier.weight(1f),
                label = { Text(stringResource(R.string.post_comment_label)) },
            )
        }
    }
}
