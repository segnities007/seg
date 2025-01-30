package com.segnities007.seg.ui.screens.hub.home.comment

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.ui.components.card.postcard.EngagementIconAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
data class CommentUiState(
    val comments: List<Post> = listOf(),
    val comment: Post = Post(),
)

@Immutable
data class CommentUiAction(
    val onGetComment: (comment: Post) -> Unit,
    val onGetComments: (comment: Post) -> Unit,
    val onGetBeforeComments: (comment: Post, commentID: Int) -> Unit,
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
                onGetComment = this::onGetComment,
                onGetComments = this::onGetComments,
                onGetBeforeComments = this::onGetBeforeComments,
            )

        fun onGetEngagementIconAction(): EngagementIconAction =
            EngagementIconAction(
                onLike = this::onLike,
                onRepost = this::onRepost,
                onComment = this::onComment,
            )

        private fun onGetComment(comment: Post) {
            commentUiState = commentUiState.copy(comment = comment)
        }

        private fun onGetComments(comment: Post) {
            viewModelScope.launch(Dispatchers.IO) {
                // todo
            }
        }

        private fun onGetBeforeComments(
            comment: Post,
            commentID: Int,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                // todo
            }
        }

        private fun onUpdatePosts(newPost: Post) {
            val newPosts =
                commentUiState.comments.map { post ->
                    if (newPost.id == post.id) newPost else post
                }
            val comment = if (commentUiState.comment.id == newPost.id) newPost else commentUiState.comment

            commentUiState = commentUiState.copy(comments = newPosts, comment = comment)
        }

        private fun onLike(
            post: Post,
            myself: User,
            onAddOrRemoveFromMyList: () -> Unit,
        ) {
            val newPost: Post
            if (!myself.likes.contains(post.id)) {
                newPost = post.copy(likeCount = post.likeCount + 1)
                viewModelScope.launch(Dispatchers.IO) {
                    postRepository.onLike(post = newPost, user = myself)
                }
            } else {
                newPost = post.copy(likeCount = post.likeCount - 1)
                viewModelScope.launch(Dispatchers.IO) {
                    postRepository.onUnLike(post = newPost, user = myself)
                }
            }
            onUpdatePosts(newPost)
            onAddOrRemoveFromMyList()
        }

        private fun onRepost(
            post: Post,
            myself: User,
            onAddOrRemoveFromMyList: () -> Unit,
        ) {
            val newPost: Post
            if (!myself.reposts.contains(post.id)) {
                newPost = post.copy(repostCount = post.repostCount + 1)
                viewModelScope.launch(Dispatchers.IO) {
                    postRepository.onRepost(post = newPost, user = myself)
                }
            } else {
                newPost = post.copy(repostCount = post.repostCount - 1)
                viewModelScope.launch(Dispatchers.IO) {
                    postRepository.onUnRepost(post = newPost, user = myself)
                }
            }
            onUpdatePosts(newPost)
            onAddOrRemoveFromMyList()
        }

        private fun onComment(
            post: Post,
            comment: Post,
            myself: User,
            updateMyself: () -> Unit,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                // TODO
            }
        }
    }
