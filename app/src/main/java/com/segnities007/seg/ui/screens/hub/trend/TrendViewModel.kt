package com.segnities007.seg.ui.screens.hub.trend

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.R
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.presentation.TopLayerViewModel
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.ui.components.card.postcard.EngagementIconAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

//TODO フラグを利用してonlikeなどの処理を使い回すようにする

data class TrendUiState(
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

data class TrendUiAction(
    val onGetTrendPostOfToday: (limit: Long) -> Unit,
    val onGetTrendPostOfWeek: (limit: Long) -> Unit,
    val onGetTrendPostOfMonth: (limit: Long) -> Unit,
    val onGetTrendPostOfYear: (limit: Long) -> Unit,
    val onReadMoreAboutTrendOfToday: () -> Unit,
    val onReadMoreAboutTrendOfWeek: () -> Unit,
    val onReadMoreAboutTrendOfMonth: () -> Unit,
    val onReadMoreAboutTrendOfYear: () -> Unit,
    val onResetReadMore: () -> Unit,
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
                onReadMoreAboutTrendOfToday = this::onReadMoreAboutTrendOfToday,
                onReadMoreAboutTrendOfWeek = this::onReadMoreAboutTrendOfWeek,
                onReadMoreAboutTrendOfMonth = this::onReadMoreAboutTrendOfMonth,
                onReadMoreAboutTrendOfYear = this::onReadMoreAboutTrendOfYear,
                onGetTrendPostOfToday = this::onGetTrendPostOfToday,
                onGetTrendPostOfWeek = this::onGetTrendPostOfWeek,
                onGetTrendPostOfMonth = this::onGetTrendPostOfMonth,
                onGetTrendPostOfYear = this::onGetTrendPostOfYear,
                onResetReadMore = this::onResetReadMore,
            )

        fun onGetEngagementIconActionForToday(): EngagementIconAction =
            EngagementIconAction(
                onLike = this::onLikeForToday,
                onRepost = this::onRepostForToday,
                onComment = this::onComment,
            )

    fun onGetEngagementIconActionForWeek(): EngagementIconAction =
        EngagementIconAction(
            onLike = this::onLikeForWeek,
            onRepost = this::onRepostForWeek,
            onComment = this::onComment,
        )

    fun onGetEngagementIconActionForMonth(): EngagementIconAction =
        EngagementIconAction(
            onLike = this::onLikeForMonth,
            onRepost = this::onRepostForMonth,
            onComment = this::onComment,
        )

    fun onGetEngagementIconActionForYear(): EngagementIconAction =
        EngagementIconAction(
            onLike = this::onLikeForYear,
            onRepost = this::onRepostForYear,
            onComment = this::onComment,
        )

        private fun onResetReadMore() {
            trendUiState =
                trendUiState.copy(
                    isReadMoreAboutTrendOfToday = false,
                    isReadMoreAboutTrendOfWeek = false,
                    isReadMoreAboutTrendOfMonth = false,
                    isReadMoreAboutTrendOfYear = false,
                )
        }

        private fun onReadMoreAboutTrendOfToday() {
            trendUiState = trendUiState.copy(isReadMoreAboutTrendOfToday = !trendUiState.isReadMoreAboutTrendOfToday)
        }

        private fun onReadMoreAboutTrendOfWeek() {
            trendUiState = trendUiState.copy(isReadMoreAboutTrendOfWeek = !trendUiState.isReadMoreAboutTrendOfWeek)
        }

        private fun onReadMoreAboutTrendOfMonth() {
            trendUiState = trendUiState.copy(isReadMoreAboutTrendOfMonth = !trendUiState.isReadMoreAboutTrendOfMonth)
        }

        private fun onReadMoreAboutTrendOfYear() {
            trendUiState = trendUiState.copy(isReadMoreAboutTrendOfYear = !trendUiState.isReadMoreAboutTrendOfYear)
        }

        private fun onGetTrendPostOfToday(limit: Long) {
            viewModelScope.launch(Dispatchers.IO) {
                val trendOfToday = postRepository.onGetTrendPostOfToday(limit = limit)
                trendUiState = trendUiState.copy(trendOfToday = trendOfToday)
            }
        }

        private fun onGetTrendPostOfWeek(limit: Long) {
            viewModelScope.launch(Dispatchers.IO) {
                val trendOfWeek = postRepository.onGetTrendPostOfWeek(limit = limit)
                trendUiState = trendUiState.copy(trendOfWeek = trendOfWeek)
            }
        }

        private fun onGetTrendPostOfMonth(limit: Long) {
            viewModelScope.launch(Dispatchers.IO) {
                val trendOfMonth = postRepository.onGetTrendPostOfMonth(limit = limit)
                trendUiState = trendUiState.copy(trendOfMonth = trendOfMonth)
            }
        }

        private fun onGetTrendPostOfYear(limit: Long) {
            viewModelScope.launch(Dispatchers.IO) {
                val trendOfYear = postRepository.onGetTrendPostOfYear(limit = limit)
                trendUiState = trendUiState.copy(trendOfYear = trendOfYear)
            }
        }

        private fun onLikeForToday(
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
            onUpdateTrendsForToday(newPost)
            onGetUser()
        }

    private fun onLikeForWeek(
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
        onUpdateTrendsForWeek(newPost)
        onGetUser()
    }

    private fun onLikeForMonth(
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
        onUpdateTrendsForMonth(newPost)
        onGetUser()
    }

    private fun onLikeForYear(
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
        onUpdateTrendsForYear(newPost)
        onGetUser()
    }

        private fun onRepostForToday(
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
            onUpdateTrendsForToday(newPost)
            onGetUser()
        }

    private fun onRepostForWeek(
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
        onUpdateTrendsForWeek(newPost)
        onGetUser()
    }

    private fun onRepostForMonth(
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
        onUpdateTrendsForMonth(newPost)
        onGetUser()
    }

    private fun onRepostForYear(
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
        onUpdateTrendsForYear(newPost)
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

        private fun onUpdateTrendsForToday(newTrend: Post) {
            val trends =
                trendUiState.trendOfToday.map { trend ->
                    if (newTrend.id == trend.id) newTrend else trend
                }

            trendUiState = trendUiState.copy(trendOfToday = trends)
        }

    private fun onUpdateTrendsForWeek(newTrend: Post) {
        val trends =
            trendUiState.trendOfWeek.map { trend ->
                if (newTrend.id == trend.id) newTrend else trend
            }

        trendUiState = trendUiState.copy(trendOfWeek = trends)
    }

    private fun onUpdateTrendsForMonth(newTrend: Post) {
        val trends =
            trendUiState.trendOfMonth.map { trend ->
                if (newTrend.id == trend.id) newTrend else trend
            }

        trendUiState = trendUiState.copy(trendOfMonth = trends)
    }

    private fun onUpdateTrendsForYear(newTrend: Post) {
        val trends =
            trendUiState.trendOfYear.map { trend ->
                if (newTrend.id == trend.id) newTrend else trend
            }

        trendUiState = trendUiState.copy(trendOfYear = trends)
    }
    }
