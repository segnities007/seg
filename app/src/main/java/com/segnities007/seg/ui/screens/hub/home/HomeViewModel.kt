package com.segnities007.seg.ui.screens.hub.home

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.domain.repository.PostRepository
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
    val onProcessOfEngagementAction: (newPost: Post) -> Unit,
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
                onProcessOfEngagementAction = this::onProcessOfEngagementAction,
            )

        private fun onProcessOfEngagementAction(newPost: Post) {
            onUpdatePosts(newPost)
        }

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
    }
