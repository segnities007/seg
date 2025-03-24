package com.segnities007.seg.ui.components.card.postcard

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState
import com.segnities007.seg.ui.screens.hub.home.HomeAction
import com.segnities007.seg.ui.screens.hub.setting.my_posts.MyPostsAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
data class PostCardUiAction(
    val onDeletePost: (
        post: Post,
        myPostUiAction: MyPostsAction,
        homeAction: HomeAction,
        hubState: HubState,
        hubAction: HubAction,
    ) -> Unit,
    val onClickIcon: (onHubNavigate: (NavigationHubRoute) -> Unit) -> Unit,
    val onClickPostCard: (onHubNavigate: (NavigationHubRoute) -> Unit) -> Unit,
    val onIncrementViewCount: (post: Post) -> Unit,
    val onLike: (
        post: Post,
        hubState: HubState,
        hubAction: HubAction,
        onProcessOfEngagementAction: (newPost: Post) -> Unit,
    ) -> Unit,
    val onRepost: (
        post: Post,
        hubState: HubState,
        hubAction: HubAction,
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
            myPostsAction: MyPostsAction,
            homeAction: HomeAction,
            hubState: HubState,
            hubAction: HubAction,
        ) {
            val newPostsOfSelf = hubState.user.posts.minus(post.id)
            myPostsAction.onRemovePostFromPosts(post)
            val newSelf = hubState.user.copy(posts = newPostsOfSelf)
            hubAction.onUpdateSelf(newSelf)
            viewModelScope.launch(Dispatchers.IO) {
                postRepository.onDeletePost(post)
                homeAction.onGetNewPosts()
            }
        }

        private fun onClickIcon(onHubNavigate: (NavigationHubRoute) -> Unit) {
            onHubNavigate(NavigationHubRoute.Account)
        }

        private fun onClickPostCard(onHubNavigate: (NavigationHubRoute) -> Unit) {
            onHubNavigate(NavigationHubRoute.Comment)
        }

        private fun onIncrementViewCount(post: Post) {
            viewModelScope.launch(Dispatchers.IO) {
                postRepository.onIncrementView(post)
            }
        }

        private fun onLike(
            post: Post,
            hubState: HubState,
            hubAction: HubAction,
            onProcessOfEngagementAction: (newPost: Post) -> Unit, // 起動した際に行いたい処理
        ) {
            val newPost: Post

            if (hubState.user.likes.contains(post.id)) {
                newPost = post.copy(likeCount = post.likeCount - 1)
                viewModelScope.launch(Dispatchers.IO) {
                    postRepository.onUnLike(post = newPost, user = hubState.user)
                }
                hubAction.onRemovePostIDFromMyLikes(post.id)
            } else {
                newPost = post.copy(likeCount = post.likeCount + 1)
                viewModelScope.launch(Dispatchers.IO) {
                    postRepository.onLike(post = newPost, user = hubState.user)
                }
                hubAction.onAddPostIDToMyLikes(post.id)
            }

            onProcessOfEngagementAction(newPost)
        }

        private fun onRepost(
            post: Post,
            hubState: HubState,
            hubAction: HubAction,
            onProcessOfEngagementAction: (newPost: Post) -> Unit, // 起動した際に行いたい処理
        ) {
            val newPost: Post
            if (hubState.user.reposts.contains(post.id)) {
                newPost = post.copy(repostCount = post.repostCount - 1)
                viewModelScope.launch(Dispatchers.IO) {
                    postRepository.onUnRepost(post = newPost, user = hubState.user)
                }
            } else {
                newPost = post.copy(repostCount = post.repostCount + 1)
                viewModelScope.launch(Dispatchers.IO) {
                    postRepository.onRepost(post = newPost, user = hubState.user)
                }
            }

            if (hubState.user.reposts.contains(post.id)) {
                hubAction.onRemovePostIDFromMyReposts(post.id)
            } else {
                hubAction.onAddPostIDToMyReposts(post.id)
            }
            onProcessOfEngagementAction(newPost)
        }
    }
