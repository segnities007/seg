package com.segnities007.seg.ui.screens.hub.comment

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
import com.segnities007.seg.R
import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.components.card.postcard.PostCard
import com.segnities007.seg.ui.components.card.postcard.PostCardUiAction
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState

@Composable
fun Comment(
    modifier: Modifier = Modifier,
    hubState: HubState,
    hubAction: HubAction,
    commentViewModel: CommentViewModel = hiltViewModel(),
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Navigation) -> Unit,
) {
    LaunchedEffect(Unit) {
        commentViewModel.onGetCommentUiAction().onGetComments(hubState.comment)
    }

    CommentUi(
        modifier = modifier,
        hubState = hubState,
        hubAction = hubAction,
        commentState = commentViewModel.commentState,
        commentAction = commentViewModel.onGetCommentUiAction(),
        postCardUiAction = postCardUiAction,
        onHubNavigate = onHubNavigate,
    )
}

@Composable
private fun CommentUi(
    modifier: Modifier = Modifier,
    hubState: HubState,
    hubAction: HubAction,
    commentState: CommentState,
    postCardUiAction: PostCardUiAction,
    commentAction: CommentAction,
    onHubNavigate: (Navigation) -> Unit,
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
                hubAction = hubAction,
                postCardUiAction = postCardUiAction,
                onHubNavigate = onHubNavigate,
                onProcessOfEngagementAction = commentAction.onProcessOfEngagementAction,
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
                hubAction = hubAction,
                postCardUiAction = postCardUiAction,
                onHubNavigate = onHubNavigate,
                onProcessOfEngagementAction = commentAction.onProcessOfEngagementAction,
            )
            Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
        }
    }
}
