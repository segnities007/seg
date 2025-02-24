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
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState

@Composable
fun Comment(
    modifier: Modifier = Modifier,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    commentViewModel: CommentViewModel = hiltViewModel(),
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Navigation) -> Unit,
) {
    LaunchedEffect(Unit) {
        commentViewModel.onGetCommentUiAction().onGetComments(hubUiState.comment)
    }

    CommentUi(
        modifier = modifier,
        hubUiState = hubUiState,
        hubUiAction = hubUiAction,
        commentUiState = commentViewModel.commentUiState,
        commentUiAction = commentViewModel.onGetCommentUiAction(),
        postCardUiAction = postCardUiAction,
        onHubNavigate = onHubNavigate,
    )
}

@Composable
private fun CommentUi(
    modifier: Modifier = Modifier,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    commentUiState: CommentUiState,
    postCardUiAction: PostCardUiAction,
    commentUiAction: CommentUiAction,
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
                post = hubUiState.comment,
                hubUiState = hubUiState,
                hubUiAction = hubUiAction,
                postCardUiAction = postCardUiAction,
                onHubNavigate = onHubNavigate,
                onProcessOfEngagementAction = commentUiAction.onProcessOfEngagementAction,
            )
            Spacer(Modifier.padding(dimensionResource(R.dimen.padding_normal)))
        }
        items(
            commentUiState.comments.size,
            key = { index: Int -> commentUiState.comments[index].id },
        ) {
            PostCard(
                post = commentUiState.comments[it],
                hubUiState = hubUiState,
                hubUiAction = hubUiAction,
                postCardUiAction = postCardUiAction,
                onHubNavigate = onHubNavigate,
                onProcessOfEngagementAction = commentUiAction.onProcessOfEngagementAction,
            )
            Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
        }
    }
}
