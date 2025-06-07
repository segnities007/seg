package com.example.feature.screens.hub.trend

import androidx.compose.runtime.Immutable
import com.example.domain.model.post.Post

@Immutable
sealed interface TrendAction {
    data object GetAdditionalTrendPostOfToday : TrendAction

    data object GetAdditionalTrendPostOfWeek : TrendAction

    data object GetAdditionalTrendPostOfMonth : TrendAction

    data object GetAdditionalTrendPostOfYear : TrendAction

    data class GetTrendPostOfToday(
        val limit: Long,
    ) : TrendAction

    data class GetTrendPostOfWeek(
        val limit: Long,
    ) : TrendAction

    data class GetTrendPostOfMonth(
        val limit: Long,
    ) : TrendAction

    data class GetTrendPostOfYear(
        val limit: Long,
    ) : TrendAction

    data class ProcessOfEngagement(
        val newPost: Post,
    ) : TrendAction

    data object Init : TrendAction

    data object Dispose : TrendAction
}
