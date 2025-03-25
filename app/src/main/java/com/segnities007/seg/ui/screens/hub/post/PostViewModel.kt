package com.segnities007.seg.ui.screens.hub.post

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
    ) : ViewModel() {
        var postState by mutableStateOf(PostState())
            private set

        fun onGetPostUiAction(): PostAction =
            PostAction(
                onUpdateInputText = this::onUpdateInputText,
                onCreatePost = this::onCreatePost,
                onCreateComment = this::onCreateComment,
                onUpdateIsLoading = this::onUpdateIsLoading,
            )

        private fun onUpdateIsLoading(isLoading: Boolean) {
            postState = postState.copy(isLoading = isLoading)
        }

        private fun onCreatePost(
            user: User,
            onUpdateIsLoading: (isLoading: Boolean) -> Unit,
            onUpdateSelf: () -> Unit,
            onNavigate: () -> Unit,
        ) {
            val description = postState.inputText
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
            hubState: HubState,
            hubAction: HubAction,
            onUpdateIsLoading: (isLoading: Boolean) -> Unit,
            onNavigate: () -> Unit,
        ) {
            val description = postState.inputText
            onUpdateIsLoading(true)
            viewModelScope.launch(Dispatchers.IO) {
                val result =
                    postRepository.onCreateComment(
                        description = description,
                        self = hubState.user,
                        commentedPost = hubState.comment,
                    )
                if (result) {
                    hubAction.onGetUser()
                    val updatedCommentedPost = postRepository.onGetPost(hubState.comment.id)
                    hubAction.onSetComment(updatedCommentedPost)
                    viewModelScope.launch(Dispatchers.Main) {
                        onNavigate()
                    }
                }
                onUpdateIsLoading(false)
            }
        }

        private fun onUpdateInputText(newInputText: String) {
            postState = postState.copy(inputText = newInputText)
        }
    }
