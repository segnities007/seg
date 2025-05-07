package com.example.feature.components.card.postcard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.domain.model.post.Post
import com.example.domain.presentation.navigation.NavigationHubRoute
import com.example.feature.R
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.HubState

@Composable
fun PostSimpleCard(
    post: Post,
    hubState: HubState,
    isIncrementView: Boolean = true,
    onHubAction: (HubAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
    onHubNavigate: (NavigationHubRoute) -> Unit,
) {
    PostCardUi(
        post = post,
        hubState = hubState,
        isIncrementView = isIncrementView,
        onHubAction = onHubAction,
        onPostCardAction = onPostCardAction,
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
