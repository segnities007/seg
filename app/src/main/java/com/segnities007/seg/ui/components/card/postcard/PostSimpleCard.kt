package com.segnities007.seg.ui.components.card.postcard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.segnities007.seg.R
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState

@Composable
fun PostSimpleCard(
    post: Post,
    hubState: HubState,
    hubAction: HubAction,
    postCardUiAction: PostCardUiAction,
    isIncrementView: Boolean = true,
    onHubNavigate: (NavigationHubRoute) -> Unit,
) {
    PostCardUi(
        post = post,
        hubState = hubState,
        hubAction = hubAction,
        postCardUiAction = postCardUiAction,
        isIncrementView = isIncrementView,
        onHubNavigate = onHubNavigate,
    ) {
        CardContents {
            Column(modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_sn))) {
                Name()
                Description()
            }
        }
    }
}
