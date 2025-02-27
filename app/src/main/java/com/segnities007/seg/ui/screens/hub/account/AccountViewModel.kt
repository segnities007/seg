package com.segnities007.seg.ui.screens.hub.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AccountUiState(
    val user: User = User(),
    val posts: List<Post> = listOf(),
    val likedPosts: List<Post> = listOf(),
    val repostedPosts: List<Post> = listOf(),
)

data class AccountUiFlagState(
    val isCompletedFetchPosts: Boolean = false,
    val isLoading: Boolean = false,
)

data class AccountUiAction(
    val onReset: () -> Unit,
    val onInitAccountUiState: (userID: String) -> Unit,
    val onGetOtherUser: (userID: String) -> Unit,
    val onSetOtherUser: (user: User) -> Unit,
    val onGetUserPosts: (userID: String) -> Unit,
    val onToggleIsLoading: () -> Unit,
    val onToggleIsCompletedFetchPosts: () -> Unit,
    val onGetPosts: () -> Unit,
    val onFollow: (
        isFollow: Boolean,
        myself: User,
        other: User,
        onToggleIsLoading: () -> Unit,
        onGetMyself: () -> Unit) -> Unit,
    val onProcessOfEngagementAction: (newPost: Post) -> Unit,
)

@HiltViewModel
class AccountViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
        private val postRepository: PostRepository,
    ) : ViewModel() {
        var accountUiState by mutableStateOf(AccountUiState())
            private set

        var accountUiFlagState by mutableStateOf(AccountUiFlagState())
            private set

        fun getAccountUiAction(): AccountUiAction =
            AccountUiAction(
                onGetOtherUser = this::onGetOtherUser,
                onSetOtherUser = this::onSetOtherUsers,
                onGetUserPosts = this::onGetUserPosts,
                onGetPosts = this::onGetPosts,
                onFollow = this::onFollow,
                onReset = this::onReset,
                onInitAccountUiState = this::onInitAccountUiState,
                onToggleIsLoading = this::onToggleIsLoading,
                onToggleIsCompletedFetchPosts = this::onToggleIsCompletedFetchPosts,
                onProcessOfEngagementAction = this::onProcessOfEngagement,
            )

        private fun onInitAccountUiState(userID: String) {
            viewModelScope.launch(Dispatchers.IO) {
                val user = userRepository.onGetOtherUser(userID)
                accountUiState = accountUiState.copy(user = user, posts = listOf())

                val posts = postRepository.onGetPostsOfUser(userID)
                if (posts.isNotEmpty()) {
                    accountUiState = accountUiState.copy(posts = posts)
                } else {
                    onToggleIsCompletedFetchPosts()
                }
            }
        }

        private fun onGetPosts() {
            viewModelScope.launch(Dispatchers.IO) {
                if (accountUiState.posts.isEmpty()) {
                    val posts = postRepository.onGetPostsOfUser(accountUiState.user.userID)
                    if (posts.isEmpty()) {
                        onToggleIsCompletedFetchPosts()
                        return@launch
                    }
                    accountUiState = accountUiState.copy(posts = posts)
                } else {
                    onGetBeforePosts()
                }
            }
        }

        private fun onGetBeforePosts() {
            if (accountUiState.posts.isEmpty()) return
            viewModelScope.launch(Dispatchers.IO) {
                val posts = postRepository.onGetBeforePostsOfUser(accountUiState.user.userID, accountUiState.posts.last().updateAt)
                if (posts.isNotEmpty()) {
                    accountUiState = accountUiState.copy(posts = accountUiState.posts.plus(posts))
                } else {
                    onToggleIsCompletedFetchPosts()
                }
            }
        }

        private fun onProcessOfEngagement(newPost: Post) {
            onUpdatePosts(newPost)
            onGetOtherUser(accountUiState.user.userID)
        }

        private fun onUpdatePosts(newPost: Post) {
            val newPosts =
                accountUiState.posts.map { post ->
                    if (newPost.id == post.id) newPost else post
                }

            accountUiState = accountUiState.copy(posts = newPosts)
        }

        private fun onFollow(
            isFollow: Boolean,
            myself: User,
            other: User,
            onToggleIsLoading: () -> Unit,
            onGetMyself: () -> Unit,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                if(isFollow) userRepository.onUnFollowUser(myself, other) else userRepository.onFollowUser(myself, other)
                onGetMyself()
                onToggleIsLoading()
            }
        }

        private fun onSetOtherUsers(user: User) {
            viewModelScope.launch(Dispatchers.IO) {
                accountUiState = accountUiState.copy(user = user)
            }
        }

        private fun onGetOtherUser(userID: String) {
            viewModelScope.launch(Dispatchers.IO) {
                val user = userRepository.onGetOtherUser(userID)
                accountUiState = accountUiState.copy(user = user)
            }
        }

        private fun onGetUserPosts(userID: String) {
            viewModelScope.launch(Dispatchers.IO) {
                val posts = postRepository.onGetPostsOfUser(userID)
                if (posts.isNotEmpty()) {
                    accountUiState = accountUiState.copy(posts = posts)
                } else {
                    onToggleIsCompletedFetchPosts()
                }
            }
        }

        private fun onToggleIsCompletedFetchPosts() {
            accountUiFlagState = accountUiFlagState.copy(isCompletedFetchPosts = !accountUiFlagState.isCompletedFetchPosts)
        }

        private fun onToggleIsLoading() {
            accountUiFlagState = accountUiFlagState.copy(isLoading = !accountUiFlagState.isLoading)
        }

        private fun onReset() {
            accountUiFlagState = accountUiFlagState.copy(isCompletedFetchPosts = false)
            accountUiState = accountUiState.copy(posts = listOf())
        }
    }
