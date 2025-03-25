package com.segnities007.seg.ui.screens.hub.comment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.domain.repository.PostRepository
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

        fun onGetCommentUiAction(): CommentAction =
            CommentAction(
                onGetComments = this::onGetComments,
                onProcessOfEngagementAction = this::onProcessOfEngagementAction,
            )

        private fun onProcessOfEngagementAction(updatedPost: Post) {
            onUpdatePosts(updatedPost)
        }

        private fun onGetComments(comment: Post) {
            viewModelScope.launch(Dispatchers.IO) {
                val comments = postRepository.onGetComments(comment)
                commentState = commentState.copy(comments = comments)
            }
        }

        private fun onUpdatePosts(updatedPost: Post) {
            val newPosts =
                commentState.comments.map { post ->
                    if (updatedPost.id == post.id) updatedPost else post
                }
            commentState = commentState.copy(comments = newPosts)
        }
    }
