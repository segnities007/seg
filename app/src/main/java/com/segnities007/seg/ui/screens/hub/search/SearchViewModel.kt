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
    val onGetUsersByKeyword: (keyword: String) -> Unit,
    val onGetPostsByKeyword: (keyword: String) -> Unit,
    val onGetBeforePostsByKeyword: (keyword: String, afterPostCreateAt: LocalDateTime) -> Unit,
    val onGetPostsByKeywordSortedByViewCount: (keyword: String) -> Unit,
    val onGetBeforePostsByKeywordSortedByViewCount: (keyword: String, afterPostCreateAt: LocalDateTime) -> Unit,
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
                onGetUsersByKeyword = this::onGetUsersByKeyword,
                onGetPostsByKeyword = this::onGetPostsByKeyword,
                onGetBeforePostsByKeyword = this::onGetBeforePostsByKeyword,
                onGetPostsByKeywordSortedByViewCount = this::onGetPostsByKeywordSortedByViewCount,
                onGetBeforePostsByKeywordSortedByViewCount = this::onGetBeforePostsByKeywordSortedByViewCount,
            )

        private fun onUpdateKeyword(newKeyword: String) {
            topSearchBarUiState = topSearchBarUiState.copy(keyword = newKeyword)
        }

        private fun onUpdateIndex(newIndex: Int) {
            topSearchBarUiState = topSearchBarUiState.copy(index = newIndex)
        }

        private fun onGetUsersByKeyword(keyword: String) {
            viewModelScope.launch(Dispatchers.IO) {
                searchUiState = searchUiState.copy(users = userRepository.onGetUsersByKeyword(keyword))
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
                searchUiState = searchUiState.copy(posts = postRepository.onGetBeforePostsByKeyword(keyword, afterPostCreateAt))
            }
        }

        private fun onGetPostsByKeywordSortedByViewCount(keyword: String) {
            viewModelScope.launch(Dispatchers.IO) {
                searchUiState = searchUiState.copy(postsSortedByViewCount = postRepository.onGetPostsByKeywordSortedByViewCount(keyword))
            }
        }

        private fun onGetBeforePostsByKeywordSortedByViewCount(
            keyword: String,
            afterPostCreateAt: LocalDateTime,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                searchUiState =
                    searchUiState.copy(
                        postsSortedByViewCount = postRepository.onGetBeforePostsByKeywordSortedByViewCount(keyword, afterPostCreateAt),
                    )
            }
        }
    }
