package com.segnities007.seg.ui.screens.hub.home.comment

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.segnities007.seg.data.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@Immutable
data class CommentUiState(
    val comments: List<Post> = listOf(),
    val comment: Post = Post(),
)

@Immutable
data class CommentUiAction(
    val onGetComment: (comment: Post) -> Unit,
    val onProcessOfEngagementAction: (newPost: Post) -> Unit,
)

@HiltViewModel
class CommentViewModel
    @Inject
    constructor() : ViewModel() {
        var commentUiState by mutableStateOf(CommentUiState())
            private set

        fun onGetCommentUiAction(): CommentUiAction =
            CommentUiAction(
                onGetComment = this::onGetComment,
                onProcessOfEngagementAction = this::onProcessOfEngagementAction,
            )

        private fun onProcessOfEngagementAction(newPost: Post) {
            onUpdatePosts(newPost)
        }

        private fun onGetComment(comment: Post) {
            commentUiState = commentUiState.copy(comment = comment)
        }

        private fun onUpdatePosts(newPost: Post) {
            val newPosts =
                commentUiState.comments.map { post ->
                    if (newPost.id == post.id) newPost else post
                }
            val comment = if (commentUiState.comment.id == newPost.id) newPost else commentUiState.comment

            commentUiState = commentUiState.copy(comments = newPosts, comment = comment)
        }
    }
