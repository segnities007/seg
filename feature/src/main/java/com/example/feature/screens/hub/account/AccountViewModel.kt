package com.example.feature.screens.hub.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                is AccountAction.GetOtherUser -> onGetOtherUser(action.userID)
                AccountAction.ToggleIsLoading,
                AccountAction.ResetState,
                is AccountAction.SetOtherUser,
                -> accountUiState = accountReducer(action, accountUiState)

                is AccountAction.ClickFollowButton -> clickFollowButton(action)
                AccountAction.GetPosts -> getPosts(action)
                is AccountAction.InitAccountState -> initAccountState(action)
                is AccountAction.GetUserPosts -> getUserPosts(action)
                is AccountAction.ProcessOfEngagementAction -> processOfEngagementAction(action)
            }
        }

        private fun clickFollowButton(action: AccountAction.ClickFollowButton) {
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
                accountUiState = accountReducer(action, accountUiState)
            }
        }

        private fun getPosts(action: AccountAction) {
            viewModelScope.launch(Dispatchers.IO) {
                if (accountUiState.posts.isEmpty()) {
                    val posts = postRepository.onGetPostsOfUser(accountUiState.user.userID)
                    if (posts.isEmpty()) {
                        accountUiState = accountReducer(action, accountUiState)
                        return@launch
                    }
                    accountUiState = accountUiState.copy(posts = posts)
                } else {
                    onGetBeforePosts(action)
                }
            }
        }

        private fun initAccountState(action: AccountAction.InitAccountState) {
            viewModelScope.launch(Dispatchers.IO) {
                val user = userRepository.onGetOtherUser(action.userID)
                accountUiState = accountUiState.copy(user = user, posts = listOf())

                val posts = postRepository.onGetPostsOfUser(action.userID)
                accountUiState =
                    when {
                        posts.isNotEmpty() -> accountUiState.copy(posts = posts)
                        else -> accountReducer(action, accountUiState)
                    }
            }
        }

        private fun getUserPosts(action: AccountAction.GetUserPosts) {
            viewModelScope.launch(Dispatchers.IO) {
                val posts = postRepository.onGetPostsOfUser(action.userID)
                accountUiState =
                    when {
                        posts.isNotEmpty() -> accountUiState.copy(posts = posts)
                        else -> accountReducer(action, accountUiState)
                    }
            }
        }

        private fun processOfEngagementAction(action: AccountAction) {
            accountUiState = accountReducer(action, accountUiState)
            onGetOtherUser(accountUiState.user.userID)
        }

        private fun onGetOtherUser(userID: String) {
            viewModelScope.launch(Dispatchers.IO) {
                val user = userRepository.onGetOtherUser(userID)
                accountUiState = accountUiState.copy(user = user)
            }
        }

        private fun onGetBeforePosts(action: AccountAction) {
            if (accountUiState.posts.isEmpty()) return
            viewModelScope.launch(Dispatchers.IO) {
                val posts =
                    postRepository.onGetBeforePostsOfUser(
                        accountUiState.user.userID,
                        accountUiState.posts.last().updateAt,
                    )
                accountUiState =
                    when {
                        posts.isNotEmpty() ->
                            accountUiState.copy(
                                posts =
                                    accountUiState.posts.plus(
                                        posts,
                                    ),
                            )

                        else -> accountReducer(action, accountUiState)
                    }
            }
        }
    }
