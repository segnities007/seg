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

data class TrendUiState(
    val trends: List<Post> = listOf(),
    val isReadMoreAboutTrend: Boolean = false,
)

data class TrendUiAction(
    val onGetTrendPostInThisWeek: (limit: Long) -> Unit,
    val onReadMoreAboutTrend: () -> Unit,
)

@HiltViewModel
class TrendViewModel
@Inject
constructor(
    private val postRepository: PostRepository,
) : TopLayerViewModel() {

    var trendUiState by mutableStateOf(TrendUiState())
        private set

    fun onGetTrendUiAction(): TrendUiAction{
        return TrendUiAction(
            onReadMoreAboutTrend = this::onReadMoreAboutTrend,
            onGetTrendPostInThisWeek = this::onGetTrendPostInThisWeek
        )
    }

    private fun onReadMoreAboutTrend(){
        trendUiState = trendUiState.copy(isReadMoreAboutTrend = !trendUiState.isReadMoreAboutTrend)
    }

    private fun onGetTrendPostInThisWeek(limit: Long){
        viewModelScope.launch(Dispatchers.IO){
            val trends = postRepository.getTrendPostInThisWeek(limit = limit)
            trendUiState = trendUiState.copy(trends = trends)
        }
    }


}