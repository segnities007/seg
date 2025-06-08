package com.example.feature.screens.hub.trend

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.domain.model.post.Post
import com.example.domain.repository.PostRepository
import com.example.feature.navigation.TopLayerViewModel
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

        var trendFlagState by mutableStateOf(TrendFlagState())
            private set

        fun onTrendAction(action: TrendAction) {
            when (action) {
                TrendAction.Dispose -> trendFlagState = TrendFlagState()
                TrendAction.Init -> init()
                TrendAction.GetAdditionalTrendPostOfMonth ->
                    trendFlagState =
                        trendFlagState.copy(isGetMoreAboutTrendOfToday = !trendFlagState.isGetMoreAboutTrendOfToday)

                TrendAction.GetAdditionalTrendPostOfToday ->
                    trendFlagState =
                        trendFlagState.copy(isGetMoreAboutTrendOfWeek = !trendFlagState.isGetMoreAboutTrendOfWeek)

                TrendAction.GetAdditionalTrendPostOfWeek ->
                    trendFlagState =
                        trendFlagState.copy(isGetMoreAboutTrendOfMonth = !trendFlagState.isGetMoreAboutTrendOfMonth)

                TrendAction.GetAdditionalTrendPostOfYear ->
                    trendFlagState =
                        trendFlagState.copy(isGetMoreAboutTrendOfYear = !trendFlagState.isGetMoreAboutTrendOfYear)

                is TrendAction.GetTrendPostOfMonth -> getTrendPostOfMonth(action)
                is TrendAction.GetTrendPostOfToday -> getTrendPostOfToday(action)
                is TrendAction.GetTrendPostOfWeek -> getTrendPostOfWeek(action)
                is TrendAction.GetTrendPostOfYear -> getTrendPostOfYear(action)
                is TrendAction.ProcessOfEngagement -> onUpdateTrendLists(action.newPost)
            }
        }

        private fun getTrendPostOfMonth(action: TrendAction.GetTrendPostOfMonth) {
            viewModelScope.launch(Dispatchers.IO) {
                val trendPostsOfMonth =
                    postRepository.onGetTrendPostOfMonth(limit = action.limit)
                trendState = trendState.copy(trendPostsOfMonth = trendPostsOfMonth)
            }
        }

        private fun getTrendPostOfToday(action: TrendAction.GetTrendPostOfToday) {
            viewModelScope.launch(Dispatchers.IO) {
                val trendPostsOfToday =
                    postRepository.onGetTrendPostOfToday(limit = action.limit)
                trendState = trendState.copy(trendPostsOfToday = trendPostsOfToday)
            }
        }

        private fun getTrendPostOfWeek(action: TrendAction.GetTrendPostOfWeek) {
            viewModelScope.launch(Dispatchers.IO) {
                val trendPostsOfWeek = postRepository.onGetTrendPostOfWeek(limit = action.limit)
                trendState = trendState.copy(trendPostsOfWeek = trendPostsOfWeek)
            }
        }

        private fun getTrendPostOfYear(action: TrendAction.GetTrendPostOfYear) {
            viewModelScope.launch(Dispatchers.IO) {
                val trendPostsOfYear = postRepository.onGetTrendPostOfYear(limit = action.limit)
                trendState = trendState.copy(trendPostsOfYear = trendPostsOfYear)
            }
        }

        private fun init() {
            val limit = 3L
            viewModelScope.launch(Dispatchers.IO) {
                val trendPostOfToday = postRepository.onGetTrendPostOfToday(limit = limit)
                val trendPostOfWeek = postRepository.onGetTrendPostOfWeek(limit = limit)
                val trendPostOfMonth = postRepository.onGetTrendPostOfMonth(limit = limit)
                val trendPostOfYear = postRepository.onGetTrendPostOfYear(limit = limit)
                trendState =
                    trendState.copy(
                        trendPostsOfToday = trendPostOfToday,
                        trendPostsOfWeek = trendPostOfWeek,
                        trendPostsOfMonth = trendPostOfMonth,
                        trendPostsOfYear = trendPostOfYear,
                    )
            }
        }

        private fun onUpdateTrendLists(newTrend: Post) {
            var trendLists =
                trendState.trendPostsOfToday.map { trend ->
                    if (newTrend.id == trend.id) newTrend else trend
                }

            trendState = trendState.copy(trendPostsOfToday = trendLists)

            trendLists =
                trendState.trendPostsOfWeek.map { trend ->
                    if (newTrend.id == trend.id) newTrend else trend
                }

            trendState = trendState.copy(trendPostsOfWeek = trendLists)

            trendLists =
                trendState.trendPostsOfMonth.map { trend ->
                    if (newTrend.id == trend.id) newTrend else trend
                }

            trendState = trendState.copy(trendPostsOfMonth = trendLists)

            trendLists =
                trendState.trendPostsOfYear.map { trend ->
                    if (newTrend.id == trend.id) newTrend else trend
                }

            trendState = trendState.copy(trendPostsOfYear = trendLists)
        }
    }
