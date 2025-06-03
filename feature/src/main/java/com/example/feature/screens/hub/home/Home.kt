package com.example.feature.screens.hub.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.domain.presentation.navigation.Navigation
import com.example.feature.components.card.postcard.PostCardAction
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.HubState
import com.example.feature.screens.hub.home.normal.Normal

@Composable
fun Home(
    modifier: Modifier,
    hubState: HubState,
    homeState: HomeState,
    onHubAction: (HubAction) -> Unit,
    onHomeAction: (HomeAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
    onHubNavigate: (Navigation) -> Unit,
) {
    Normal(
        modifier = modifier,
        hubState = hubState,
        homeState = homeState,
        onHubAction = onHubAction,
        onHomeAction = onHomeAction,
        onPostCardAction = onPostCardAction,
        onHubNavigate = onHubNavigate,
        onProcessOfEngagementAction = {
            onHomeAction(HomeAction.ChangeEngagementOfPost(it))
        },
    )
}
