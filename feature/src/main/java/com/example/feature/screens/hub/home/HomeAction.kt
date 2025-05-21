package com.example.feature.screens.hub.home

import androidx.compose.runtime.Immutable
import com.example.domain.model.post.Genre
import com.example.domain.model.post.Post
import java.time.LocalDateTime

@Immutable
sealed interface HomeAction {
    data class GetNewPosts(
        val genre: Genre,
    ) : HomeAction

    data class ChangeIsAllPostsFetched(
        val genre: Genre,
    ) : HomeAction

    data class UpdateCurrentGenre(
        val newGenre: Genre,
    ) : HomeAction

    data class GetBeforeNewPosts(
        val updatedAt: LocalDateTime,
        val genre: Genre,
    ) : HomeAction

    data class ChangeEngagementOfPost(
        val newPost: Post,
    ) : HomeAction
}
