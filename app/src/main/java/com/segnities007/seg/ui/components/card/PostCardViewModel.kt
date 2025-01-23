package com.segnities007.seg.ui.components.card

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.R
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.navigation.hub.NavigationHubRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
data class PostCardUiState(
    val posts: List<Post> = listOf(),
    val post: Post = Post(), // for comment
)

@Immutable
data class PostCardUiAction(
    val onGetNewPosts: () -> Unit,
    val onGetPosts: (userID: String) -> Unit,
    val onGetPost: (postID: Int) -> Unit,
    val onGetBeforePosts: (afterPostCreateAt: java.time.LocalDateTime) -> Unit,
    val onUpdatePost: (post: Post) -> Unit,
    val onClickIcon: (onHubNavigate: (Route) -> Unit) -> Unit,
    val onClickPostCard: (onHubNavigate: (Route) -> Unit) -> Unit,
    val onIncrementViewCount: (post: Post) -> Unit,
)

@Immutable
data class EngagementIconAction(
    val onLike: (post: Post, myself: User, onGetUser: () -> Unit) -> Unit,
    val onRepost: (post: Post, myself: User, onGetUser: () -> Unit) -> Unit,
    val onComment: (post: Post, comment: Post, myself: User, onGetUser: () -> Unit) -> Unit,
)

@Immutable
data class EngagementIconState(
    val pushIcons: List<Int> =
        listOf(
            R.drawable.baseline_favorite_24,
            R.drawable.baseline_repeat_24,
            R.drawable.baseline_chat_bubble_24,
            R.drawable.baseline_bar_chart_24,
        ),
    val unPushIcons: List<Int> =
        listOf(
            R.drawable.baseline_favorite_border_24,
            R.drawable.baseline_repeat_24,
            R.drawable.baseline_chat_bubble_outline_24,
            R.drawable.baseline_bar_chart_24,
        ),
    val contentDescriptions: List<Int> =
        listOf(
            R.string.favorite,
            R.string.repost,
            R.string.comment,
            R.string.view_count,
        ),
)

@HiltViewModel
class PostCardViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
    ) : ViewModel() {
        val engagementIconState = EngagementIconState()

        var postCardUiState by mutableStateOf(PostCardUiState())
            private set

        fun onGetPostCardUiAction(): PostCardUiAction =
            PostCardUiAction(
                onGetNewPosts = this::onGetNewPosts,
                onGetPost = this::onGetPost,
                onGetPosts = this::onGetPosts,
                onGetBeforePosts = this::onGetBeforePosts,
                onClickIcon = this::onClickIcon,
                onClickPostCard = this::onClickPostCard,
                onUpdatePost = this::onUpdatePost,
                onIncrementViewCount = this::onIncrementViewCount,
            )

        fun onGetEngagementIconAction(): EngagementIconAction =
            EngagementIconAction(
                onLike = this::onLike,
                onRepost = this::onRepost,
                onComment = this::onComment,
            )

        private fun onUpdatePost(post: Post) {
            viewModelScope.launch(Dispatchers.IO) {
                postCardUiState = postCardUiState.copy(post = post)
            }
        }

        private fun onGetPost(postID: Int) {
            viewModelScope.launch(Dispatchers.IO) {
                val post = postRepository.getPost(postID)
                postCardUiState = postCardUiState.copy(post = post)
            }
        }

        private fun onGetNewPosts() {
            viewModelScope.launch(Dispatchers.IO) {
                val posts = postRepository.getNewPosts()
                postCardUiState = postCardUiState.copy(posts = posts)
            }
        }

        private fun onGetPosts(userID: String) {
            viewModelScope.launch(Dispatchers.IO) {
                val posts = postRepository.getUserPosts(userID)
                postCardUiState = postCardUiState.copy(posts = posts)
            }
        }

        private fun onGetBeforePosts(afterPostCreateAt: java.time.LocalDateTime) {
            viewModelScope.launch(Dispatchers.IO) {
                val posts = postRepository.getBeforePosts(afterPostCreateAt)
                postCardUiState = postCardUiState.copy(posts = postCardUiState.posts.plus(posts))
            }
        }

        private fun onUpdatePosts(newPost: Post) {
            val newPosts =
                postCardUiState.posts.map { post ->
                    if (newPost.id == post.id) newPost else post
                }

            postCardUiState = postCardUiState.copy(posts = newPosts)
        }

        private fun onClickIcon(onHubNavigate: (Route) -> Unit) {
            onHubNavigate(NavigationHubRoute.Account())
        }

        private fun onClickPostCard(onHubNavigate: (Route) -> Unit) {
            onHubNavigate(NavigationHubRoute.Comment())
        }

        private fun onIncrementViewCount(post: Post) {
            viewModelScope.launch(Dispatchers.IO) {
                postRepository.onIncrementView(post)
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
            onUpdatePosts(newPost)
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
            onUpdatePosts(newPost)
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
    }
