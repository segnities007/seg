package com.example.feature.screens.hub.comment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
    ) : ViewModel() {
        var commentState by mutableStateOf(CommentState())
            private set

        fun onCommentAction(action: CommentAction) {
            when (action) {
                is CommentAction.GetComments -> getComments(action)
                is CommentAction.ProcessOfEngagementAction,
                -> commentReducer(action, commentState)
            }
        }

        private fun getComments(action: CommentAction.GetComments) {
            viewModelScope.launch(Dispatchers.IO) {
                val comments = postRepository.onGetComments(action.comment)
                commentState = commentState.copy(comments = comments)
            }
        }
    }
