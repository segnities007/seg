package com.example.feature.screens.hub.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Post
import com.example.domain.repository.PostRepository
import com.example.domain.repository.UserRepository
import com.example.feature.components.bar.top_search_bar.TopSearchBarAction
import com.example.feature.components.bar.top_search_bar.TopSearchBarState
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
            is SearchAction.GetBeforePostsByKeyword -> {
                viewModelScope.launch(Dispatchers.IO) {
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
                }
            }

            is SearchAction.GetBeforePostsByKeywordSortedByViewCount -> {
                viewModelScope.launch(Dispatchers.IO) {
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
                }
            }

            is SearchAction.GetBeforeUsersByKeyword -> {
                viewModelScope.launch(Dispatchers.IO) {
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
                }
            }

            is SearchAction.GetPostsByKeyword -> {
                viewModelScope.launch(Dispatchers.IO) {
                    searchState =
                        searchState.copy(posts = postRepository.onGetPostsByKeyword(action.keyword))
                }
            }

            is SearchAction.GetPostsByKeywordSortedByViewCount -> {
                viewModelScope.launch(Dispatchers.IO) {
                    searchState =
                        searchState.copy(
                            postsSortedByViewCount =
                                postRepository.onGetPostsByKeywordSortedByViewCount(
                                    action.keyword,
                                ),
                        )
                }
            }

            is SearchAction.GetUsersByKeyword -> {
                viewModelScope.launch(Dispatchers.IO) {
                    searchState =
                        searchState.copy(
                            users = userRepository.onGetUsersByKeyword(action.keyword),
                        )
                }
            }

            is SearchAction.ProcessOfEngagementAction -> {
                onUpdatePosts(action.newPost)
            }

            SearchAction.ResetSearchState -> {
                searchState =
                    searchState.copy(
                        users = listOf(),
                        posts = listOf(),
                        postsSortedByViewCount = listOf(),
                    )
                onResetIs()
            }

            is SearchAction.Search -> {
                val keyword = action.keyword
                viewModelScope.launch(Dispatchers.IO) {
                    val users = userRepository.onGetUsersByKeyword(keyword)
                    val posts = postRepository.onGetPostsByKeyword(keyword)
                    val postsSortedByViewCount =
                        postRepository.onGetPostsByKeywordSortedByViewCount(keyword)
                    onResetIs()
                    searchState =
                        searchState.copy(
                            users = users,
                            posts = posts,
                            postsSortedByViewCount = postsSortedByViewCount,
                        )
                }
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

    private fun onUpdatePosts(newPost: Post) {
        var newPosts =
            searchState.posts.map { post ->
                if (newPost.id == post.id) newPost else post
            }

        searchState = searchState.copy(posts = newPosts)

        newPosts =
            searchState.postsSortedByViewCount.map { post ->
                if (newPost.id == post.id) newPost else post
            }

        searchState = searchState.copy(postsSortedByViewCount = newPosts)
    }
}
