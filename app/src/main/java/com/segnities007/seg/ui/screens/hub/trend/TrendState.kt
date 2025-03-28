package com.segnities007.seg.ui.screens.hub.trend

import com.segnities007.seg.data.model.Post

data class TrendListState(
    val trendPostsOfToday: List<Post> = listOf(),
    val trendPostsOfWeek: List<Post> = listOf(),
    val trendPostsOfMonth: List<Post> = listOf(),
    val trendPostsOfYear: List<Post> = listOf(),
)

data class TrendFlagState(
    val isGetMoreAboutTrendOfToday: Boolean = false,
    val isGetMoreAboutTrendOfWeek: Boolean = false,
    val isGetMoreAboutTrendOfMonth: Boolean = false,
    val isGetMoreAboutTrendOfYear: Boolean = false,
)
