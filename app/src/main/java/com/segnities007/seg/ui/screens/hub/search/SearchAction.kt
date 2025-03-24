package com.segnities007.seg.ui.screens.hub.search

import com.segnities007.seg.data.model.Post
import java.time.LocalDateTime

data class SearchAction(
    val onEnter: (keyword: String) -> Unit,
    val onResetListsOfSearchUiState: () -> Unit,
    val onGetUsersByKeyword: (keyword: String) -> Unit,
    val onGetBeforeUsersByKeyword: (keyword: String, afterPostCreateAt: LocalDateTime) -> Unit,
    val onGetPostsByKeyword: (keyword: String) -> Unit,
    val onGetBeforePostsByKeyword: (keyword: String, afterPostCreateAt: LocalDateTime) -> Unit,
    val onGetPostsByKeywordSortedByViewCount: (keyword: String) -> Unit,
    val onGetBeforePostsByKeywordSortedByViewCount: (keyword: String, viewCount: Int) -> Unit,
    val onProcessOfEngagementAction: (newPost: Post) -> Unit,
)