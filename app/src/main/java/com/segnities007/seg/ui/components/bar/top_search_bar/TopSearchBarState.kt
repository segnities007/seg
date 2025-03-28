package com.segnities007.seg.ui.components.bar.top_search_bar

data class TopSearchBarState(
    val keyword: String = "",
    val isCompletedLoadingUsers: Boolean = true,
    val isCompletedLoadingPosts: Boolean = true,
    val isCompletedLoadingPostsSortedByViewCount: Boolean = true,
)
