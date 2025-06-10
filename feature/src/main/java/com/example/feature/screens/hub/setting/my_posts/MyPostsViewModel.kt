package com.example.feature.screens.hub.setting.my_posts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.PostRepository
import com.example.feature.model.UiStatus
import com.example.feature.screens.hub.HubAction
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
                is MyPostsAction.GetLikedPosts -> getLikedPosts(action)
                is MyPostsAction.GetPosts -> getPosts(action)
                is MyPostsAction.GetRepostedPosts -> getRepostedPosts(action)
                MyPostsAction.Init,
                is MyPostsAction.ProcessOfEngagement,
                is MyPostsAction.SetSelf,
                is MyPostsAction.RemovePostFromPosts,
                is MyPostsAction.UpdateSelectedTabIndex,
                -> myPostsState = myPostsReducer(myPostsState, action)
            }
        }

        private fun getLikedPosts(action: MyPostsAction.GetLikedPosts) {
            myPostsState = myPostsState.copy(uiStatus = UiStatus.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    if (myPostsState.likedPosts.isEmpty()) {
                        val posts =
                            postRepository.onGetPosts(
                                myPostsState.self.likes
                                    .reversed()
                                    .take(takeN),
                            )
                        if (posts.isEmpty()) {
                            myPostsReducer(state = myPostsState, action = action)
                        } else {
                            val newPosts = posts.filter { post -> post.id != 0 }
                            myPostsState =
                                myPostsState.copy(likedPosts = myPostsState.likedPosts.plus(newPosts))
                        }
                    } else {
                        onGetBeforeLikedPosts(
                            myPostsState.self.likes
                                .reversed()
                                .take(takeN),
                            myPostsState.likedPosts.last().id,
                            action,
                        )
                    }
                    myPostsState = myPostsState.copy(uiStatus = UiStatus.Success)
                } catch (e: Exception) {
                    myPostsState =
                        myPostsState.copy(uiStatus = UiStatus.Error("エラーが発生しました。"))
                    action.onHubAction(HubAction.OpenSnackBar((myPostsState.uiStatus as UiStatus.Error).message))
                } finally {
                    myPostsState = myPostsState.copy(uiStatus = UiStatus.Initial)
                }
            }
        }

        private fun getPosts(action: MyPostsAction.GetPosts) {
            myPostsState = myPostsState.copy(uiStatus = UiStatus.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    if (myPostsState.posts.isEmpty()) {
                        val posts =
                            postRepository.onGetPosts(
                                myPostsState.self.posts
                                    .reversed()
                                    .take(takeN),
                            )
                        if (posts.isEmpty()) {
                            myPostsState = myPostsReducer(state = myPostsState, action = action)
                        } else {
                            val newPosts = posts.filter { post -> post.id != 0 }
                            myPostsState =
                                myPostsState.copy(posts = myPostsState.posts.plus(newPosts))
                        }
                    } else {
                        onGetBeforePosts(
                            myPostsState.self.posts.reversed(),
                            myPostsState.posts.last().id,
                            action,
                        )
                    }
                    myPostsState = myPostsState.copy(uiStatus = UiStatus.Success)
                } catch (e: Exception) {
                    myPostsState =
                        myPostsState.copy(uiStatus = UiStatus.Error("エラーが発生しました。"))
                    action.onHubAction(HubAction.OpenSnackBar((myPostsState.uiStatus as UiStatus.Error).message))
                } finally {
                    myPostsState = myPostsState.copy(uiStatus = UiStatus.Initial)
                }
            }
        }

        private fun getRepostedPosts(action: MyPostsAction.GetRepostedPosts) {
            myPostsState = myPostsState.copy(uiStatus = UiStatus.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    if (myPostsState.repostedPosts.isEmpty()) {
                        val posts =
                            postRepository.onGetPosts(
                                myPostsState.self.reposts
                                    .reversed()
                                    .take(takeN),
                            )
                        if (posts.isEmpty()) {
                            myPostsState = myPostsReducer(state = myPostsState, action = action)
                        } else {
                            val newPosts = posts.filter { post -> post.id != 0 }
                            myPostsState =
                                myPostsState.copy(
                                    repostedPosts = myPostsState.repostedPosts.plus(newPosts),
                                )
                        }
                    } else {
                        onGetBeforeRepostedPosts(
                            myPostsState.self.reposts
                                .reversed()
                                .take(takeN),
                            myPostsState.repostedPosts.last().id,
                            action,
                        )
                    }
                    myPostsState = myPostsState.copy(uiStatus = UiStatus.Success)
                } catch (e: Exception) {
                    myPostsState =
                        myPostsState.copy(uiStatus = UiStatus.Error("エラーが発生しました。"))
                    action.onHubAction(HubAction.OpenSnackBar((myPostsState.uiStatus as UiStatus.Error).message))
                } finally {
                    myPostsState = myPostsState.copy(uiStatus = UiStatus.Initial)
                }
            }
        }

        private fun onGetBeforeLikedPosts(
            likedList: List<Int>,
            lastLikedPostID: Int,
            action: MyPostsAction,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                // 取得したい塊の先頭の場所のIndexを取得
                val index = likedList.indexOf(lastLikedPostID) + 1
                // 取得したいPostをtakeN個だけ取得
                val posts = postRepository.onGetPosts(likedList.drop(index).take(takeN))
                if (posts.isEmpty()) {
                    myPostsState = myPostsReducer(myPostsState, action)
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
            action: MyPostsAction,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                // 取得したい塊の先頭の場所のIndexを取得
                val index = repostedList.indexOf(lastRepostedPostID) + 1
                // 取得したいPostをtakeN個だけ取得
                val posts = postRepository.onGetPosts(repostedList.drop(index).take(takeN))
                if (posts.isEmpty()) {
                    myPostsState = myPostsReducer(myPostsState, action)
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
            action: MyPostsAction,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                // 取得したい塊の先頭の場所のIndexを取得
                val index = postIDs.indexOf(lastPostID) + 1
                // 取得したいPostをtakeN個だけ取得
                val posts = postRepository.onGetPosts(postIDs.drop(index).take(takeN))
                if (posts.isEmpty()) {
                    myPostsState = myPostsReducer(myPostsState, action)
                } else {
                    val newPosts = posts.filter { post -> post.id != 0 }
                    myPostsState = myPostsState.copy(posts = myPostsState.posts.plus(newPosts))
                }
            }
        }
    }
