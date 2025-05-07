package com.example.feature.screens.hub.home

import androidx.compose.runtime.Immutable
import com.example.domain.model.post.Post
import java.time.LocalDateTime

@Immutable
sealed class HomeAction {
    data object GetNewPosts : HomeAction()

    data object ChangeHasNoMorePost : HomeAction()

    data class GetBeforeNewPosts(
        val updatedAt: LocalDateTime,
    ) : HomeAction()

    data class ProcessOfEngagement(
        val newPost: Post,
    ) : HomeAction()
}
