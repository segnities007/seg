package com.example.feature.screens.hub.home.normal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.domain.model.post.Genre
import com.example.domain.model.post.Post
import com.example.domain.presentation.navigation.Navigation
import com.example.feature.R
import com.example.feature.components.card.postcard.PostCard
import com.example.feature.components.card.postcard.PostCardAction
import com.example.feature.components.indicator.LoadingUI
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.HubState
import com.example.feature.screens.hub.home.HomeAction
import com.example.feature.screens.hub.home.HomeState

@Composable
fun Normal(
    modifier: Modifier,
    hubState: HubState,
    homeState: HomeState,
    onHubAction: (HubAction) -> Unit,
    onHomeAction: (HomeAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
    onHubNavigate: (Navigation) -> Unit,
    onProcessOfEngagementAction: (newPost: Post) -> Unit,
) {
    LazyColumn(
        state =
            when (homeState.currentGenre) {
                Genre.KATAUTA -> homeState.lazyListStateOfKatauta
                Genre.SEDOUKA -> homeState.lazyListStateOfSedouka
                Genre.TANKA -> homeState.lazyListStateOfTanka
                Genre.HAIKU -> homeState.lazyListStateOfHaiku
                else -> homeState.lazyListStateOfPost
            },
        modifier =
            modifier
                .fillMaxSize()
                .padding(
                    top = dimensionResource(R.dimen.padding_smallest),
                    start = dimensionResource(R.dimen.padding_small),
                    end = dimensionResource(R.dimen.padding_small),
                ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        items(
            count =
                when (homeState.currentGenre) {
                    Genre.KATAUTA -> homeState.katautas.size
                    Genre.SEDOUKA -> homeState.sedoukas.size
                    Genre.TANKA -> homeState.tankas.size
                    Genre.HAIKU -> homeState.haikus.size
                    else -> homeState.posts.size
                },
            key = { index: Int ->
                when (homeState.currentGenre) {
                    Genre.KATAUTA -> homeState.katautas[index].id
                    Genre.SEDOUKA -> homeState.sedoukas[index].id
                    Genre.TANKA -> homeState.tankas[index].id
                    Genre.HAIKU -> homeState.haikus[index].id
                    else -> homeState.posts[index].id
                }
            },
        ) { i ->
            PostCard(
                post =
                    when (homeState.currentGenre) {
                        Genre.KATAUTA -> homeState.katautas[i]
                        Genre.SEDOUKA -> homeState.sedoukas[i]
                        Genre.TANKA -> homeState.tankas[i]
                        Genre.HAIKU -> homeState.haikus[i]
                        else -> homeState.posts[i]
                    },
                hubState = hubState,
                isIncrementView = true,
                onHubNavigate = onHubNavigate,
                onHubAction = onHubAction,
                onPostCardAction = onPostCardAction,
                onProcessOfEngagementAction = onProcessOfEngagementAction,
            )
            Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smallest)))
        }
        item {
            if (
                !homeState.isAllPostsFetched &&
                when (homeState.currentGenre) {
                    Genre.KATAUTA -> homeState.katautas.isNotEmpty()
                    Genre.SEDOUKA -> homeState.sedoukas.isNotEmpty()
                    Genre.TANKA -> homeState.tankas.isNotEmpty()
                    Genre.HAIKU -> homeState.haikus.isNotEmpty()
                    else -> homeState.posts.isNotEmpty()
                }
            ) {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(
                        onLoading = {
                            if (
                                when (homeState.currentGenre) {
                                    Genre.KATAUTA -> homeState.katautas.isNotEmpty()
                                    Genre.SEDOUKA -> homeState.sedoukas.isNotEmpty()
                                    Genre.TANKA -> homeState.tankas.isNotEmpty()
                                    Genre.HAIKU -> homeState.haikus.isNotEmpty()
                                    else -> homeState.posts.isNotEmpty()
                                }
                            ) {
                                onHomeAction(
                                    when (homeState.currentGenre) {
                                        Genre.KATAUTA ->
                                            HomeAction.GetBeforeNewPosts(
                                                updatedAt = homeState.katautas.last().updateAt,
                                                genre = Genre.KATAUTA,
                                            )

                                        Genre.SEDOUKA ->
                                            HomeAction.GetBeforeNewPosts(
                                                updatedAt = homeState.sedoukas.last().updateAt,
                                                genre = Genre.SEDOUKA,
                                            )

                                        Genre.TANKA ->
                                            HomeAction.GetBeforeNewPosts(
                                                updatedAt = homeState.tankas.last().updateAt,
                                                genre = Genre.TANKA,
                                            )

                                        Genre.HAIKU ->
                                            HomeAction.GetBeforeNewPosts(
                                                updatedAt = homeState.haikus.last().updateAt,
                                                genre = Genre.HAIKU,
                                            )

                                        else ->
                                            HomeAction.GetBeforeNewPosts(
                                                updatedAt = homeState.posts.last().updateAt,
                                                genre = Genre.NORMAL,
                                            )
                                    },
                                )
                            }
                        },
                    )
                }
            }
        }
    }
}
