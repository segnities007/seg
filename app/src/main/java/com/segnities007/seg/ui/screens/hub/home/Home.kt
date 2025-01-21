package com.segnities007.seg.ui.screens.hub.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.components.card.PostCard
import com.segnities007.seg.ui.components.card.PostCardUiAction
import com.segnities007.seg.ui.components.card.PostCardUiState
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState

@Composable
fun Home(
    modifier: Modifier,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    postCardUiState: PostCardUiState,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Route) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        items(
            postCardUiState.posts.size,
            key = { index: Int -> postCardUiState.posts[index].id },
        ) { i ->
            PostCard(
                post = postCardUiState.posts[i],
                myself = hubUiState.user,
                onHubNavigate = onHubNavigate,
                hubUiAction = hubUiAction,
                postCardUiAction = postCardUiAction,
            )
        }
    }
}
