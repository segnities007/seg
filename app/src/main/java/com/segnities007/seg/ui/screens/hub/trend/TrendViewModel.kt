package com.segnities007.seg.ui.screens.hub.trend

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.presentation.TopLayerViewModel
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.ui.components.card.EngagementIconAction
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

        fun onGetTrendUiAction(): TrendUiAction =
            TrendUiAction(
                onReadMoreAboutTrend = this::onReadMoreAboutTrend,
                onGetTrendPostInThisWeek = this::onGetTrendPostInThisWeek,
            )

        fun onGetEngagementIconAction(): EngagementIconAction =
            EngagementIconAction(
                onLike = this::onLike,
                onRepost = this::onRepost,
                onComment = this::onComment,
            )

        private fun onReadMoreAboutTrend() {
            trendUiState = trendUiState.copy(isReadMoreAboutTrend = !trendUiState.isReadMoreAboutTrend)
        }

        private fun onGetTrendPostInThisWeek(limit: Long) {
            viewModelScope.launch(Dispatchers.IO) {
                val trends = postRepository.getTrendPostInThisWeek(limit = limit)
                trendUiState = trendUiState.copy(trends = trends)
            }
        }

        private fun onLike(
            post: Post,
            myself: User,
            onGetUser: () -> Unit,
        ) {
            val newPost: Post
            if (!myself.likes.contains(post.id)) {
                newPost = post.copy(likeCount = post.likeCount + 1)
                viewModelScope.launch(Dispatchers.IO) {
                    postRepository.onLike(post = newPost, user = myself)
                }
            } else {
                newPost = post.copy(likeCount = post.likeCount - 1)
                viewModelScope.launch(Dispatchers.IO) {
                    postRepository.onUnLike(post = newPost, user = myself)
                }
            }
            onUpdateTrends(newPost)
            onGetUser()
        }

        private fun onRepost(
            post: Post,
            myself: User,
            onGetUser: () -> Unit,
        ) {
            val newPost: Post
            if (!myself.reposts.contains(post.id)) {
                newPost = post.copy(repostCount = post.repostCount + 1)
                viewModelScope.launch(Dispatchers.IO) {
                    postRepository.onRepost(post = newPost, user = myself)
                }
            } else {
                newPost = post.copy(repostCount = post.repostCount - 1)
                viewModelScope.launch(Dispatchers.IO) {
                    postRepository.onUnRepost(post = newPost, user = myself)
                }
            }
            onUpdateTrends(newPost)
            onGetUser()
        }

        private fun onComment(
            post: Post,
            comment: Post,
            myself: User,
            updateMyself: () -> Unit,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                // TODO
            }
        }

        private fun onUpdateTrends(newTrend: Post) {
            val trends =
                trendUiState.trends.map { trend ->
                    if (newTrend.id == trend.id) newTrend else trend
                }

            trendUiState = trendUiState.copy(trends = trends)
        }
    }
