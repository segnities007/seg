package com.segnities007.seg.ui.screens.hub.setting.my_posts

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
import java.time.LocalDateTime
import javax.inject.Inject

@Immutable
data class MyPostsUiState(
    val self: User = User(),
    val posts: List<Post> = listOf(),
    val likedPosts: List<Post> = listOf(),
    val repostedPosts: List<Post> = listOf(),
    val hasNoMorePosts: Boolean = false,
    val hasNoMoreLikedPosts: Boolean = false,
    val hasNoMoreRepostedPosts: Boolean = false,
    val titles: List<String> =
        listOf(
            "Post",
            "Like",
            "Repost",
        ),
    val selectedTabIndex: Int = 0,
)

@Immutable
data class MyPostsUiAction(
    val onInit: () -> Unit,
    val onUpdateSelectedTabIndex: (index: Int) -> Unit,
    val onSetSelf: (self: User) -> Unit,
    val onGetPosts: () -> Unit,
    val onGetLikedPosts: () -> Unit,
    val onGetRepostedPosts: () -> Unit,
    val onProcessOfEngagementAction: (newPost: Post) -> Unit,
)

@HiltViewModel
class MyPostsViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
    ) : ViewModel() {
        private val takeN = 7

        var myPostsUiState by mutableStateOf(MyPostsUiState())
            private set

        fun onGetMyPostsUiAction(): MyPostsUiAction =
            MyPostsUiAction(
                onSetSelf = this::onSetSelf,
                onUpdateSelectedTabIndex = this::onUpdateSelectedTabIndex,
                onGetPosts = this::onGetPosts,
                onGetLikedPosts = this::onGetLikedPosts,
                onGetRepostedPosts = this::onGetRepostedPosts,
                onProcessOfEngagementAction = this::onProcessOfEngagementAction,
                onInit = this::onInit,
            )

        private fun onInit() {
            if (myPostsUiState.self.posts.isEmpty()) {
                onToggleHasNoMorePosts()
            }
            if (myPostsUiState.self.likes.isEmpty()) {
                onToggleHasNoMoreLikedPosts()
            }
            if (myPostsUiState.self.reposts.isEmpty()) {
                onToggleHasNoMoreRepostedPosts()
            }
        }

        private fun onProcessOfEngagementAction(newPost: Post) {
            val newPosts =
                myPostsUiState.posts.map { post ->
                    if (newPost.id == post.id) newPost else post
                }

            val newLikedPosts =
                myPostsUiState.likedPosts.map { post ->
                    if (newPost.id == post.id) newPost else post
                }

            val newRepostedPosts =
                myPostsUiState.repostedPosts.map { post ->
                    if (newPost.id == post.id) newPost else post
                }

            myPostsUiState = myPostsUiState.copy(posts = newPosts, likedPosts = newLikedPosts, repostedPosts = newRepostedPosts)
        }

        private fun onGetLikedPosts() {
            viewModelScope.launch(Dispatchers.IO) {
                if (myPostsUiState.likedPosts.isEmpty()) {
                    val posts = postRepository.onGetPosts(myPostsUiState.self.likes.take(takeN))
                    if (posts.isEmpty()) {
                        onToggleHasNoMoreLikedPosts()
                    } else {
                        val newPosts = posts.filter { post -> post.id != 0 }
                        myPostsUiState = myPostsUiState.copy(likedPosts = myPostsUiState.likedPosts.plus(newPosts))
                    }
                } else {
                    onGetBeforeLikedPosts(myPostsUiState.self.likes.take(takeN), myPostsUiState.likedPosts.last().id)
                }
            }
        }

        private fun onGetBeforeLikedPosts(
            likedList: List<Int>,
            lastLikedPostID: Int,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                // 取得したい塊の先頭の場所のIndexを取得
                val index = likedList.indexOf(lastLikedPostID) + 1
                // 取得したいPostをtakeN個だけ取得
                val posts = postRepository.onGetPosts(likedList.drop(index).take(takeN))
                if (posts.isEmpty()) {
                    onToggleHasNoMoreLikedPosts()
                } else {
                    val newPosts = posts.filter { post -> post.id != 0 }
                    myPostsUiState = myPostsUiState.copy(likedPosts = myPostsUiState.likedPosts.plus(newPosts))
                }
            }
        }

        private fun onGetRepostedPosts() {
            viewModelScope.launch(Dispatchers.IO) {
                if (myPostsUiState.repostedPosts.isEmpty()) {
                    val posts = postRepository.onGetPosts(myPostsUiState.self.reposts.take(takeN))
                    if (posts.isEmpty()) {
                        onToggleHasNoMoreRepostedPosts()
                    } else {
                        val newPosts = posts.filter { post -> post.id != 0 }
                        myPostsUiState = myPostsUiState.copy(repostedPosts = myPostsUiState.repostedPosts.plus(newPosts))
                    }
                } else {
                    onGetBeforeRepostedPosts(myPostsUiState.self.reposts.take(takeN), myPostsUiState.repostedPosts.last().id)
                }
            }
        }

        private fun onGetBeforeRepostedPosts(
            repostedList: List<Int>,
            lastRepostedPostID: Int,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                // 取得したい塊の先頭の場所のIndexを取得
                val index = repostedList.indexOf(lastRepostedPostID) + 1
                // 取得したいPostをtakeN個だけ取得
                val posts = postRepository.onGetPosts(repostedList.drop(index).take(takeN))
                if (posts.isEmpty()) {
                    onToggleHasNoMoreRepostedPosts()
                } else {
                    val newPosts = posts.filter { post -> post.id != 0 }
                    myPostsUiState = myPostsUiState.copy(repostedPosts = myPostsUiState.repostedPosts.plus(newPosts))
                }
            }
        }

        private fun onGetPosts() {
            viewModelScope.launch(Dispatchers.IO) {
                if (myPostsUiState.posts.isEmpty()) {
                    val posts = postRepository.onGetPostsOfUser(myPostsUiState.self.userID)
                    myPostsUiState = myPostsUiState.copy(posts = posts)
                } else {
                    onGetBeforePosts(myPostsUiState.posts.last().createAt)
                }
            }
        }

        private fun onSetSelf(self: User) {
            myPostsUiState = myPostsUiState.copy(self = self)
        }

        private fun onGetBeforePosts(createAt: LocalDateTime) {
            viewModelScope.launch(Dispatchers.IO) {
                val posts = postRepository.onGetBeforePostsOfUser(userID = myPostsUiState.self.userID, updateAt = createAt)
                if (posts.isEmpty()) onToggleHasNoMorePosts()
                myPostsUiState = myPostsUiState.copy(posts = myPostsUiState.posts.plus(posts))
            }
        }

        private fun onToggleHasNoMorePosts() {
            myPostsUiState = myPostsUiState.copy(hasNoMorePosts = !myPostsUiState.hasNoMorePosts)
        }

        private fun onToggleHasNoMoreLikedPosts() {
            myPostsUiState = myPostsUiState.copy(hasNoMoreLikedPosts = !myPostsUiState.hasNoMoreLikedPosts)
        }

        private fun onToggleHasNoMoreRepostedPosts() {
            myPostsUiState = myPostsUiState.copy(hasNoMoreRepostedPosts = !myPostsUiState.hasNoMoreRepostedPosts)
        }

        private fun onUpdateSelectedTabIndex(index: Int) {
            myPostsUiState = myPostsUiState.copy(selectedTabIndex = index)
        }
    }
