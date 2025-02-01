package com.segnities007.seg.ui.screens.hub.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.domain.repository.UserRepository
import com.segnities007.seg.ui.components.card.postcard.EngagementIconAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

data class TopSearchBarUiState(
    val keyword: String = "",
    val index: Int = 0,
    val titles: List<String> =
        listOf(
            "Most View",
            "Latest",
            "Users",
        ),
    val isCompletedLoadingUsers: Boolean = true,
    val isCompletedLoadingPosts: Boolean = true,
    val isCompletedLoadingPostsSortedByViewCount: Boolean = true,
)

data class TopSearchBarUiAction(
    val onUpdateKeyword: (newKeyword: String) -> Unit,
    val onUpdateIndex: (newIndex: Int) -> Unit,
)

data class SearchUiState(
    val users: List<User> = listOf(),
    val posts: List<Post> = listOf(),
    val postsSortedByViewCount: List<Post> = listOf(),
)

data class SearchUiAction(
    val onEnter: (keyword: String) -> Unit,
    val onResetListsOfSearchUiState: () -> Unit,
    val onGetUsersByKeyword: (keyword: String) -> Unit,
    val onGetBeforeUsersByKeyword: (keyword: String, afterPostCreateAt: LocalDateTime) -> Unit,
    val onGetPostsByKeyword: (keyword: String) -> Unit,
    val onGetBeforePostsByKeyword: (keyword: String, afterPostCreateAt: LocalDateTime) -> Unit,
    val onGetPostsByKeywordSortedByViewCount: (keyword: String) -> Unit,
    val onGetBeforePostsByKeywordSortedByViewCount: (keyword: String, viewCount: Int) -> Unit,
    val onProcessOfEngagementAction: (newPost: Post) -> Unit,
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

        var searchUiState by mutableStateOf(SearchUiState())
            private set

        fun onGetTopSearchBarUiAction(): TopSearchBarUiAction =
            TopSearchBarUiAction(
                onUpdateKeyword = this::onUpdateKeyword,
                onUpdateIndex = this::onUpdateIndex,
            )

        fun onGetSearchUiAction(): SearchUiAction =
            SearchUiAction(
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

        private fun onProcessOfEngagementAction(newPost: Post){
            onUpdatePosts(newPost)
        }

        private fun onEnter(keyword: String) {
            viewModelScope.launch(Dispatchers.IO) {
                val users = userRepository.onGetUsersByKeyword(keyword)
                val posts = postRepository.onGetPostsByKeyword(keyword)
                val postsSortedByViewCount = postRepository.onGetPostsByKeywordSortedByViewCount(keyword)
                onResetIs()
                searchUiState = searchUiState.copy(users = users, posts = posts, postsSortedByViewCount = postsSortedByViewCount)
            }
        }

        private fun onResetListsOfSearchUiState() {
            searchUiState = searchUiState.copy(users = listOf(), posts = listOf(), postsSortedByViewCount = listOf())
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

        private fun onUpdateIndex(newIndex: Int) {
            topSearchBarUiState = topSearchBarUiState.copy(index = newIndex)
        }

        private fun onGetUsersByKeyword(keyword: String) {
            viewModelScope.launch(Dispatchers.IO) {
                searchUiState =
                    searchUiState.copy(
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
                    val users = searchUiState.users.plus(newUsers)
                    searchUiState = searchUiState.copy(users = users)
                    return@launch
                } else {
                    onBeFalseIsUsers()
                }
            }
        }

        private fun onGetPostsByKeyword(keyword: String) {
            viewModelScope.launch(Dispatchers.IO) {
                searchUiState = searchUiState.copy(posts = postRepository.onGetPostsByKeyword(keyword))
            }
        }

        private fun onGetBeforePostsByKeyword(
            keyword: String,
            afterPostCreateAt: LocalDateTime,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                val newPosts = postRepository.onGetBeforePostsByKeyword(keyword, afterPostCreateAt)
                if (newPosts.isNotEmpty()) {
                    val posts = searchUiState.posts.plus(newPosts)
                    searchUiState = searchUiState.copy(posts = posts)
                    searchUiState = searchUiState.copy(posts = searchUiState.posts.plus(newPosts))
                } else {
                    onBeFalseIsPosts()
                }
            }
        }

        private fun onGetPostsByKeywordSortedByViewCount(keyword: String) {
            viewModelScope.launch(Dispatchers.IO) {
                searchUiState = searchUiState.copy(postsSortedByViewCount = postRepository.onGetPostsByKeywordSortedByViewCount(keyword))
            }
        }

        private fun onGetBeforePostsByKeywordSortedByViewCount(
            keyword: String,
            viewCount: Int,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                val newPosts = postRepository.onGetBeforePostsByKeywordSortedByViewCount(keyword, viewCount)
                if (newPosts.isNotEmpty()) {
                    val postsSortedByViewCount = searchUiState.postsSortedByViewCount.plus(newPosts)
                    searchUiState = searchUiState.copy(postsSortedByViewCount = postsSortedByViewCount)
                } else {
                    onBeFalseIsPostsSorted()
                }
            }
        }

        private fun onUpdatePosts(newPost: Post) {
            var newPosts =
                searchUiState.posts.map { post ->
                    if (newPost.id == post.id) newPost else post
                }

            searchUiState = searchUiState.copy(posts = newPosts)

            newPosts =
                searchUiState.postsSortedByViewCount.map { post ->
                    if (newPost.id == post.id) newPost else post
                }

            searchUiState = searchUiState.copy(postsSortedByViewCount = newPosts)
        }

    }
