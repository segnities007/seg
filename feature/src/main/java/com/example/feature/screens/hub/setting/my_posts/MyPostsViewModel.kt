package com.example.feature.screens.hub.setting.my_posts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.PostRepository
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

        fun onMyPostsAction(action: MyPostsAction) {
            when (action) {
                MyPostsAction.GetLikedPosts -> {
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
                                myPostsState =
                                    myPostsState.copy(likedPosts = myPostsState.likedPosts.plus(newPosts))
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

                MyPostsAction.GetPosts -> {
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
                                myPostsState =
                                    myPostsState.copy(posts = myPostsState.posts.plus(newPosts))
                            }
                        } else {
                            // using reversing for sorted by descending order
                            onGetBeforePosts(
                                myPostsState.self.posts.reversed(),
                                myPostsState.posts.last().id,
                            )
                        }
                    }
                }

                MyPostsAction.GetRepostedPosts -> {
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
                                myPostsState =
                                    myPostsState.copy(
                                        repostedPosts = myPostsState.repostedPosts.plus(newPosts),
                                    )
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

                MyPostsAction.Init -> {
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

                is MyPostsAction.ProcessOfEngagement -> {
                    val newPost = action.newPost

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

                    myPostsState =
                        myPostsState.copy(
                            posts = newPosts,
                            likedPosts = newLikedPosts,
                            repostedPosts = newRepostedPosts,
                        )
                }

                is MyPostsAction.RemovePostFromPosts -> {
                    myPostsState = myPostsState.copy(posts = myPostsState.posts.minus(action.post))
                }

                is MyPostsAction.SetSelf -> {
                    myPostsState = myPostsState.copy(self = action.self)
                }

                is MyPostsAction.UpdateSelectedTabIndex -> {
                    myPostsState = myPostsState.copy(selectedTabIndex = action.index)
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
                    myPostsState =
                        myPostsState.copy(likedPosts = myPostsState.likedPosts.plus(newPosts))
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
                    myPostsState =
                        myPostsState.copy(repostedPosts = myPostsState.repostedPosts.plus(newPosts))
                }
            }
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
            myPostsState =
                myPostsState.copy(hasNoMoreRepostedPosts = !myPostsState.hasNoMoreRepostedPosts)
        }
    }
