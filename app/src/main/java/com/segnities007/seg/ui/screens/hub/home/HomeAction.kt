package com.segnities007.seg.ui.screens.hub.home

import androidx.compose.runtime.Immutable
import com.segnities007.seg.data.model.Post

@Immutable
data class HomeAction(
    val onGetNewPosts: () -> Unit,
    val onGetBeforeNewPosts: (updatedAt: java.time.LocalDateTime) -> Unit,
    val onChangeHasNoMorePost: () -> Unit,
    val onProcessOfEngagementAction: (newPost: Post) -> Unit,
)