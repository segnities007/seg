package com.segnities007.seg.ui.components.card.postcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.home.HomeAction
import com.segnities007.seg.ui.screens.hub.setting.my_posts.MyPostsAction
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

                    if (action.hubState.user.likes
                            .contains(action.post.id)
                    ) {
                        newPost = action.post.copy(likeCount = action.post.likeCount - 1)
                        viewModelScope.launch(Dispatchers.IO) {
                            postRepository.onUnLike(post = newPost, user = action.hubState.user)
                        }
                        action.onHubAction(HubAction.RemovePostIDFromMyLikes(action.post.id))
                    } else {
                        newPost = action.post.copy(likeCount = action.post.likeCount + 1)
                        viewModelScope.launch(Dispatchers.IO) {
                            postRepository.onLike(post = newPost, user = action.hubState.user)
                        }
                        action.onHubAction(HubAction.AddPostIDToMyLikes(action.post.id))
                    }

                    action.onProcessOfEngagementAction(newPost)
                }

                is PostCardAction.ClickRepostIcon -> {
                    val newPost: Post
                    val hubState = action.hubState
                    val post = action.post

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
                        action.onHubAction(HubAction.RemovePostIDFromReposts(post.id))
                    } else {
                        action.onHubAction(HubAction.AddPostIDFromReposts(post.id))
                    }
                    action.onProcessOfEngagementAction(newPost)
                }

                is PostCardAction.DeletePost -> {
                    val hubState = action.hubState
                    val post = action.post

                    val newPostsOfSelf = hubState.user.posts.minus(post.id)
                    action.onMyPostsAction(MyPostsAction.RemovePostFromPosts(post))
                    val newSelf = hubState.user.copy(posts = newPostsOfSelf)
                    action.onHubAction(HubAction.SetSelf(newSelf))
                    viewModelScope.launch(Dispatchers.IO) {
                        postRepository.onDeletePost(post)
                        action.onHomeAction(HomeAction.GetNewPosts)
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
