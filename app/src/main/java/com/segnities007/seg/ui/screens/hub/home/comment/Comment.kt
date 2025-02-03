package com.segnities007.seg.ui.screens.hub.home.comment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.segnities007.seg.domain.presentation.Route
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
    onHubNavigate: (Route) -> Unit,
) {
    LaunchedEffect(Unit) {
        val commentUiAction = commentViewModel.onGetCommentUiAction()
        commentUiAction.onGetComment(hubUiState.comment)
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
    onHubNavigate: (Route) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ) {
        PostCard(
            post = commentUiState.comment,
            hubUiState = hubUiState,
            hubUiAction = hubUiAction,
            postCardUiAction = postCardUiAction,
            onHubNavigate = onHubNavigate,
            onProcessOfEngagementAction = commentUiAction.onProcessOfEngagementAction,
        )
    }
}
