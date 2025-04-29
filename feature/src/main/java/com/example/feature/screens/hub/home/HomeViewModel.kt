package com.example.feature.screens.hub.home

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
class HomeViewModel
@Inject
constructor(
    private val postRepository: PostRepository,
) : ViewModel() {
    var homeState by mutableStateOf(HomeState())
        private set

    fun onHomeAction(action: HomeAction) {
        when (action) {
            HomeAction.ChangeHasNoMorePost -> {
                onChangeHasNoMorePost()
            }

            is HomeAction.GetBeforeNewPosts -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val posts = postRepository.onGetBeforePosts(action.updatedAt)
                    if (posts.isEmpty()) onChangeHasNoMorePost()
                    homeState = homeState.copy(posts = homeState.posts.plus(posts))
                }
            }

            HomeAction.GetNewPosts -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val posts = postRepository.onGetNewPosts()
                    homeState = homeState.copy(posts = posts)
                }
            }

            is HomeAction.ProcessOfEngagement -> {
                val newPosts =
                    homeState.posts.map { post ->
                        if (action.newPost.id == post.id) action.newPost else post
                    }

                homeState = homeState.copy(posts = newPosts)
            }
        }
    }

    private fun onChangeHasNoMorePost() {
        homeState = homeState.copy(hasNoMorePost = !homeState.hasNoMorePost)
    }
}
