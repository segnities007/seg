package com.example.feature.screens.hub.home.haiku

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
import com.example.domain.presentation.navigation.Navigation
import com.example.feature.R
import com.example.feature.components.card.haiku.HaikuCard
import com.example.feature.components.card.postcard.PostCardAction
import com.example.feature.components.indicator.LoadingUI
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.HubState
import com.example.feature.screens.hub.home.HomeAction
import com.example.feature.screens.hub.home.HomeState

@Composable
fun Haiku(
    modifier: Modifier,
    hubState: HubState,
    homeState: HomeState,
    onHubAction: (HubAction) -> Unit,
    onHomeAction: (HomeAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
    onHubNavigate: (Navigation) -> Unit,
) {
    LazyColumn(
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
            homeState.posts.size,
            key = { index: Int -> homeState.posts[index].id },
        ) { i ->
            if (homeState.posts[i].genre == Genre.HAIKU) {
                HaikuCard(
                    post = homeState.posts[i],
                    hubState = hubState,
                    isIncrementView = true,
                    onHubNavigate = onHubNavigate,
                    onHubAction = onHubAction,
                    onPostCardAction = onPostCardAction,
                )
                Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smallest)))
            }
        }
        item {
            if (!homeState.hasNoMorePost && homeState.posts.isNotEmpty()) {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(
                        onLoading = {
                            if (homeState.posts.isNotEmpty()) {
                                onHomeAction(HomeAction.GetBeforeNewPosts(homeState.posts.last().updateAt))
                            }
                        },
                    )
                }
            }
        }
    }
}
