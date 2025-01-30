package com.segnities007.seg.ui.screens.hub.home.comment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.components.card.postcard.EngagementIconState
import com.segnities007.seg.ui.components.card.postcard.PostCard
import com.segnities007.seg.ui.components.card.postcard.PostCardUiAction
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState

@Composable
fun Comment(
    modifier: Modifier = Modifier,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    engagementIconState: EngagementIconState,
    commentViewModel: CommentViewModel = hiltViewModel(),
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Route) -> Unit,
) {
    LaunchedEffect(Unit) {
        val commentUiAction = commentViewModel.onGetCommentUiAction()
        commentUiAction.onGetComment(hubUiState.comment)
        // TODO get comments
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ) {
        PostCard(
            post = commentViewModel.commentUiState.comment,
            myself = hubUiState.user,
            hubUiAction = hubUiAction,
            postCardUiAction = postCardUiAction,
            engagementIconState = engagementIconState,
            engagementIconAction = commentViewModel.onGetEngagementIconAction(),
            onHubNavigate = onHubNavigate,
        )
    }
}
