package com.segnities007.seg.ui.screens.hub.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

data class TopSearchBarUiState(
    val keyword: String = "",
    val isCompletedLoadingUsers: Boolean = true,
    val isCompletedLoadingPosts: Boolean = true,
    val isCompletedLoadingPostsSortedByViewCount: Boolean = true,
)

data class TopSearchBarUiAction(
    val onUpdateKeyword: (newKeyword: String) -> Unit,
)

@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
        private val userRepository: UserRepository,
    ) : ViewModel() {
        var topSearchBarUiState by mutableStateOf(TopSearchBarUiState())
            private set

        var searchState by mutableStateOf(SearchState())
            private set

        fun onGetTopSearchBarUiAction(): TopSearchBarUiAction =
            TopSearchBarUiAction(
                onUpdateKeyword = this::onUpdateKeyword,
            )

        fun onGetSearchUiAction(): SearchAction =
            SearchAction(
                onEnter = this::onEnter,
                onResetListsOfSearchUiState = this::onResetListsOfSearchUiState,
                onGetUsersByKeyword = this::onGetUsersByKeyword,
                onGetBeforeUsersByKeyword = this::onGetBeforeUsersByKeyword,
                onGetPostsByKeyword = this::onGetPostsByKeyword,
                onGetBeforePostsByKeyword = this::onGetBeforePostsByKeyword,
                onGetPostsByKeywordSortedByViewCount = this::onGetPostsByKeywordSortedByViewCount,
                onGetBeforePostsByKeywordSortedByViewCount = this::onGetBeforePostsByKeywordSortedByViewCount,
                onProcessOfEngagementAction = this::onProcessOfEngagementAction,
            )

        private fun onProcessOfEngagementAction(newPost: Post) {
            onUpdatePosts(newPost)
        }

        private fun onEnter(keyword: String) {
            viewModelScope.launch(Dispatchers.IO) {
                val users = userRepository.onGetUsersByKeyword(keyword)
                val posts = postRepository.onGetPostsByKeyword(keyword)
                val postsSortedByViewCount = postRepository.onGetPostsByKeywordSortedByViewCount(keyword)
                onResetIs()
                searchState = searchState.copy(users = users, posts = posts, postsSortedByViewCount = postsSortedByViewCount)
            }
        }

        private fun onResetListsOfSearchUiState() {
            searchState = searchState.copy(users = listOf(), posts = listOf(), postsSortedByViewCount = listOf())
            onResetIs()
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

        private fun onGetUsersByKeyword(keyword: String) {
            viewModelScope.launch(Dispatchers.IO) {
                searchState =
                    searchState.copy(
                        users = userRepository.onGetUsersByKeyword(keyword),
                    )
            }
        }

        private fun onGetBeforeUsersByKeyword(
            keyword: String,
            afterPostCreateAt: LocalDateTime,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                val newUsers = userRepository.onGetBeforeUsersByKeyword(keyword, afterPostCreateAt)

                if (newUsers.isNotEmpty()) {
                    val users = searchState.users.plus(newUsers)
                    searchState = searchState.copy(users = users)
                    return@launch
                } else {
                    onBeFalseIsUsers()
                }
            }
        }

        private fun onGetPostsByKeyword(keyword: String) {
            viewModelScope.launch(Dispatchers.IO) {
                searchState = searchState.copy(posts = postRepository.onGetPostsByKeyword(keyword))
            }
        }

        private fun onGetBeforePostsByKeyword(
            keyword: String,
            afterPostCreateAt: LocalDateTime,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                val newPosts = postRepository.onGetBeforePostsByKeyword(keyword, afterPostCreateAt)
                if (newPosts.isNotEmpty()) {
                    val posts = searchState.posts.plus(newPosts)
                    searchState = searchState.copy(posts = posts)
                    searchState = searchState.copy(posts = searchState.posts.plus(newPosts))
                } else {
                    onBeFalseIsPosts()
                }
            }
        }

        private fun onGetPostsByKeywordSortedByViewCount(keyword: String) {
            viewModelScope.launch(Dispatchers.IO) {
                searchState = searchState.copy(postsSortedByViewCount = postRepository.onGetPostsByKeywordSortedByViewCount(keyword))
            }
        }

        private fun onGetBeforePostsByKeywordSortedByViewCount(
            keyword: String,
            viewCount: Int,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                val newPosts = postRepository.onGetBeforePostsByKeywordSortedByViewCount(keyword, viewCount)
                if (newPosts.isNotEmpty()) {
                    val postsSortedByViewCount = searchState.postsSortedByViewCount.plus(newPosts)
                    searchState = searchState.copy(postsSortedByViewCount = postsSortedByViewCount)
                } else {
                    onBeFalseIsPostsSorted()
                }
            }
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
