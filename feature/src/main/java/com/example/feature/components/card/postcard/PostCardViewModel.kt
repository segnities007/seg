package com.example.feature.components.card.postcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.post.Post
import com.example.domain.presentation.navigation.NavigationHubRoute
import com.example.domain.repository.PostRepository
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.home.HomeAction
import com.example.feature.screens.hub.setting.my_posts.MyPostsAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostCardViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
    ) : ViewModel() {
        fun onPostCardAction(action: PostCardAction) {
            when (action) {
                is PostCardAction.ClickPostCard -> {
                    action.onHubNavigate(NavigationHubRoute.Comment)
                }

                is PostCardAction.ClickAvatarIcon -> {
                    action.onHubNavigate(NavigationHubRoute.Account)
                }

                is PostCardAction.ClickLikeIcon -> {
                    val newPost: Post

                    if (action.hubState.self.likes
                            .contains(action.post.id)
                    ) {
                        newPost = action.post.copy(likeCount = action.post.likeCount - 1)
                        viewModelScope.launch(Dispatchers.IO) {
                            postRepository.onUnLike(post = newPost, user = action.hubState.self)
                        }
                        action.onHubAction(HubAction.RemovePostIDFromMyLikes(action.post.id))
                    } else {
                        newPost = action.post.copy(likeCount = action.post.likeCount + 1)
                        viewModelScope.launch(Dispatchers.IO) {
                            postRepository.onLike(post = newPost, user = action.hubState.self)
                        }
                        action.onHubAction(HubAction.AddPostIDToMyLikes(action.post.id))
                    }

                    action.onProcessOfEngagementAction(newPost)
                }

                is PostCardAction.ClickRepostIcon -> {
                    val newPost: Post
                    val hubState = action.hubState
                    val post = action.post

                    if (hubState.self.reposts.contains(post.id)) {
                        newPost = post.copy(repostCount = post.repostCount - 1)
                        viewModelScope.launch(Dispatchers.IO) {
                            postRepository.onUnRepost(post = newPost, user = hubState.self)
                        }
                    } else {
                        newPost = post.copy(repostCount = post.repostCount + 1)
                        viewModelScope.launch(Dispatchers.IO) {
                            postRepository.onRepost(post = newPost, user = hubState.self)
                        }
                    }

                    if (hubState.self.reposts.contains(post.id)) {
                        action.onHubAction(HubAction.RemovePostIDFromReposts(post.id))
                    } else {
                        action.onHubAction(HubAction.AddPostIDFromReposts(post.id))
                    }
                    action.onProcessOfEngagementAction(newPost)
                }

                is PostCardAction.DeletePost -> {
                    val hubState = action.hubState
                    val post = action.post

                    val newPostsOfSelf = hubState.self.posts.minus(post.id)
                    action.onMyPostsAction(MyPostsAction.RemovePostFromPosts(post))
                    val newSelf = hubState.self.copy(posts = newPostsOfSelf)
                    action.onHubAction(HubAction.SetSelf(newSelf))
                    viewModelScope.launch(Dispatchers.IO) {
                        postRepository.onDeletePost(post)
                        action.onHomeAction(HomeAction.GetNewPosts(post.genre))
                    }
                }

                is PostCardAction.IncrementViewCount -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        postRepository.onIncrementView(action.post)
                    }
                }
            }
        }
    }
