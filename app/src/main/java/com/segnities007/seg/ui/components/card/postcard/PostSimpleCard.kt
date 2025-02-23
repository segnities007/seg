package com.segnities007.seg.ui.components.card.postcard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState

@Composable
fun PostSimpleCard(
    post: Post,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    postCardUiAction: PostCardUiAction,
    isIncrementView: Boolean = true,
    onHubNavigate: (NavigationHubRoute) -> Unit,
) {
    PostCardUi(
        post = post,
        hubUiState = hubUiState,
        hubUiAction = hubUiAction,
        postCardUiAction = postCardUiAction,
        isIncrementView = isIncrementView,
        onHubNavigate = onHubNavigate,
    ) {
        CardContents {
            Column(modifier = Modifier.padding(horizontal = commonPadding)) {
                Name()
                Description()
            }
        }
    }
}