package com.example.feature.screens.hub.trend

import com.example.domain.model.post.Post
import com.example.feature.model.UiStatus

data class TrendState(
    val uiStatus: UiStatus = UiStatus.Initial,
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
