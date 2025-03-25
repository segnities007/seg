package com.segnities007.seg.ui.screens.hub.trend

import com.segnities007.seg.data.model.Post

data class TrendAction(
    val onGetTrendPostOfToday: (limit: Long) -> Unit,
    val onGetTrendPostOfWeek: (limit: Long) -> Unit,
    val onGetTrendPostOfMonth: (limit: Long) -> Unit,
    val onGetTrendPostOfYear: (limit: Long) -> Unit,
    val onReadMoreAboutTrendOfToday: () -> Unit,
    val onReadMoreAboutTrendOfWeek: () -> Unit,
    val onReadMoreAboutTrendOfMonth: () -> Unit,
    val onReadMoreAboutTrendOfYear: () -> Unit,
    val onResetReadMore: () -> Unit,
    val onProcessOfEngagementAction: (newPost: Post) -> Unit,
)
