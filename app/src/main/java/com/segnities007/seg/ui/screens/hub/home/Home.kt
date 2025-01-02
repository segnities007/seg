package com.segnities007.seg.ui.screens.hub.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.repository.PostRepositoryImpl
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.ui.screens.hub.HomeUiAction
import com.segnities007.seg.ui.screens.hub.HomeUiState

@Composable
fun Home(
    modifier: Modifier,
    homeUiState: HomeUiState,
    homeUiAction: HomeUiAction,
){

    Column (
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        for (i in homeUiState.posts.indices.reversed()) {
            val post = homeUiState.posts[i]
            PostCard(
                onClick = {},
                onIconClick = {},
                post = post
            )
        }

    }
}