package com.segnities007.seg.ui.screens.hub.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.components.card.PostCard
import com.segnities007.seg.ui.components.card.PostCardUiAction
import com.segnities007.seg.ui.components.card.PostCardUiState
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState

@Composable
fun Home(
    modifier: Modifier,
    hubNavController: NavController,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    postCardUiState: PostCardUiState,
    postCardUiAction: PostCardUiAction,
){

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ){
        items(
            postCardUiState.posts.size,
            key = { index: Int ->  postCardUiState.posts[index].id},
        ){i ->
            PostCard(
                post = postCardUiState.posts[i],
                images = postCardUiState.imageLists[i],
                icon = postCardUiState.icons[i],
                myself = hubUiState.user,
                hubUiAction = hubUiAction,
                postCardUiAction = postCardUiAction,
                onCardClick = {/*TODO*/ },
                onInitializeAction = { post: Post ->
                    postCardUiAction.onIncrementViewCount(post)
                },
                onAvatarClick = { userID: String ->
                    hubUiAction.onGetUserID(userID)
                    hubUiAction.onChangeCurrentRouteName(NavigationHubRoute.Account.routeName)
                    hubNavController.navigate(NavigationHubRoute.Account)
                },

            )
        }
    }

}