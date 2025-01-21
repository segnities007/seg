package com.segnities007.seg.ui.components.card

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.navigation.hub.NavigationHubRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PostCardUiState(
    val posts: List<Post> = listOf(),
    val post: Post = Post(), // for comment
)

data class PostCardUiAction(
    val onGetNewPosts: () -> Unit,
    val onGetPost: (postID: Int) -> Unit,
    val onGetBeforePosts: (afterPostCreateAt: java.time.LocalDateTime) -> Unit,
    val onUpdatePost: (post: Post) -> Unit,
    val onClickIcon: (onHubNavigate: (Route) -> Unit) -> Unit,
    val onClickPostCard: (onHubNavigate: (Route) -> Unit) -> Unit,
    val onLike: (post: Post, myself: User, onGetUser: () -> Unit) -> Unit,
    val onRepost: (post: Post, myself: User, onGetUser: () -> Unit) -> Unit,
    val onComment: (post: Post, comment: Post, myself: User, onGetUser: () -> Unit) -> Unit,
    val onIncrementViewCount: (post: Post) -> Unit,
)

@HiltViewModel
class PostCardViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
    ) : ViewModel() {
        var postCardUiState by mutableStateOf(PostCardUiState())
            private set

        fun onGetPostCardUiAction(): PostCardUiAction =
            PostCardUiAction(
                onGetNewPosts = this::onGetNewPosts,
                onGetPost = this::onGetPost,
                onGetBeforePosts = this::onGetBeforePosts,
                onClickIcon = this::onClickIcon,
                onClickPostCard = this::onClickPostCard,
                onLike = this::onLike,
                onRepost = this::onRepost,
                onComment = this::onComment,
                onUpdatePost = this::onUpdatePost,
                onIncrementViewCount = this::onIncrementViewCount,
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
