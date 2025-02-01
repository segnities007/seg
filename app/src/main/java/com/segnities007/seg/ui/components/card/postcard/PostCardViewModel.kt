package com.segnities007.seg.ui.components.card.postcard

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
data class PostCardUiAction(
    val onDeletePost: (post: Post) -> Unit,
    val onClickIcon: (onHubNavigate: (Route) -> Unit) -> Unit,
    val onClickPostCard: (onHubNavigate: (Route) -> Unit) -> Unit,
    val onIncrementViewCount: (post: Post) -> Unit,
    val onLike: (
        post: Post,
        myself: User,
        onProcessOfEngagementAction: (newPost: Post) -> Unit,
    ) -> Unit,
    val onRepost: (
        post: Post,
        myself: User,
        onProcessOfEngagementAction: (newPost: Post) -> Unit,
    ) -> Unit,
)

@HiltViewModel
class PostCardViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
    ) : ViewModel() {
        fun onGetPostCardUiAction(): PostCardUiAction =
            PostCardUiAction(
                onDeletePost = this::onDeletePost,
                onClickIcon = this::onClickIcon,
                onClickPostCard = this::onClickPostCard,
                onIncrementViewCount = this::onIncrementViewCount,
                onLike = this::onLike,
                onRepost = this::onRepost,
            )

        private fun onDeletePost(post: Post) {
            viewModelScope.launch(Dispatchers.IO) {
                postRepository.onDeletePost(post)
            }
        }

        private fun onClickIcon(onHubNavigate: (Route) -> Unit) {
            onHubNavigate(NavigationHubRoute.Account())
        }

        private fun onClickPostCard(onHubNavigate: (Route) -> Unit) {
            onHubNavigate(NavigationHubRoute.Comment())
        }

        private fun onIncrementViewCount(post: Post) {
            viewModelScope.launch(Dispatchers.IO) {
                postRepository.onIncrementView(post)
            }
        }

        private fun onLike(
            post: Post,
            myself: User,
            onGetEngagementProcess: (newPost: Post) -> Unit, // 起動した際に行いたい処理
        ) {
            val newPost: Post
            if (myself.likes.contains(post.id)) {
                newPost = post.copy(likeCount = post.likeCount - 1)
                viewModelScope.launch(Dispatchers.IO) {
                    postRepository.onLike(post = newPost, user = myself)
                }
            } else {
                newPost = post.copy(likeCount = post.likeCount + 1)
                viewModelScope.launch(Dispatchers.IO) {
                    postRepository.onUnLike(post = newPost, user = myself)
                }
            }
            onGetEngagementProcess(newPost)
        }

        private fun onRepost(
            post: Post,
            myself: User,
            onProcessOfEngagementAction: (newPost: Post) -> Unit, // 起動した際に行いたい処理
        ) {
            val newPost: Post
            if (myself.reposts.contains(post.id)) {
                newPost = post.copy(repostCount = post.repostCount - 1)
                viewModelScope.launch(Dispatchers.IO) {
                    postRepository.onRepost(post = newPost, user = myself)
                }
            } else {
                newPost = post.copy(repostCount = post.repostCount + 1)
                viewModelScope.launch(Dispatchers.IO) {
                    postRepository.onUnRepost(post = newPost, user = myself)
                }
            }
            onProcessOfEngagementAction(newPost)
        }
    }
