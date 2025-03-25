package com.segnities007.seg.ui.screens.hub.trend

import com.segnities007.seg.R
import com.segnities007.seg.data.model.Post

data class TrendState(
    val trendOfToday: List<Post> = listOf(),
    val trendOfWeek: List<Post> = listOf(),
    val trendOfMonth: List<Post> = listOf(),
    val trendOfYear: List<Post> = listOf(),
    val isReadMoreAboutTrendOfToday: Boolean = false,
    val isReadMoreAboutTrendOfWeek: Boolean = false,
    val isReadMoreAboutTrendOfMonth: Boolean = false,
    val isReadMoreAboutTrendOfYear: Boolean = false,
    val textIDs: List<Int> =
        listOf(
            R.string.todays_most_view_post,
            R.string.weeks_most_view_post,
            R.string.months_most_view_post,
            R.string.years_most_view_post,
        ),
)
