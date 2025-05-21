package com.example.feature.screens.hub.home

import androidx.compose.runtime.Immutable
import com.example.domain.model.post.Genre
import com.example.domain.model.post.Post
import java.time.LocalDateTime

@Immutable
sealed interface HomeAction {
    data object GetNewPosts : HomeAction

    data object GetNewHaikus : HomeAction

    data object GetNewTankas : HomeAction

    data object ChangeIsAllPostsFetched : HomeAction

    data object ChangeIsAllHaikusFetched : HomeAction

    data object ChangeIsAllTankasFetched : HomeAction

    data class UpdateCurrentGenre(
        val newGenre: Genre,
    ) : HomeAction

    data class GetBeforeNewPosts(
        val updatedAt: LocalDateTime,
    ) : HomeAction

    data class GetBeforeNewHaikus(
        val updatedAt: LocalDateTime,
    ) : HomeAction

    data class GetBeforeNewTankas(
        val updatedAt: LocalDateTime,
    ) : HomeAction

    data class ChangeEngagementOfPost(
        val newPost: Post,
    ) : HomeAction

    data class ChangeEngagementOfHaiku(
        val newHaiku: Post,
    ) : HomeAction

    data class ChangeEngagementOfTanka(
        val newTanka: Post,
    ) : HomeAction
}
