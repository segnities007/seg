package com.segnities007.seg.ui.screens.hub.home.comment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.components.card.postcard.EngagementIconAction
import com.segnities007.seg.ui.components.card.postcard.EngagementIconState
import com.segnities007.seg.ui.components.card.postcard.PostCard
import com.segnities007.seg.ui.components.card.postcard.PostCardUiAction
import com.segnities007.seg.ui.components.card.postcard.PostCardUiState
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState

@Composable
fun Comment(
    modifier: Modifier = Modifier,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    engagementIconState: EngagementIconState,
    engagementIconAction: EngagementIconAction,
    postCardUiState: PostCardUiState,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Route) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ) {
        PostCard(
            post = postCardUiState.post,
            myself = hubUiState.user,
            hubUiAction = hubUiAction,
            postCardUiAction = postCardUiAction,
            engagementIconState = engagementIconState,
            engagementIconAction = engagementIconAction,
            onHubNavigate = onHubNavigate,
        )
    }
}

@Composable
private fun comments() {
}
