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
    val onRemovePostFromPosts: (post: Post) -> Unit,
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
                onRemovePostFromPosts = this::onRemovePostFromPosts,
            )

        private fun onRemovePostFromPosts(post: Post) {
            myPostsUiState = myPostsUiState.copy(posts = myPostsUiState.posts.minus(post))
        }

        private fun onInit() {
            if (myPostsUiState.self.likes.isEmpty()) {
                onToggleHasNoMoreLikedPosts()
            }
            if (myPostsUiState.self.reposts.isEmpty()) {
                onToggleHasNoMoreRepostedPosts()
            }
            if (myPostsUiState.self.posts.isEmpty()) {
                onToggleHasNoMorePosts()
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
                    // using reversing for sorted by descending order
                    val posts =
                        postRepository.onGetPosts(
                            myPostsUiState.self.likes
                                .reversed()
                                .take(takeN),
                        )
                    if (posts.isEmpty()) {
                        onToggleHasNoMoreLikedPosts()
                    } else {
                        val newPosts = posts.filter { post -> post.id != 0 }
                        myPostsUiState = myPostsUiState.copy(likedPosts = myPostsUiState.likedPosts.plus(newPosts))
                    }
                } else {
                    // using reversing for sorted by descending order
                    onGetBeforeLikedPosts(
                        myPostsUiState.self.likes
                            .reversed()
                            .take(takeN),
                        myPostsUiState.likedPosts.last().id,
                    )
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
                    // using reversing for sorted by descending order
                    val posts =
                        postRepository.onGetPosts(
                            myPostsUiState.self.reposts
                                .reversed()
                                .take(takeN),
                        )
                    if (posts.isEmpty()) {
                        onToggleHasNoMoreRepostedPosts()
                    } else {
                        val newPosts = posts.filter { post -> post.id != 0 }
                        myPostsUiState = myPostsUiState.copy(repostedPosts = myPostsUiState.repostedPosts.plus(newPosts))
                    }
                } else {
                    // using reversing for sorted by descending order
                    onGetBeforeRepostedPosts(
                        myPostsUiState.self.reposts
                            .reversed()
                            .take(takeN),
                        myPostsUiState.repostedPosts.last().id,
                    )
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
                    // using reversing for sorted by descending order
                    val posts =
                        postRepository.onGetPosts(
                            myPostsUiState.self.posts
                                .reversed()
                                .take(takeN),
                        )
                    if (posts.isEmpty()) {
                        onToggleHasNoMorePosts()
                    } else {
                        val newPosts = posts.filter { post -> post.id != 0 }
                        myPostsUiState = myPostsUiState.copy(posts = myPostsUiState.posts.plus(newPosts))
                    }
                } else {
                    // using reversing for sorted by descending order
                    onGetBeforePosts(myPostsUiState.self.posts.reversed(), myPostsUiState.posts.last().id)
                }
            }
        }

        private fun onSetSelf(self: User) {
            myPostsUiState = myPostsUiState.copy(self = self)
        }

        private fun onGetBeforePosts(
            postIDs: List<Int>,
            lastPostID: Int,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                // 取得したい塊の先頭の場所のIndexを取得
                val index = postIDs.indexOf(lastPostID) + 1
                // 取得したいPostをtakeN個だけ取得
                val posts = postRepository.onGetPosts(postIDs.drop(index).take(takeN))
                if (posts.isEmpty()) {
                    onToggleHasNoMorePosts()
                } else {
                    val newPosts = posts.filter { post -> post.id != 0 }
                    myPostsUiState = myPostsUiState.copy(posts = myPostsUiState.posts.plus(newPosts))
                }
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
