package com.segnities007.seg.ui.screens.hub.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.model.UserState
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.domain.repository.UserRepository
import com.segnities007.seg.ui.components.card.EngagementIconAction
import com.segnities007.seg.ui.components.card.PostCardUiAction
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AccountUiState(
    override val user: User = User(),
    val posts: List<Post> = listOf(),
    val users: List<User> = listOf(),
) : UserState

data class AccountUiAction(
    val onGetOtherUser: (userID: String) -> Unit,
    val onGetUserPosts: (userID: String) -> Unit,
    val onGetUsers: (userIDs: List<User>) -> Unit,
    val onSetUsers: (userIDs: List<String>) -> Unit,
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
                onGetUserPosts = this::onGetUserPosts,
                onFollow = this::onFollow,
                onSetUsers = this::onSetUsers,
                onGetUsers = this::onGetUsers,
                onUnFollow = this::onUnFollow,
            )

        fun getPostUiAction(): PostCardUiAction =
            PostCardUiAction(
                onGetNewPosts = { /*TODO*/ },
                onGetPost = { /*TODO*/ },
                onGetBeforePosts = { /*TODO*/ },
                onUpdatePost = { /*TODO*/ },
                onClickIcon = this::onClickIcon,
                onClickPostCard = this::onClickPostCard,
                onIncrementViewCount = this::onIncrementViewCount,
                onGetPosts = {},
                onDeletePost = {},
            )

        fun onGetEngagementIconAction(): EngagementIconAction =
            EngagementIconAction(
                onLike = this::onLike,
                onRepost = this::onRepost,
                onComment = this::onComment,
            )

        private fun onClickIcon(onHubNavigate: (Route) -> Unit) {
            onHubNavigate(NavigationHubRoute.Account())
        }

        private fun onClickPostCard(onHubNavigate: (Route) -> Unit) {
            onHubNavigate(NavigationHubRoute.Comment())
        }

        private fun onUpdatePosts(postID: Int) {
            viewModelScope.launch(Dispatchers.IO) {
                val newPost = postRepository.onGetPost(postID)

                val newPosts =
                    accountUiState.posts.map { post ->
                        if (newPost.id == post.id) newPost else post
                    }

                accountUiState = accountUiState.copy(posts = newPosts)
            }
        }

        private fun onSetUsers(userIDs: List<String>) {
            viewModelScope.launch(Dispatchers.IO) {
                val users = userRepository.getUsers(userIDs)
                onGetUsers(users)
            }
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

        private fun onGetUsers(users: List<User>) {
            viewModelScope.launch(Dispatchers.IO) {
                accountUiState = accountUiState.copy(users = users)
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
                accountUiState = accountUiState.copy(posts = posts)
            }
        }

        private fun onIncrementViewCount(post: Post) {
            viewModelScope.launch(Dispatchers.IO) {
                postRepository.onIncrementView(post)
            }
        }

        private fun onLike(
            post: Post,
            myself: User,
            onGetMyself: () -> Unit,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                if (myself.likes.contains(post.id)) {
                    postRepository.onLike(post = post, user = myself)
                } else {
                    postRepository.onUnLike(post = post, user = myself)
                }
                onUpdatePosts(post.id)
                onGetMyself()
                onGetOtherUser(accountUiState.user.userID)
            }
        }

        private fun onRepost(
            post: Post,
            myself: User,
            onGetMyself: () -> Unit,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                if (myself.reposts.contains(post.id)) {
                    postRepository.onRepost(post = post, user = myself)
                } else {
                    postRepository.onUnRepost(post = post, user = myself)
                }
                onUpdatePosts(post.id)
                onGetMyself()
                onGetOtherUser(accountUiState.user.userID)
            }
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
