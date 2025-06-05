package com.example.feature.screens.hub.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.post.Post
import com.example.domain.repository.PostRepository
import com.example.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
        private val postRepository: PostRepository,
    ) : ViewModel() {
        var accountUiState by mutableStateOf(AccountState())
            private set

        fun onAccountAction(action: AccountAction) {
            when (action) {
                is AccountAction.ClickFollowButton -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        if (action.isFollow) {
                            userRepository.onUnFollowUser(
                                action.self,
                                action.other,
                            )
                        } else {
                            userRepository.onFollowUser(action.self, action.other)
                        }
                        action.getSelf()
                        accountReducer(action, accountUiState)
                    }
                }

                is AccountAction.GetOtherUser -> {
                    onGetOtherUser(action.userID)
                }

                AccountAction.GetPosts -> {
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

                is AccountAction.InitAccountState -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val user = userRepository.onGetOtherUser(action.userID)
                        accountUiState = accountUiState.copy(user = user, posts = listOf())

                        val posts = postRepository.onGetPostsOfUser(action.userID)
                        if (posts.isNotEmpty()) {
                            accountUiState = accountUiState.copy(posts = posts)
                        } else {
                            onToggleIsCompletedFetchPosts()
                        }
                    }
                }

                is AccountAction.GetUserPosts -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val posts = postRepository.onGetPostsOfUser(action.userID)
                        if (posts.isNotEmpty()) {
                            accountUiState = accountUiState.copy(posts = posts)
                        } else {
                            onToggleIsCompletedFetchPosts()
                        }
                    }
                }

                is AccountAction.ProcessOfEngagementAction -> {
                    onUpdatePosts(action.newPost)
                    onGetOtherUser(accountUiState.user.userID)
                }

                AccountAction.ToggleIsLoading,
                AccountAction.ResetState,
                is AccountAction.SetOtherUser,
                -> accountUiState = accountReducer(action, accountUiState)
            }
        }

        private fun onGetOtherUser(userID: String) {
            viewModelScope.launch(Dispatchers.IO) {
                val user = userRepository.onGetOtherUser(userID)
                accountUiState = accountUiState.copy(user = user)
            }
        }

        private fun onGetBeforePosts() {
            if (accountUiState.posts.isEmpty()) return
            viewModelScope.launch(Dispatchers.IO) {
                val posts =
                    postRepository.onGetBeforePostsOfUser(
                        accountUiState.user.userID,
                        accountUiState.posts.last().updateAt,
                    )
                if (posts.isNotEmpty()) {
                    accountUiState = accountUiState.copy(posts = accountUiState.posts.plus(posts))
                } else {
                    onToggleIsCompletedFetchPosts()
                }
            }
        }

        private fun onUpdatePosts(newPost: Post) {
            val newPosts =
                accountUiState.posts.map { post ->
                    if (newPost.id == post.id) newPost else post
                }

            accountUiState = accountUiState.copy(posts = newPosts)
        }

        private fun onToggleIsCompletedFetchPosts() {
            accountUiState =
                accountUiState.copy(isCompletedFetchPosts = !accountUiState.isCompletedFetchPosts)
        }
    }
