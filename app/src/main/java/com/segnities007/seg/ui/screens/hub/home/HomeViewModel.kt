package com.segnities007.seg.ui.screens.hub.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
    ) : ViewModel() {
        var homeState by mutableStateOf(HomeState())
            private set

        fun onGetHomeUiAction(): HomeAction =
            HomeAction(
                onGetNewPosts = this::onGetNewPosts,
                onChangeHasNoMorePost = this::onChangeHasNoMorePost,
                onGetBeforeNewPosts = this::onGetBeforeNewPosts,
                onProcessOfEngagementAction = this::onProcessOfEngagementAction,
            )

        private fun onProcessOfEngagementAction(newPost: Post) {
            onUpdatePosts(newPost)
        }

        private fun onGetNewPosts() {
            viewModelScope.launch(Dispatchers.IO) {
                val posts = postRepository.onGetNewPosts()
                homeState = homeState.copy(posts = posts)
            }
        }

        private fun onGetBeforeNewPosts(updatedAt: java.time.LocalDateTime) {
            viewModelScope.launch(Dispatchers.IO) {
                val posts = postRepository.onGetBeforePosts(updatedAt)
                if (posts.isEmpty()) onChangeHasNoMorePost()
                homeState = homeState.copy(posts = homeState.posts.plus(posts))
            }
        }

        private fun onChangeHasNoMorePost() {
            homeState = homeState.copy(hasNoMorePost = !homeState.hasNoMorePost)
        }

        private fun onUpdatePosts(newPost: Post) {
            val newPosts =
                homeState.posts.map { post ->
                    if (newPost.id == post.id) newPost else post
                }

            homeState = homeState.copy(posts = newPosts)
        }
    }
