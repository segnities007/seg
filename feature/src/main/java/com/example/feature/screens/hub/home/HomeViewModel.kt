package com.example.feature.screens.hub.home

import androidx.compose.foundation.lazy.LazyListState
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
        val lazyListStateOfNormal = LazyListState()
        val lazyListStateOfHaiku = LazyListState()
        val lazyListStateOfTanka = LazyListState()

        fun onHomeAction(action: HomeAction) {
            when (action) {
                HomeAction.ChangeIsAllPostsFetched -> {
                    onChangeIsAllPostsFetched()
                }

                HomeAction.ChangeIsAllHaikusFetched -> {
                    onChangeIsAllHaikusFetched()
                }

                is HomeAction.GetBeforeNewPosts -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val posts = postRepository.onGetBeforePosts(action.updatedAt)
                        if (posts.isEmpty()) onChangeIsAllPostsFetched()
                        homeState = homeState.copy(posts = homeState.posts.plus(posts))
                    }
                }

                HomeAction.GetNewPosts -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val posts = postRepository.onGetNewPosts()
                        homeState = homeState.copy(posts = posts)
                    }
                }

                is HomeAction.ChangeEngagementOfPost -> {
                    val newPosts =
                        homeState.posts.map { post ->
                            if (action.newPost.id == post.id) action.newPost else post
                        }

                    homeState = homeState.copy(posts = newPosts)
                }

                is HomeAction.ChangeEngagementOfHaiku -> {
                    val newHaikus =
                        homeState.haikus.map { haiku ->
                            if (action.newHaiku.id == haiku.id) action.newHaiku else haiku
                        }
                    homeState = homeState.copy(haikus = newHaikus)
                }

                is HomeAction.UpdateCurrentGenre -> {
                    homeState = homeState.copy(currentGenre = action.newGenre)
                }

                is HomeAction.GetBeforeNewHaikus -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val haikus = postRepository.onGetBeforeHaikus(action.updatedAt)
                        if (haikus.isEmpty()) onChangeIsAllHaikusFetched()
                        homeState = homeState.copy(haikus = homeState.haikus.plus(haikus))
                    }
                }

                HomeAction.GetNewHaikus -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val haikus = postRepository.onGetNewHaikus()
                        homeState = homeState.copy(haikus = haikus)
                    }
                }

                is HomeAction.ChangeEngagementOfTanka -> TODO()
                HomeAction.ChangeIsAllTankasFetched -> TODO()
                is HomeAction.GetBeforeNewTankas -> TODO()
                HomeAction.GetNewTankas -> TODO()
            }
        }

        private fun onChangeIsAllPostsFetched() {
            homeState = homeState.copy(isAllPostsFetched = !homeState.isAllPostsFetched)
        }

        private fun onChangeIsAllHaikusFetched() {
            homeState = homeState.copy(isAllHaikusFetched = !homeState.isAllHaikusFetched)
        }
    }
