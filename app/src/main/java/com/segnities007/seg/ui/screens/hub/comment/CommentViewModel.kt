package com.segnities007.seg.ui.screens.hub.comment

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
data class CommentUiState(
    val comments: List<Post> = listOf(),
)

@Immutable
data class CommentUiAction(
    val onGetComments: (comment: Post) -> Unit,
    val onProcessOfEngagementAction: (newPost: Post) -> Unit,
)

@HiltViewModel
class CommentViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
    ) : ViewModel() {
        var commentUiState by mutableStateOf(CommentUiState())
            private set

        fun onGetCommentUiAction(): CommentUiAction =
            CommentUiAction(
                onGetComments = this::onGetComments,
                onProcessOfEngagementAction = this::onProcessOfEngagementAction,
            )

        private fun onProcessOfEngagementAction(newPost: Post) {
//            onUpdatePosts(newPost)
            //TODO
        }

        private fun onGetComments(comment: Post){
            viewModelScope.launch(Dispatchers.IO){
                val comments = postRepository.onGetPosts(comment.comments)
                commentUiState = commentUiState.copy(comments = comments)
            }
        }

//        private fun onUpdatePosts(newPost: Post) {
//            val newPosts =
//                commentUiState.comments.map { post ->
//                    if (newPost.id == post.id) newPost else post
//                }
//            val comment = if (commentUiState.comment.id == newPost.id) newPost else commentUiState.comment
//
//            commentUiState = commentUiState.copy(comments = newPosts, comment = comment)
//        }
    }
