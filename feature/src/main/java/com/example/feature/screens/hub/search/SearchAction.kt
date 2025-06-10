package com.example.feature.screens.hub.search

import androidx.compose.runtime.Immutable
import com.example.domain.model.post.Post
import com.example.feature.screens.hub.HubAction
import java.time.LocalDateTime

@Immutable
sealed interface SearchAction {
    data object ResetSearchState : SearchAction

    data class Search(
        val keyword: String,
        val onHubAction: (HubAction) -> Unit,
    ) : SearchAction // onEnter

    data class GetUsersByKeyword(
        val keyword: String,
        val onHubAction: (HubAction) -> Unit,
    ) : SearchAction

    data class GetBeforeUsersByKeyword(
        val keyword: String,
        val afterPostCreatedAt: LocalDateTime,
        val onHubAction: (HubAction) -> Unit,
    ) : SearchAction

    data class GetPostsByKeyword(
        val keyword: String,
        val onHubAction: (HubAction) -> Unit,
    ) : SearchAction

    data class GetBeforePostsByKeyword(
        val keyword: String,
        val afterPostCreatedAt: LocalDateTime,
        val onHubAction: (HubAction) -> Unit,
    ) : SearchAction

    data class GetPostsByKeywordSortedByViewCount(
        val keyword: String,
        val onHubAction: (HubAction) -> Unit,
    ) : SearchAction

    data class GetBeforePostsByKeywordSortedByViewCount(
        val keyword: String,
        val viewCount: Int,
        val onHubAction: (HubAction) -> Unit,
    ) : SearchAction

    data class ProcessOfEngagementAction(
        val newPost: Post,
    ) : SearchAction
}
