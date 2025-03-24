package com.segnities007.seg.ui.screens.hub.setting.my_posts

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

@HiltViewModel
class MyPostsViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
    ) : ViewModel() {
        private val takeN = 7

        var myPostsState by mutableStateOf(MyPostsState())
            private set

        fun onGetMyPostsUiAction(): MyPostsAction =
            MyPostsAction(
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
            myPostsState = myPostsState.copy(posts = myPostsState.posts.minus(post))
        }

        private fun onInit() {
            if (myPostsState.self.likes.isEmpty()) {
                onToggleHasNoMoreLikedPosts()
            }
            if (myPostsState.self.reposts.isEmpty()) {
                onToggleHasNoMoreRepostedPosts()
            }
            if (myPostsState.self.posts.isEmpty()) {
                onToggleHasNoMorePosts()
            }
        }

        private fun onProcessOfEngagementAction(newPost: Post) {
            val newPosts =
                myPostsState.posts.map { post ->
                    if (newPost.id == post.id) newPost else post
                }

            val newLikedPosts =
                myPostsState.likedPosts.map { post ->
                    if (newPost.id == post.id) newPost else post
                }

            val newRepostedPosts =
                myPostsState.repostedPosts.map { post ->
                    if (newPost.id == post.id) newPost else post
                }

            myPostsState = myPostsState.copy(posts = newPosts, likedPosts = newLikedPosts, repostedPosts = newRepostedPosts)
        }

        private fun onGetLikedPosts() {
            viewModelScope.launch(Dispatchers.IO) {
                if (myPostsState.likedPosts.isEmpty()) {
                    // using reversing for sorted by descending order
                    val posts =
                        postRepository.onGetPosts(
                            myPostsState.self.likes
                                .reversed()
                                .take(takeN),
                        )
                    if (posts.isEmpty()) {
                        onToggleHasNoMoreLikedPosts()
                    } else {
                        val newPosts = posts.filter { post -> post.id != 0 }
                        myPostsState = myPostsState.copy(likedPosts = myPostsState.likedPosts.plus(newPosts))
                    }
                } else {
                    // using reversing for sorted by descending order
                    onGetBeforeLikedPosts(
                        myPostsState.self.likes
                            .reversed()
                            .take(takeN),
                        myPostsState.likedPosts.last().id,
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
                    myPostsState = myPostsState.copy(likedPosts = myPostsState.likedPosts.plus(newPosts))
                }
            }
        }

        private fun onGetRepostedPosts() {
            viewModelScope.launch(Dispatchers.IO) {
                if (myPostsState.repostedPosts.isEmpty()) {
                    // using reversing for sorted by descending order
                    val posts =
                        postRepository.onGetPosts(
                            myPostsState.self.reposts
                                .reversed()
                                .take(takeN),
                        )
                    if (posts.isEmpty()) {
                        onToggleHasNoMoreRepostedPosts()
                    } else {
                        val newPosts = posts.filter { post -> post.id != 0 }
                        myPostsState = myPostsState.copy(repostedPosts = myPostsState.repostedPosts.plus(newPosts))
                    }
                } else {
                    // using reversing for sorted by descending order
                    onGetBeforeRepostedPosts(
                        myPostsState.self.reposts
                            .reversed()
                            .take(takeN),
                        myPostsState.repostedPosts.last().id,
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
                    myPostsState = myPostsState.copy(repostedPosts = myPostsState.repostedPosts.plus(newPosts))
                }
            }
        }

        private fun onGetPosts() {
            viewModelScope.launch(Dispatchers.IO) {
                if (myPostsState.posts.isEmpty()) {
                    // using reversing for sorted by descending order
                    val posts =
                        postRepository.onGetPosts(
                            myPostsState.self.posts
                                .reversed()
                                .take(takeN),
                        )
                    if (posts.isEmpty()) {
                        onToggleHasNoMorePosts()
                    } else {
                        val newPosts = posts.filter { post -> post.id != 0 }
                        myPostsState = myPostsState.copy(posts = myPostsState.posts.plus(newPosts))
                    }
                } else {
                    // using reversing for sorted by descending order
                    onGetBeforePosts(myPostsState.self.posts.reversed(), myPostsState.posts.last().id)
                }
            }
        }

        private fun onSetSelf(self: User) {
            myPostsState = myPostsState.copy(self = self)
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
                    myPostsState = myPostsState.copy(posts = myPostsState.posts.plus(newPosts))
                }
            }
        }

        private fun onToggleHasNoMorePosts() {
            myPostsState = myPostsState.copy(hasNoMorePosts = !myPostsState.hasNoMorePosts)
        }

        private fun onToggleHasNoMoreLikedPosts() {
            myPostsState = myPostsState.copy(hasNoMoreLikedPosts = !myPostsState.hasNoMoreLikedPosts)
        }

        private fun onToggleHasNoMoreRepostedPosts() {
            myPostsState = myPostsState.copy(hasNoMoreRepostedPosts = !myPostsState.hasNoMoreRepostedPosts)
        }

        private fun onUpdateSelectedTabIndex(index: Int) {
            myPostsState = myPostsState.copy(selectedTabIndex = index)
        }
    }
