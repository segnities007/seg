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
    val onChangeHasNoMorePosts: () -> Unit,
    val onChangeHasNoMoreLikedPosts: () -> Unit,
    val onChangeHasNoMoreRepostedPosts: () -> Unit,
    val onUpdateSelectedTabIndex: (index: Int) -> Unit,
    val onInit: () -> Unit,
    val onGetSelf: (self: User) -> Unit,
    val onGetPosts: () -> Unit,
    val onGetBeforePosts: (updatedAt: LocalDateTime) -> Unit,
    val onGetLikedPosts: (likedList: List<Int>) -> Unit,
    val onGetBeforeLikedPosts: (likedList: List<Int>, lastLikedPostID: Int) -> Unit,
    val onGetRepostedPosts: (repostedList: List<Int>) -> Unit,
    val onGetBeforeRepostedPosts: (repostedList: List<Int>, lastRepostedPostID: Int) -> Unit,
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
                onGetSelf = this::onGetSelf,
                onChangeHasNoMorePosts = this::onChangeHasNoMorePosts,
                onChangeHasNoMoreLikedPosts = this::onChangeHasNoMoreLikedPosts,
                onChangeHasNoMoreRepostedPosts = this::onChangeHasNoMoreRepostedPosts,
                onUpdateSelectedTabIndex = this::onUpdateSelectedTabIndex,
                onGetPosts = this::onGetPosts,
                onGetLikedPosts = this::onGetLikedPosts,
                onGetBeforeLikedPosts = this::onGetBeforeLikedPosts,
                onGetRepostedPosts = this::onGetRepostedPosts,
                onGetBeforeRepostedPosts = this::onGetBeforeRepostedPosts,
                onGetBeforePosts = this::onGetBeforePosts,
                onInit = this::onInit,
                onProcessOfEngagementAction = this::onProcessOfEngagementAction,
            )

        private fun onProcessOfEngagementAction(newPost: Post) {
            onUpdatePosts(newPost)
        }

        private fun onInit() {
            viewModelScope.launch(Dispatchers.IO) {
                val likedPosts: List<Post> =
                    if (myPostsUiState.self.likes.isNotEmpty()) {
                        postRepository.onGetPosts(
                            myPostsUiState.self.likes.take(takeN),
                        )
                    } else {
                        listOf()
                    }
                val repostedPosts: List<Post> =
                    if (myPostsUiState.self.reposts.isNotEmpty()) {
                        postRepository.onGetPosts(
                            myPostsUiState.self.reposts,
                        )
                    } else {
                        listOf()
                    }
                val posts: List<Post> = postRepository.onGetPostsOfUser(myPostsUiState.self.userID)
                myPostsUiState = myPostsUiState.copy(posts = posts, likedPosts = likedPosts, repostedPosts = repostedPosts)
            }
        }

        private fun onGetLikedPosts(likedList: List<Int>) {
            viewModelScope.launch(Dispatchers.IO) {
                val posts = postRepository.onGetPosts(likedList.take(takeN))
                if (posts.isEmpty()) {
                    onChangeHasNoMoreLikedPosts()
                } else {
                    val newPosts = posts.filter { post -> post.id != 0 }
                    myPostsUiState = myPostsUiState.copy(likedPosts = myPostsUiState.likedPosts.plus(newPosts))
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
                    onChangeHasNoMoreLikedPosts()
                } else {
                    val newPosts = posts.filter { post -> post.id != 0 }
                    myPostsUiState = myPostsUiState.copy(likedPosts = myPostsUiState.likedPosts.plus(newPosts))
                }
            }
        }

        private fun onGetRepostedPosts(repostedList: List<Int>) {
            viewModelScope.launch(Dispatchers.IO) {
                val posts = postRepository.onGetPosts(repostedList)
                if (posts.isEmpty()) {
                    onChangeHasNoMoreRepostedPosts()
                } else {
                    val newPosts = posts.filter { post -> post.id != 0 }
                    myPostsUiState = myPostsUiState.copy(repostedPosts = myPostsUiState.repostedPosts.plus(newPosts))
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
                    onChangeHasNoMoreRepostedPosts()
                } else {
                    val newPosts = posts.filter { post -> post.id != 0 }
                    myPostsUiState = myPostsUiState.copy(repostedPosts = myPostsUiState.repostedPosts.plus(newPosts))
                }
            }
        }

        private fun onUpdateSelectedTabIndex(index: Int) {
            myPostsUiState = myPostsUiState.copy(selectedTabIndex = index)
        }

        private fun onGetSelf(self: User) {
            myPostsUiState = myPostsUiState.copy(self = self)
        }

        private fun onGetPosts() {
            viewModelScope.launch(Dispatchers.IO) {
                val posts = postRepository.onGetPostsOfUser(myPostsUiState.self.userID)
                myPostsUiState = myPostsUiState.copy(posts = posts)
            }
        }

        private fun onGetBeforePosts(createAt: LocalDateTime) {
            viewModelScope.launch(Dispatchers.IO) {
                val posts = postRepository.onGetBeforePostsOfUser(userID = myPostsUiState.self.userID, updateAt = createAt)
                if (posts.isEmpty()) onChangeHasNoMorePosts()
                myPostsUiState = myPostsUiState.copy(posts = myPostsUiState.posts.plus(posts))
            }
        }

        private fun onUpdatePosts(newPost: Post) {
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

        private fun onChangeHasNoMorePosts() {
            myPostsUiState = myPostsUiState.copy(hasNoMorePosts = !myPostsUiState.hasNoMorePosts)
        }

        private fun onChangeHasNoMoreLikedPosts() {
            myPostsUiState = myPostsUiState.copy(hasNoMoreLikedPosts = !myPostsUiState.hasNoMoreLikedPosts)
        }

        private fun onChangeHasNoMoreRepostedPosts() {
            myPostsUiState = myPostsUiState.copy(hasNoMoreRepostedPosts = !myPostsUiState.hasNoMoreRepostedPosts)
        }
    }
