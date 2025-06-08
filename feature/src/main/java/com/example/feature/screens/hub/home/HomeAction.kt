package com.example.feature.screens.hub.home

import androidx.compose.runtime.Immutable
import com.example.domain.model.post.Genre
import com.example.domain.model.post.Post
import com.example.feature.screens.hub.HubAction
import java.time.LocalDateTime

@Immutable
interface HomeAction {
    data class GetNewPosts(
        val genre: Genre,
        val onHubAction: (HubAction) -> Unit,
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
        val onHubAction: (HubAction) -> Unit,
    ) : HomeAction

    data class ChangeEngagementOfPost(
        val newPost: Post,
    ) : HomeAction
}
