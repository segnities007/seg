package com.segnities007.seg.ui.screens.hub.comment

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
            is CommentAction.GetComments -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val comments = postRepository.onGetComments(action.comment)
                    commentState = commentState.copy(comments = comments)
                }
            }

            is CommentAction.ProcessOfEngagementAction -> {
                val newPosts =
                    commentState.comments.map { post ->
                        if (action.updatedPost.id == post.id) action.updatedPost else post
                    }
                commentState = commentState.copy(comments = newPosts)
            }
        }
    }
}
