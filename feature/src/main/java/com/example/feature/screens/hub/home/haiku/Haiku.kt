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
import com.example.domain.model.post.Post
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
    onProcessOfEngagementAction: (newPost: Post) -> Unit,
) {
    LazyColumn(
        state = homeState.lazyListStateOfHaiku,
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
            homeState.haikus.size,
            key = { index: Int -> homeState.haikus[index].id },
        ) { i ->
            HaikuCard(
                post = homeState.haikus[i],
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
            if (!homeState.isAllHaikusFetched && homeState.haikus.isNotEmpty()) {
                Column {
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                    LoadingUI(
                        onLoading = {
                            if (homeState.haikus.isNotEmpty()) {
                                onHomeAction(
                                    HomeAction.GetBeforeNewPosts(
                                        updatedAt = homeState.haikus.last().updateAt,
                                        genre = Genre.HAIKU,
                                    ),
                                )
                            }
                        },
                    )
                }
            }
        }
    }
}
