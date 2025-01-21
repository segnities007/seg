package com.segnities007.seg.ui.screens.hub.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.components.card.PostCard
import com.segnities007.seg.ui.components.card.PostCardUiAction
import com.segnities007.seg.ui.components.card.PostCardUiState
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState
import com.segnities007.seg.R

@Composable
fun Home(
    modifier: Modifier,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    postCardUiState: PostCardUiState,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Route) -> Unit,
) {

    LazyColumn(
        modifier = modifier.fillMaxSize().padding(top = dimensionResource(R.dimen.padding_smaller)),
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
                postCardUiAction = postCardUiAction,
            )
        }
        //action for fetching before post
        item{
            Column {
                Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
                LoadingUI(
                    onLoading = {
                        if(postCardUiState.posts.isNotEmpty()){
                            Log.d("Home", "onLoading")
                            postCardUiAction.onGetBeforePosts(postCardUiState.posts.last().updateAt)
                        }
                    }
                )
            }
        }
    }

}

@Composable
private fun LoadingUI(
    modifier: Modifier = Modifier,
    onLoading: () -> Unit,
){
    LaunchedEffect(Unit) {
        onLoading()
    }
    CircularProgressIndicator(modifier = modifier.width(32.dp))
}