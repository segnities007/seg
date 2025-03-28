package com.segnities007.seg.ui.screens.hub.search

import androidx.compose.runtime.Immutable
import com.segnities007.seg.data.model.Post
import java.time.LocalDateTime

@Immutable
sealed class SearchAction {
    data object ResetSearchState : SearchAction()

    data class Search(
        val keyword: String,
    ) : SearchAction() // onEnter

    data class GetUsersByKeyword(
        val keyword: String,
    ) : SearchAction()

    data class GetBeforeUsersByKeyword(
        val keyword: String,
        val afterPostCreatedAt: LocalDateTime,
    ) : SearchAction()

    data class GetPostsByKeyword(
        val keyword: String,
    ) : SearchAction()

    data class GetBeforePostsByKeyword(
        val keyword: String,
        val afterPostCreatedAt: LocalDateTime,
    ) : SearchAction()

    data class GetPostsByKeywordSortedByViewCount(
        val keyword: String,
    ) : SearchAction()

    data class GetBeforePostsByKeywordSortedByViewCount(
        val keyword: String,
        val viewCount: Int,
    ) : SearchAction()

    data class ProcessOfEngagementAction(
        val newPost: Post,
    ) : SearchAction()
}
