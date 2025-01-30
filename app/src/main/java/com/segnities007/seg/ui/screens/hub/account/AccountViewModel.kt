package com.segnities007.seg.ui.screens.hub.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.domain.repository.UserRepository
import com.segnities007.seg.ui.components.card.postcard.EngagementIconAction
import com.segnities007.seg.ui.components.card.postcard.PostCardUiAction
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AccountUiState(
    val user: User = User(),
    val posts: List<Post> = listOf(),
    val likedPosts: List<Post> = listOf(), // TODO
    val repostedPosts: List<Post> = listOf(), // TODO
    val isNotCompleted: Boolean = true,
)

data class AccountUiAction(
    val onReset: () -> Unit,
    val onInitAccountUiState: (userID: String) -> Unit,
    val onGetOtherUser: (userID: String) -> Unit,
    val onSetOtherUser: (user: User) -> Unit,
    val onGetUserPosts: (userID: String) -> Unit,
    val onChangeIsNotCompletedOfAccount: () -> Unit,
    val onGetBeforePosts: (updateAt: java.time.LocalDateTime) -> Unit,
    val onFollow: (myself: User, other: User, onGetMyself: () -> Unit) -> Unit,
    val onUnFollow: (myself: User, other: User, onGetMyself: () -> Unit) -> Unit,
)

@HiltViewModel
class AccountViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
        private val postRepository: PostRepository,
    ) : ViewModel() {
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

        var accountUiState by mutableStateOf(AccountUiState())
            private set

        fun getAccountUiAction(): AccountUiAction =
            AccountUiAction(
                onGetOtherUser = this::onGetOtherUser,
                onSetOtherUser = this::onSetOtherUsers,
                onGetUserPosts = this::onGetUserPosts,
                onGetBeforePosts = this::onGetBeforePosts,
                onFollow = this::onFollow,
                onUnFollow = this::onUnFollow,
                onReset = this::onResetAccount,
                onInitAccountUiState = this::onInitAccountUiState,
                onChangeIsNotCompletedOfAccount = this::onChangeIsNotCompletedOfAccount,
            )

        fun getPostUiAction(): PostCardUiAction =
            PostCardUiAction(
                onClickIcon = this::onClickIcon,
                onClickPostCard = this::onClickPostCard,
                onIncrementViewCount = this::onIncrementViewCount,
                onDeletePost = {},
            )

        fun onGetEngagementIconAction(): EngagementIconAction =
            EngagementIconAction(
                onLike = this::onLike,
                onRepost = this::onRepost,
                onComment = this::onComment,
            )

        private fun onInitAccountUiState(userID: String) {
            viewModelScope.launch(ioDispatcher) {
                val user = userRepository.getOtherUser(userID)
                accountUiState = accountUiState.copy(user = user)

                val posts = postRepository.onGetPostsOfUser(userID)
                if (posts.isNotEmpty()) {
                    accountUiState = accountUiState.copy(posts = posts)
                } else {
                    onChangeIsNotCompletedOfAccount()
                }
            }
        }

        private fun onGetBeforePosts(updateAt: java.time.LocalDateTime) {
            viewModelScope.launch(Dispatchers.IO) {
                val posts = postRepository.onGetBeforePostsOfUser(accountUiState.user.userID, updateAt)
                if (posts.isNotEmpty()) {
                    accountUiState = accountUiState.copy(posts = accountUiState.posts.plus(posts))
                } else {
                    onChangeIsNotCompletedOfAccount()
                }
            }
        }

        private fun onClickIcon(onHubNavigate: (Route) -> Unit) {
            onHubNavigate(NavigationHubRoute.Account())
        }

        private fun onClickPostCard(onHubNavigate: (Route) -> Unit) {
            onHubNavigate(NavigationHubRoute.Comment())
        }

        private fun onUpdatePosts(newPost: Post) {
            val newPosts =
                accountUiState.posts.map { post ->
                    if (newPost.id == post.id) newPost else post
                }

            accountUiState = accountUiState.copy(posts = newPosts)
        }

        private fun onFollow(
            myself: User,
            other: User,
            onGetMyself: () -> Unit,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                userRepository.followUser(myself, other)
                onGetMyself()
            }
        }

        private fun onUnFollow(
            myself: User,
            other: User,
            onGetMyself: () -> Unit,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                userRepository.unFollowUser(myself, other)
                onGetMyself()
            }
        }

        private fun onSetOtherUsers(user: User) {
            viewModelScope.launch(Dispatchers.IO) {
                accountUiState = accountUiState.copy(user = user)
            }
        }

        private fun onGetOtherUser(userID: String) {
            viewModelScope.launch(ioDispatcher) {
                val user = userRepository.getOtherUser(userID)
                accountUiState = accountUiState.copy(user = user)
            }
        }

        private fun onGetUserPosts(userID: String) {
            viewModelScope.launch(ioDispatcher) {
                val posts = postRepository.onGetPostsOfUser(userID)
                if (posts.isNotEmpty()) {
                    accountUiState = accountUiState.copy(posts = posts)
                } else {
                    onChangeIsNotCompletedOfAccount()
                }
            }
        }

        private fun onIncrementViewCount(post: Post) {
            viewModelScope.launch(Dispatchers.IO) {
                postRepository.onIncrementView(post)
            }
        }

        private fun onChangeIsNotCompletedOfAccount() {
            accountUiState = accountUiState.copy(isNotCompleted = !accountUiState.isNotCompleted)
        }

        private fun onResetAccount() {
            accountUiState = accountUiState.copy(isNotCompleted = true)
        }

        private fun onLike(
            post: Post,
            myself: User,
            onGetMyself: () -> Unit,
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
            onUpdatePosts(newPost)
            onGetMyself()
            onGetOtherUser(accountUiState.user.userID)
        }

        private fun onRepost(
            post: Post,
            myself: User,
            onGetMyself: () -> Unit,
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
            onUpdatePosts(newPost)
            onGetMyself()
            onGetOtherUser(accountUiState.user.userID)
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
