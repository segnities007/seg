package com.example.feature.components.card.postcard

import androidx.compose.runtime.Composable
import com.example.domain.model.post.Genre
import com.example.domain.model.post.Post
import com.example.domain.presentation.navigation.NavigationHubRoute
import com.example.feature.components.card.haiku.HaikuCard
import com.example.feature.components.card.katauta.KatautaCard
import com.example.feature.components.card.sedouka.SedoukaCard
import com.example.feature.components.card.tanka.TankaCard
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.HubState
import com.example.feature.screens.hub.home.HomeAction

@Composable
fun PostCard(
    post: Post,
    hubState: HubState,
    isIncrementView: Boolean = true,
    onHubAction: (HubAction) -> Unit,
    onHomeAction: (HomeAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
    onHubNavigate: (NavigationHubRoute) -> Unit,
) {
    when (post.genre) {
        Genre.HAIKU ->
            HaikuCard(
                post = post,
                hubState = hubState,
                isIncrementView = isIncrementView,
                onHubAction = onHubAction,
                onPostCardAction = onPostCardAction,
                onHomeAction = onHomeAction,
                onHubNavigate = onHubNavigate,
            )

        Genre.TANKA ->
            TankaCard(
                post = post,
                hubState = hubState,
                isIncrementView = isIncrementView,
                onHubAction = onHubAction,
                onPostCardAction = onPostCardAction,
                onHomeAction = onHomeAction,
                onHubNavigate = onHubNavigate,
            )

        Genre.SEDOUKA ->
            SedoukaCard(
                post = post,
                hubState = hubState,
                isIncrementView = isIncrementView,
                onHubAction = onHubAction,
                onPostCardAction = onPostCardAction,
                onHomeAction = onHomeAction,
                onHubNavigate = onHubNavigate,
            )

        Genre.KATAUTA ->
            KatautaCard(
                post = post,
                hubState = hubState,
                isIncrementView = isIncrementView,
                onHubAction = onHubAction,
                onPostCardAction = onPostCardAction,
                onHomeAction = onHomeAction,
                onHubNavigate = onHubNavigate,
            )

        else ->
            DefaultPostCard(
                post = post,
                hubState = hubState,
                isIncrementView = isIncrementView,
                onHubAction = onHubAction,
                onPostCardAction = onPostCardAction,
                onHomeAction = onHomeAction,
                onHubNavigate = onHubNavigate,
            )
    }
}
