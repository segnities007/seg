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
import java.time.LocalDateTime
import javax.inject.Inject

data class AccountUiState(
    val user: User = User(),
    val posts: List<Post> = listOf(),
    val likedPosts: List<Post> = listOf(),
    val repostedPosts: List<Post> = listOf(),
    val isNotCompleted: Boolean = true,
)

data class AccountUiAction(
    val onReset: () -> Unit,
    val onInitAccountUiState: (userID: String) -> Unit,
    val onGetOtherUser: (userID: String) -> Unit,
    val onSetOtherUser: (user: User) -> Unit,
    val onGetUserPosts: (userID: String) -> Unit,
    val onChangeIsNotCompletedOfAccount: () -> Unit,
    val onGetPosts: () -> Unit,
    val onFollow: (myself: User, other: User, onGetMyself: () -> Unit) -> Unit,
    val onUnFollow: (myself: User, other: User, onGetMyself: () -> Unit) -> Unit,
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

        fun getAccountUiAction(): AccountUiAction =
            AccountUiAction(
                onGetOtherUser = this::onGetOtherUser,
                onSetOtherUser = this::onSetOtherUsers,
                onGetUserPosts = this::onGetUserPosts,
                onGetPosts = this::onGetPosts,
                onFollow = this::onFollow,
                onUnFollow = this::onUnFollow,
                onReset = this::onReset,
                onInitAccountUiState = this::onInitAccountUiState,
                onChangeIsNotCompletedOfAccount = this::onChangeIsNotCompletedOfAccount,
                onProcessOfEngagementAction = this::onProcessOfEngagement,
            )

        private fun onInitAccountUiState(userID: String) {
            viewModelScope.launch(Dispatchers.IO) {
                val user = userRepository.getOtherUser(userID)
                accountUiState = accountUiState.copy(user = user, posts = listOf())

                val posts = postRepository.onGetPostsOfUser(userID)
                if (posts.isNotEmpty()) {
                    accountUiState = accountUiState.copy(posts = posts)
                } else {
                    onChangeIsNotCompletedOfAccount()
                }
            }
        }

        private fun onGetPosts(){
            viewModelScope.launch(Dispatchers.IO) {
                if (accountUiState.posts.isEmpty()) {
                    val posts = postRepository.onGetPostsOfUser(accountUiState.user.userID)
                    if(posts.isEmpty()){
                        onChangeIsNotCompletedOfAccount()
                        return@launch
                    }
                    accountUiState = accountUiState.copy(posts = posts)
                } else {
                    onGetBeforePosts()
                }
            }
        }

        private fun onGetBeforePosts() {
            if(accountUiState.posts.isEmpty()) return
            viewModelScope.launch(Dispatchers.IO) {
                val posts = postRepository.onGetBeforePostsOfUser(accountUiState.user.userID, accountUiState.posts.last().updateAt)
                if (posts.isNotEmpty()) {
                    accountUiState = accountUiState.copy(posts = accountUiState.posts.plus(posts))
                } else {
                    onChangeIsNotCompletedOfAccount()
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
            myself: User,
            other: User,
            onGetMyself: () -> Unit,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                userRepository.followUser(myself, other)
                onGetMyself()
            }
        }

        private fun onUnFollow(
            myself: User,
            other: User,
            onGetMyself: () -> Unit,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                userRepository.unFollowUser(myself, other)
                onGetMyself()
            }
        }

        private fun onSetOtherUsers(user: User) {
            viewModelScope.launch(Dispatchers.IO) {
                accountUiState = accountUiState.copy(user = user)
            }
        }

        private fun onGetOtherUser(userID: String) {
            viewModelScope.launch(Dispatchers.IO) {
                val user = userRepository.getOtherUser(userID)
                accountUiState = accountUiState.copy(user = user)
            }
        }

        private fun onGetUserPosts(userID: String) {
            viewModelScope.launch(Dispatchers.IO) {
                val posts = postRepository.onGetPostsOfUser(userID)
                if (posts.isNotEmpty()) {
                    accountUiState = accountUiState.copy(posts = posts)
                } else {
                    onChangeIsNotCompletedOfAccount()
                }
            }
        }

        private fun onChangeIsNotCompletedOfAccount() {
            accountUiState = accountUiState.copy(isNotCompleted = !accountUiState.isNotCompleted)
        }

        private fun onReset() {
            accountUiState = accountUiState.copy(isNotCompleted = true, posts = listOf())
        }
    }
