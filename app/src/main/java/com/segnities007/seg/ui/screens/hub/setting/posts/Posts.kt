package com.segnities007.seg.ui.screens.hub.setting.posts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.segnities007.seg.R
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.components.card.EngagementIconAction
import com.segnities007.seg.ui.components.card.EngagementIconState
import com.segnities007.seg.ui.components.card.PostCard
import com.segnities007.seg.ui.components.card.PostCardUiAction
import com.segnities007.seg.ui.components.card.PostCardUiState
import com.segnities007.seg.ui.components.loading_indicator.LoadingUI
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState

@Composable
fun Posts(
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    engagementIconState: EngagementIconState,
    engagementIconAction: EngagementIconAction,
    postCardUiState: PostCardUiState,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Route) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(top = dimensionResource(R.dimen.padding_smaller)),
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
                engagementIconState = engagementIconState,
                engagementIconAction = engagementIconAction,
                postCardUiAction = postCardUiAction,
            )
        }
        // action for fetching before post
        item {
            Column {
                Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                LoadingUI(
                    onLoading = {
                        if (postCardUiState.posts.isNotEmpty()) {
                            postCardUiAction.onGetBeforePosts(postCardUiState.posts.last().updateAt)
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun DetailButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {

}

//@Composable
//private fun DetailsModalBottomSheet(
//    modifier: Modifier = Modifier,
//    onClick: () -> Unit,
//) {
//    ModalBottomSheet {
//
//    }
//}