package com.example.feature.components.bar.top_search_bar

import com.example.feature.model.UiStatus

data class TopSearchBarState(
    val uiStatus: UiStatus = UiStatus.Initial,
    val keyword: String = "",
    val isCompletedLoadingUsers: Boolean = true,
    val isCompletedLoadingPosts: Boolean = true,
    val isCompletedLoadingPostsSortedByViewCount: Boolean = true,
)
