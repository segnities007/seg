package com.example.feature.screens.hub.comment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.model.post.Post
import com.example.domain.presentation.navigation.Navigation
import com.example.feature.R
import com.example.feature.components.card.postcard.PostCard
import com.example.feature.components.card.postcard.PostCardAction
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.HubState

@Composable
fun Comment(
    modifier: Modifier = Modifier,
    hubState: HubState,
    onHubAction: (HubAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
    onHubNavigate: (Navigation) -> Unit,
) {
    val commentViewModel: CommentViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        commentViewModel.onCommentAction(CommentAction.GetComments(hubState.comment, onHubAction = onHubAction))
    }

    CommentUi(
        modifier = modifier,
        hubState = hubState,
        commentState = commentViewModel.commentState,
        onHubAction = onHubAction,
        onPostCardAction = onPostCardAction,
        onHubNavigate = onHubNavigate,
        onProcessOfEngagementAction = {
            commentViewModel.onCommentAction(CommentAction.ProcessOfEngagementAction(it))
        },
    )
}

@Composable
private fun CommentUi(
    modifier: Modifier = Modifier,
    hubState: HubState,
    commentState: CommentState,
    onHubAction: (HubAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
    onHubNavigate: (Navigation) -> Unit,
    onProcessOfEngagementAction: (newPost: Post) -> Unit,
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
        verticalArrangement = Arrangement.Top,
    ) {
        item {
            PostCard(
                post = hubState.comment,
                hubState = hubState,
                onHubNavigate = onHubNavigate,
                onHubAction = onHubAction,
                onPostCardAction = onPostCardAction,
                onProcessOfEngagementAction = onProcessOfEngagementAction,
            )
            Spacer(Modifier.padding(dimensionResource(R.dimen.padding_normal)))
        }
        items(
            commentState.comments.size,
            key = { index: Int -> commentState.comments[index].id },
        ) {
            PostCard(
                post = commentState.comments[it],
                hubState = hubState,
                onHubNavigate = onHubNavigate,
                onHubAction = onHubAction,
                onPostCardAction = onPostCardAction,
                onProcessOfEngagementAction = onProcessOfEngagementAction,
            )
            Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
        }
    }
}
