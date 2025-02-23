package com.segnities007.seg.ui.screens.hub.post

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState
import com.segnities007.seg.ui.screens.hub.home.HomeUiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PostUiState(
    val inputText: String = "",
    val isLoading: Boolean = false,
)

data class PostUiAction(
    val onUpdateIsLoading: (isLoading: Boolean) -> Unit,
    val onUpdateInputText: (newInputText: String) -> Unit,
    val onCreatePost: (
        user: User,
        onUpdateIsLoading: (isLoading: Boolean) -> Unit,
        onUpdateSelf: () -> Unit,
        onNavigate: () -> Unit,
    ) -> Unit,
    val onCreateComment: (
        hubUiState: HubUiState,
        hubUiAction: HubUiAction,
        onUpdateIsLoading: (isLoading: Boolean) -> Unit,
        onNavigate: () -> Unit,
    ) -> Unit,
)

@HiltViewModel
class PostViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
    ) : ViewModel() {
        var postUiState by mutableStateOf(PostUiState())
            private set

        fun onGetPostUiAction(): PostUiAction =
            PostUiAction(
                onUpdateInputText = this::onUpdateInputText,
                onCreatePost = this::onCreatePost,
                onCreateComment = this::onCreateComment,
                onUpdateIsLoading = this::onUpdateIsLoading,
            )

        private fun onUpdateIsLoading(isLoading: Boolean) {
            postUiState = postUiState.copy(isLoading = isLoading)
        }

        private fun onCreatePost(
            user: User,
            onUpdateIsLoading: (isLoading: Boolean) -> Unit,
            onUpdateSelf: () -> Unit,
            onNavigate: () -> Unit,
        ) {
            val description = postUiState.inputText
            onUpdateIsLoading(true)
            viewModelScope.launch(Dispatchers.IO) {
                val result = postRepository.onCreatePost(description = description, user = user)
                if (result) {
                    onUpdateSelf()
                    viewModelScope.launch(Dispatchers.Main) {
                        onNavigate()
                    }
                }
                onUpdateIsLoading(false)
            }
        }

        private fun onCreateComment(
            hubUiState: HubUiState,
            hubUiAction: HubUiAction,
            onUpdateIsLoading: (isLoading: Boolean) -> Unit,
            onNavigate: () -> Unit,
        ){
            val description = postUiState.inputText
            onUpdateIsLoading(true)
            viewModelScope.launch(Dispatchers.IO) {
                val result = postRepository.onCreateComment(
                    description = description,
                    self = hubUiState.user,
                    commentedPost = hubUiState.comment,
                )
                if (result) {
                    hubUiAction.onGetUser()
                    val updatedCommentedPost = postRepository.onGetPost(hubUiState.comment.id)
                    hubUiAction.onSetComment(updatedCommentedPost)
                    viewModelScope.launch(Dispatchers.Main) {
                        onNavigate()
                    }
                }
                onUpdateIsLoading(false)
            }
        }

        private fun onUpdateInputText(newInputText: String) {
            postUiState = postUiState.copy(inputText = newInputText)
        }
    }
