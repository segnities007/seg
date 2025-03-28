package com.segnities007.seg.ui.screens.hub.home

import androidx.compose.runtime.Immutable
import com.segnities007.seg.data.model.Post

@Immutable
sealed class HomeAction{
    data object GetNewPosts: HomeAction()
    data object ChangeHasNoMorePost: HomeAction()
    data class GetBeforeNewPosts(val updatedAt: java.time.LocalDateTime): HomeAction()
    data class ProcessOfEngagement(val newPost: Post): HomeAction()
}

