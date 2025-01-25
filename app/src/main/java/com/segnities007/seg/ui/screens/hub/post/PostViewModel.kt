package com.segnities007.seg.ui.screens.hub.post

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PostUiState(
    val inputText: String = "",
    val selectedUris: List<String> = listOf(),
)

data class PostUiAction(
    val onUpdateInputText: (newInputText: String) -> Unit,
    val onCreatePost: (user: User) -> Unit,
)

@HiltViewModel
class PostViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
    ) : ViewModel() {
        var postUiState by mutableStateOf(PostUiState())
            private set

        fun getPostUiAction(): PostUiAction =
            PostUiAction(
                onUpdateInputText = this::onUpdateInputText,
                onCreatePost = this::onCreatePost,
            )

        private fun onCreatePost(user: User) {
            val description = postUiState.inputText
            viewModelScope.launch(Dispatchers.IO) {
                postRepository.createPost(description = description, user = user)
            }
        }

        private fun onUpdateInputText(newInputText: String) {
            postUiState = postUiState.copy(inputText = newInputText)
        }
    }
