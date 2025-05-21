package com.example.feature.screens.hub.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.domain.model.post.Genre
import com.example.domain.presentation.navigation.Navigation
import com.example.feature.components.card.postcard.PostCardAction
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.HubState
import com.example.feature.screens.hub.home.haiku.Haiku
import com.example.feature.screens.hub.home.normal.Normal
import com.example.feature.screens.hub.home.tanka.Tanka

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
    when (homeState.currentGenre) {
        Genre.NORMAL -> {
            Normal(
                modifier = modifier,
                hubState = hubState,
                homeState = homeState,
                onHubAction = onHubAction,
                onHomeAction = onHomeAction,
                onPostCardAction = onPostCardAction,
                onHubNavigate = onHubNavigate,
            )
        }

        Genre.HAIKU -> {
            Haiku(
                modifier = modifier,
                hubState = hubState,
                homeState = homeState,
                onHubAction = onHubAction,
                onHomeAction = onHomeAction,
                onPostCardAction = onPostCardAction,
                onHubNavigate = onHubNavigate,
            )
        }

        Genre.TANKA -> {
            Tanka(
                modifier = modifier,
                hubState = hubState,
                homeState = homeState,
                onHubAction = onHubAction,
                onHomeAction = onHomeAction,
                onPostCardAction = onPostCardAction,
                onHubNavigate = onHubNavigate,
            )
        }

        Genre.KATAUTA -> {
            // TODO
        }

        else -> {}
    }
}
