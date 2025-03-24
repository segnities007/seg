package com.segnities007.seg.ui.screens.hub.trend

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.domain.presentation.TopLayerViewModel
import com.segnities007.seg.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
    ) : TopLayerViewModel() {
        var trendState by mutableStateOf(TrendState())
            private set

        fun onGetTrendUiAction(): TrendAction =
            TrendAction(
                onReadMoreAboutTrendOfToday = this::onReadMoreAboutTrendOfToday,
                onReadMoreAboutTrendOfWeek = this::onReadMoreAboutTrendOfWeek,
                onReadMoreAboutTrendOfMonth = this::onReadMoreAboutTrendOfMonth,
                onReadMoreAboutTrendOfYear = this::onReadMoreAboutTrendOfYear,
                onGetTrendPostOfToday = this::onGetTrendPostOfToday,
                onGetTrendPostOfWeek = this::onGetTrendPostOfWeek,
                onGetTrendPostOfMonth = this::onGetTrendPostOfMonth,
                onGetTrendPostOfYear = this::onGetTrendPostOfYear,
                onResetReadMore = this::onResetReadMore,
                onProcessOfEngagementAction = this::onProcessOfEngagementAction,
            )

        private fun onProcessOfEngagementAction(newTrend: Post) {
            onUpdateTrends(newTrend)
        }

        private fun onResetReadMore() {
            trendState =
                trendState.copy(
                    isReadMoreAboutTrendOfToday = false,
                    isReadMoreAboutTrendOfWeek = false,
                    isReadMoreAboutTrendOfMonth = false,
                    isReadMoreAboutTrendOfYear = false,
                )
        }

        private fun onReadMoreAboutTrendOfToday() {
            trendState = trendState.copy(isReadMoreAboutTrendOfToday = !trendState.isReadMoreAboutTrendOfToday)
        }

        private fun onReadMoreAboutTrendOfWeek() {
            trendState = trendState.copy(isReadMoreAboutTrendOfWeek = !trendState.isReadMoreAboutTrendOfWeek)
        }

        private fun onReadMoreAboutTrendOfMonth() {
            trendState = trendState.copy(isReadMoreAboutTrendOfMonth = !trendState.isReadMoreAboutTrendOfMonth)
        }

        private fun onReadMoreAboutTrendOfYear() {
            trendState = trendState.copy(isReadMoreAboutTrendOfYear = !trendState.isReadMoreAboutTrendOfYear)
        }

        private fun onGetTrendPostOfToday(limit: Long) {
            viewModelScope.launch(Dispatchers.IO) {
                val trendOfToday = postRepository.onGetTrendPostOfToday(limit = limit)
                trendState = trendState.copy(trendOfToday = trendOfToday)
            }
        }

        private fun onGetTrendPostOfWeek(limit: Long) {
            viewModelScope.launch(Dispatchers.IO) {
                val trendOfWeek = postRepository.onGetTrendPostOfWeek(limit = limit)
                trendState = trendState.copy(trendOfWeek = trendOfWeek)
            }
        }

        private fun onGetTrendPostOfMonth(limit: Long) {
            viewModelScope.launch(Dispatchers.IO) {
                val trendOfMonth = postRepository.onGetTrendPostOfMonth(limit = limit)
                trendState = trendState.copy(trendOfMonth = trendOfMonth)
            }
        }

        private fun onGetTrendPostOfYear(limit: Long) {
            viewModelScope.launch(Dispatchers.IO) {
                val trendOfYear = postRepository.onGetTrendPostOfYear(limit = limit)
                trendState = trendState.copy(trendOfYear = trendOfYear)
            }
        }

        private fun onUpdateTrends(newTrend: Post) {
            var trends =
                trendState.trendOfToday.map { trend ->
                    if (newTrend.id == trend.id) newTrend else trend
                }

            trendState = trendState.copy(trendOfToday = trends)

            trends =
                trendState.trendOfWeek.map { trend ->
                    if (newTrend.id == trend.id) newTrend else trend
                }

            trendState = trendState.copy(trendOfWeek = trends)

            trends =
                trendState.trendOfMonth.map { trend ->
                    if (newTrend.id == trend.id) newTrend else trend
                }

            trendState = trendState.copy(trendOfMonth = trends)

            trends =
                trendState.trendOfYear.map { trend ->
                    if (newTrend.id == trend.id) newTrend else trend
                }

            trendState = trendState.copy(trendOfYear = trends)
        }
    }
