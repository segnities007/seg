package com.segnities007.seg.ui.components.card.postcard

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState
import com.segnities007.seg.ui.screens.hub.setting.my_posts.MyPostsUiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
data class PostCardUiAction(
    val onDeletePost: (
        post: Post,
        myPostUiAction: MyPostsUiAction,
        hubUiState: HubUiState,
        hubUiAction: HubUiAction,
            ) -> Unit,
    val onClickIcon: (onHubNavigate: (Route) -> Unit) -> Unit,
    val onClickPostCard: (onHubNavigate: (Route) -> Unit) -> Unit,
    val onIncrementViewCount: (post: Post) -> Unit,
    val onLike: (
        post: Post,
        hubUiState: HubUiState,
        hubUiAction: HubUiAction,
        onProcessOfEngagementAction: (newPost: Post) -> Unit,
    ) -> Unit,
    val onRepost: (
        post: Post,
        hubUiState: HubUiState,
        hubUiAction: HubUiAction,
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

        private fun onDeletePost(
            post: Post,
            myPostsUiAction: MyPostsUiAction,
            hubUiState: HubUiState,
            hubUiAction: HubUiAction,
        ) {
            val newPostsOfSelf = hubUiState.user.posts.minus(post.id)
            myPostsUiAction.onRemovePostFromPosts(post)
            val newSelf = hubUiState.user.copy(posts = newPostsOfSelf)
            hubUiAction.onUpdateSelf(newSelf)
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
            hubUiState: HubUiState,
            hubUiAction: HubUiAction,
            onProcessOfEngagementAction: (newPost: Post) -> Unit, // 起動した際に行いたい処理
        ) {
            val newPost: Post

            if (hubUiState.user.likes.contains(post.id)) {
                newPost = post.copy(likeCount = post.likeCount - 1)
                viewModelScope.launch(Dispatchers.IO) {
                    postRepository.onUnLike(post = newPost, user = hubUiState.user)
                }
                hubUiAction.onRemovePostIDFromMyLikes(post.id)
            } else {
                newPost = post.copy(likeCount = post.likeCount + 1)
                viewModelScope.launch(Dispatchers.IO) {
                    postRepository.onLike(post = newPost, user = hubUiState.user)
                }
                hubUiAction.onAddPostIDToMyLikes(post.id)
            }

            onProcessOfEngagementAction(newPost)
        }

        private fun onRepost(
            post: Post,
            hubUiState: HubUiState,
            hubUiAction: HubUiAction,
            onProcessOfEngagementAction: (newPost: Post) -> Unit, // 起動した際に行いたい処理
        ) {
            val newPost: Post
            if (hubUiState.user.reposts.contains(post.id)) {
                newPost = post.copy(repostCount = post.repostCount - 1)
                viewModelScope.launch(Dispatchers.IO) {
                    postRepository.onUnRepost(post = newPost, user = hubUiState.user)
                }
            } else {
                newPost = post.copy(repostCount = post.repostCount + 1)
                viewModelScope.launch(Dispatchers.IO) {
                    postRepository.onRepost(post = newPost, user = hubUiState.user)
                }
            }

            if (hubUiState.user.reposts.contains(post.id)) {
                hubUiAction.onRemovePostIDFromMyReposts(post.id)
            } else {
                hubUiAction.onAddPostIDToMyReposts(post.id)
            }
            onProcessOfEngagementAction(newPost)
        }
    }
