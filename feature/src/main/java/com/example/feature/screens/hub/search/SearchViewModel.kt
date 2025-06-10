package com.example.feature.screens.hub.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.PostRepository
import com.example.domain.repository.UserRepository
import com.example.feature.components.bar.top_search_bar.TopSearchBarAction
import com.example.feature.components.bar.top_search_bar.TopSearchBarState
import com.example.feature.model.UiStatus
import com.example.feature.screens.hub.HubAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
        private val userRepository: UserRepository,
    ) : ViewModel() {
        var topSearchBarUiState by mutableStateOf(TopSearchBarState())
            private set

        var searchState by mutableStateOf(SearchState())
            private set

        fun onTopSearchBarAction(action: TopSearchBarAction) {
            when (action) {
                is TopSearchBarAction.UpdateKeyword -> {
                    onUpdateKeyword(action.newKeyword)
                }
            }
        }

        fun onSearchAction(action: SearchAction) {
            when (action) {
                is SearchAction.GetBeforePostsByKeyword -> getBeforePostsByKeyword(action)
                is SearchAction.GetBeforeUsersByKeyword -> getBeforeUsersByKeyword(action)
                is SearchAction.GetPostsByKeyword -> getPostsByKeyword(action)
                is SearchAction.GetUsersByKeyword -> getUsersByKeyword(action)
                SearchAction.ResetSearchState -> resetSearchState(action)
                is SearchAction.Search -> search(action)
                is SearchAction.GetPostsByKeywordSortedByViewCount ->
                    getPostsByKeywordSortedByViewCount(
                        action,
                    )

                is SearchAction.GetBeforePostsByKeywordSortedByViewCount ->
                    getBeforePostsByKeywordSortedByViewCount(
                        action,
                    )

                is SearchAction.ProcessOfEngagementAction ->
                    searchState =
                        searchReducer(state = searchState, action = action)
            }
        }

        private fun getBeforePostsByKeyword(action: SearchAction.GetBeforePostsByKeyword) {
            searchState = searchState.copy(uiStatus = UiStatus.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val newPosts =
                        postRepository.onGetBeforePostsByKeyword(
                            action.keyword,
                            action.afterPostCreatedAt,
                        )
                    if (newPosts.isNotEmpty()) {
                        val posts = searchState.posts.plus(newPosts)
                        searchState = searchState.copy(posts = posts)
                        searchState = searchState.copy(posts = searchState.posts.plus(newPosts))
                    } else {
                        onBeFalseIsPosts()
                    }
                    searchState = searchState.copy(uiStatus = UiStatus.Success)
                } catch (e: Exception) {
                    searchState =
                        searchState.copy(uiStatus = UiStatus.Error("ポストの取得に失敗しました。"))
                    action.onHubAction(HubAction.OpenSnackBar((searchState.uiStatus as UiStatus.Error).message))
                } finally {
                    searchState = searchState.copy(uiStatus = UiStatus.Initial)
                }
            }
        }

        private fun getBeforePostsByKeywordSortedByViewCount(action: SearchAction.GetBeforePostsByKeywordSortedByViewCount) {
            searchState = searchState.copy(uiStatus = UiStatus.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val newPosts =
                        postRepository.onGetBeforePostsByKeywordSortedByViewCount(
                            action.keyword,
                            action.viewCount,
                        )
                    if (newPosts.isNotEmpty()) {
                        val postsSortedByViewCount =
                            searchState.postsSortedByViewCount.plus(newPosts)
                        searchState =
                            searchState.copy(postsSortedByViewCount = postsSortedByViewCount)
                    } else {
                        onBeFalseIsPostsSorted()
                    }
                    searchState = searchState.copy(uiStatus = UiStatus.Success)
                } catch (e: Exception) {
                    searchState =
                        searchState.copy(uiStatus = UiStatus.Error("ポストの取得に失敗しました。"))
                    action.onHubAction(HubAction.OpenSnackBar((searchState.uiStatus as UiStatus.Error).message))
                } finally {
                    searchState = searchState.copy(uiStatus = UiStatus.Initial)
                }
            }
        }

        private fun getBeforeUsersByKeyword(action: SearchAction.GetBeforeUsersByKeyword) {
            searchState = searchState.copy(uiStatus = UiStatus.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val newUsers =
                        userRepository.onGetBeforeUsersByKeyword(
                            action.keyword,
                            action.afterPostCreatedAt,
                        )

                    if (newUsers.isNotEmpty()) {
                        val users = searchState.users.plus(newUsers)
                        searchState = searchState.copy(users = users)
                        return@launch
                    } else {
                        onBeFalseIsUsers()
                    }
                } catch (e: Exception) {
                    searchState =
                        searchState.copy(uiStatus = UiStatus.Error("ユーザーの取得に失敗しました。"))
                    action.onHubAction(HubAction.OpenSnackBar((searchState.uiStatus as UiStatus.Error).message))
                } finally {
                    searchState = searchState.copy(uiStatus = UiStatus.Initial)
                }
            }
        }

        private fun getPostsByKeyword(action: SearchAction.GetPostsByKeyword) {
            searchState = searchState.copy(uiStatus = UiStatus.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    searchState =
                        searchState.copy(
                            posts = postRepository.onGetPostsByKeyword(action.keyword),
                            uiStatus = UiStatus.Success,
                        )
                } catch (e: Exception) {
                    searchState =
                        searchState.copy(uiStatus = UiStatus.Error("ポストの取得に失敗しました。"))
                    action.onHubAction(HubAction.OpenSnackBar((searchState.uiStatus as UiStatus.Error).message))
                } finally {
                    searchState = searchState.copy(uiStatus = UiStatus.Initial)
                }
            }
        }

        private fun getPostsByKeywordSortedByViewCount(action: SearchAction.GetPostsByKeywordSortedByViewCount) {
            searchState = searchState.copy(uiStatus = UiStatus.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    searchState =
                        searchState.copy(
                            postsSortedByViewCount =
                                postRepository.onGetPostsByKeywordSortedByViewCount(
                                    action.keyword,
                                ),
                            uiStatus = UiStatus.Success,
                        )
                } catch (e: Exception) {
                    searchState =
                        searchState.copy(uiStatus = UiStatus.Error("ポストの取得に失敗しました。"))
                    action.onHubAction(HubAction.OpenSnackBar((searchState.uiStatus as UiStatus.Error).message))
                } finally {
                    searchState = searchState.copy(uiStatus = UiStatus.Initial)
                }
            }
        }

        private fun getUsersByKeyword(action: SearchAction.GetUsersByKeyword) {
            searchState = searchState.copy(uiStatus = UiStatus.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    searchState =
                        searchState.copy(
                            users = userRepository.onGetUsersByKeyword(action.keyword),
                        )
                    searchState = searchState.copy(uiStatus = UiStatus.Success)
                } catch (e: Exception) {
                    searchState =
                        searchState.copy(uiStatus = UiStatus.Error("ユーザーの取得に失敗しました。"))
                    action.onHubAction(HubAction.OpenSnackBar((searchState.uiStatus as UiStatus.Error).message))
                } finally {
                    searchState = searchState.copy(uiStatus = UiStatus.Initial)
                }
            }
        }

        private fun resetSearchState(action: SearchAction) {
            searchState = searchReducer(searchState, action)
            onResetIs()
        }

        private fun search(action: SearchAction.Search) {
            searchState = searchState.copy(uiStatus = UiStatus.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val users = userRepository.onGetUsersByKeyword(action.keyword)
                    val posts = postRepository.onGetPostsByKeyword(action.keyword)
                    val postsSortedByViewCount =
                        postRepository.onGetPostsByKeywordSortedByViewCount(action.keyword)
                    onResetIs()
                    searchState =
                        searchState.copy(
                            users = users,
                            posts = posts,
                            postsSortedByViewCount = postsSortedByViewCount,
                        )
                } catch (e: Exception) {
                    searchState =
                        searchState.copy(uiStatus = UiStatus.Error("エラーが発生しました。"))
                    action.onHubAction(HubAction.OpenSnackBar((searchState.uiStatus as UiStatus.Error).message))
                } finally {
                    searchState = searchState.copy(uiStatus = UiStatus.Initial)
                }
            }
        }

        private fun onResetIs() {
            topSearchBarUiState =
                topSearchBarUiState.copy(
                    isCompletedLoadingUsers = true,
                    isCompletedLoadingPosts = true,
                    isCompletedLoadingPostsSortedByViewCount = true,
                )
        }

        private fun onBeFalseIsUsers() {
            topSearchBarUiState =
                topSearchBarUiState.copy(
                    isCompletedLoadingUsers = false,
                )
        }

        private fun onBeFalseIsPosts() {
            topSearchBarUiState =
                topSearchBarUiState.copy(
                    isCompletedLoadingPosts = false,
                )
        }

        private fun onBeFalseIsPostsSorted() {
            topSearchBarUiState =
                topSearchBarUiState.copy(
                    isCompletedLoadingPostsSortedByViewCount = false,
                )
        }

        private fun onUpdateKeyword(newKeyword: String) {
            topSearchBarUiState = topSearchBarUiState.copy(keyword = newKeyword)
        }
    }
