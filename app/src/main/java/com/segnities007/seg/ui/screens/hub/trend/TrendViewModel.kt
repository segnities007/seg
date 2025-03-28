package com.segnities007.seg.ui.screens.hub.trend

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.ui.navigation.TopLayerViewModel
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
        var trendListState by mutableStateOf(TrendListState())
            private set

        var trendFlagState by mutableStateOf(TrendFlagState())
            private set

        fun onTrendAction(action: TrendAction) {
            when (action) {
                TrendAction.GetAdditionalTrendPostOfMonth -> {
                    trendFlagState =
                        trendFlagState.copy(isGetMoreAboutTrendOfToday = !trendFlagState.isGetMoreAboutTrendOfToday)
                }

                TrendAction.GetAdditionalTrendPostOfToday -> {
                    trendFlagState =
                        trendFlagState.copy(isGetMoreAboutTrendOfWeek = !trendFlagState.isGetMoreAboutTrendOfWeek)
                }

                TrendAction.GetAdditionalTrendPostOfWeek -> {
                    trendFlagState =
                        trendFlagState.copy(isGetMoreAboutTrendOfMonth = !trendFlagState.isGetMoreAboutTrendOfMonth)
                }

                TrendAction.GetAdditionalTrendPostOfYear -> {
                    trendFlagState =
                        trendFlagState.copy(isGetMoreAboutTrendOfYear = !trendFlagState.isGetMoreAboutTrendOfYear)
                }

                is TrendAction.GetTrendPostOfMonth -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val trendPostsOfMonth =
                            postRepository.onGetTrendPostOfMonth(limit = action.limit)
                        trendListState = trendListState.copy(trendPostsOfMonth = trendPostsOfMonth)
                    }
                }

                is TrendAction.GetTrendPostOfToday -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val trendPostsOfToday =
                            postRepository.onGetTrendPostOfToday(limit = action.limit)
                        trendListState = trendListState.copy(trendPostsOfToday = trendPostsOfToday)
                    }
                }

                is TrendAction.GetTrendPostOfWeek -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val trendPostsOfWeek = postRepository.onGetTrendPostOfWeek(limit = action.limit)
                        trendListState = trendListState.copy(trendPostsOfWeek = trendPostsOfWeek)
                    }
                }

                is TrendAction.GetTrendPostOfYear -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val trendPostsOfYear = postRepository.onGetTrendPostOfYear(limit = action.limit)
                        trendListState = trendListState.copy(trendPostsOfYear = trendPostsOfYear)
                    }
                }

                is TrendAction.ProcessOfEngagement -> {
                    onUpdateTrendLists(action.newPost)
                }

                TrendAction.Dispose -> {
                    trendFlagState = TrendFlagState()
                }

                TrendAction.Init -> {
                    val limit = 3L
                    viewModelScope.launch(Dispatchers.IO) {
                        val trendPostOfToday = postRepository.onGetTrendPostOfToday(limit = limit)
                        val trendPostOfWeek = postRepository.onGetTrendPostOfWeek(limit = limit)
                        val trendPostOfMonth = postRepository.onGetTrendPostOfMonth(limit = limit)
                        val trendPostOfYear = postRepository.onGetTrendPostOfYear(limit = limit)
                        trendListState =
                            trendListState.copy(
                                trendPostsOfToday = trendPostOfToday,
                                trendPostsOfWeek = trendPostOfWeek,
                                trendPostsOfMonth = trendPostOfMonth,
                                trendPostsOfYear = trendPostOfYear,
                            )
                    }
                }
            }
        }

        private fun onUpdateTrendLists(newTrend: Post) {
            var trendLists =
                trendListState.trendPostsOfToday.map { trend ->
                    if (newTrend.id == trend.id) newTrend else trend
                }

            trendListState = trendListState.copy(trendPostsOfToday = trendLists)

            trendLists =
                trendListState.trendPostsOfWeek.map { trend ->
                    if (newTrend.id == trend.id) newTrend else trend
                }

            trendListState = trendListState.copy(trendPostsOfWeek = trendLists)

            trendLists =
                trendListState.trendPostsOfMonth.map { trend ->
                    if (newTrend.id == trend.id) newTrend else trend
                }

            trendListState = trendListState.copy(trendPostsOfMonth = trendLists)

            trendLists =
                trendListState.trendPostsOfYear.map { trend ->
                    if (newTrend.id == trend.id) newTrend else trend
                }

            trendListState = trendListState.copy(trendPostsOfYear = trendLists)
        }
    }
