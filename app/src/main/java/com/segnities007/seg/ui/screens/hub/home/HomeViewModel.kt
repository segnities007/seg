package com.segnities007.seg.ui.screens.hub.home

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.ui.components.card.postcard.EngagementIconAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
data class HomeUiState(
    val posts: List<Post> = listOf(),
    val hasNoMorePost: Boolean = false,
)

@Immutable
data class HomeUiAction(
    val onGetNewPosts: () -> Unit,
    val onGetBeforeNewPosts: (updatedAt: java.time.LocalDateTime) -> Unit,
    val onChangeHasNoMorePost: () -> Unit,
)

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
    ) : ViewModel() {
        var homeUiState by mutableStateOf(HomeUiState())
            private set

        fun onGetHomeUiAction(): HomeUiAction =
            HomeUiAction(
                onGetNewPosts = this::onGetNewPosts,
                onChangeHasNoMorePost = this::onChangeHasNoMorePost,
                onGetBeforeNewPosts = this::onGetBeforeNewPosts,
            )

        fun onGetEngagementUiAction(): EngagementIconAction =
            EngagementIconAction(
                onLike = this::onLike,
                onRepost = this::onRepost,
                onComment = this::onComment,
            )

        private fun onGetNewPosts() {
            viewModelScope.launch(Dispatchers.IO) {
                val posts = postRepository.onGetNewPosts()
                homeUiState = homeUiState.copy(posts = posts)
            }
        }

        private fun onGetBeforeNewPosts(updatedAt: java.time.LocalDateTime) {
            viewModelScope.launch(Dispatchers.IO) {
                val posts = postRepository.onGetBeforePosts(updatedAt)
                if (posts.isEmpty()) onChangeHasNoMorePost()
                homeUiState = homeUiState.copy(posts = homeUiState.posts.plus(posts))
            }
        }

        private fun onChangeHasNoMorePost() {
            homeUiState = homeUiState.copy(hasNoMorePost = !homeUiState.hasNoMorePost)
        }

        private fun onUpdatePosts(newPost: Post) {
            val newPosts =
                homeUiState.posts.map { post ->
                    if (newPost.id == post.id) newPost else post
                }

            homeUiState = homeUiState.copy(posts = newPosts)
        }

        private fun onLike(
            post: Post,
            myself: User,
            onAddOrRemoveFromMyList: () -> Unit,
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
            onAddOrRemoveFromMyList()
        }

        private fun onRepost(
            post: Post,
            myself: User,
            onAddOrRemoveFromMyList: () -> Unit,
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
            onAddOrRemoveFromMyList()
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
