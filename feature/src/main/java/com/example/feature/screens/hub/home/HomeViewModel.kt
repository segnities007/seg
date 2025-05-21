package com.example.feature.screens.hub.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.post.Genre
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
                HomeAction.ChangeIsAllPostsFetched -> {
                    onChangeIsAllFetched(Genre.NORMAL)
                }

                HomeAction.ChangeIsAllHaikusFetched -> {
                    onChangeIsAllFetched(Genre.HAIKU)
                }

                HomeAction.ChangeIsAllTankasFetched -> {
                    onChangeIsAllFetched(Genre.TANKA)
                }

                HomeAction.GetNewPosts -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val posts = postRepository.onGetNewPosts()
                        homeState = homeState.copy(posts = posts)
                    }
                }

                HomeAction.GetNewHaikus -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val haikus = postRepository.onGetNewHaikus()
                        homeState = homeState.copy(haikus = haikus)
                    }
                }

                HomeAction.GetNewTankas -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val tankas = postRepository.onGetNewTankas()
                        homeState = homeState.copy(tankas = tankas)
                    }
                }

                is HomeAction.GetBeforeNewPosts -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val posts = postRepository.onGetBeforePosts(action.updatedAt)
                        if (posts.isEmpty()) onChangeIsAllFetched(Genre.NORMAL)
                        homeState = homeState.copy(posts = homeState.posts.plus(posts))
                    }
                }

                is HomeAction.GetBeforeNewHaikus -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val haikus = postRepository.onGetBeforeHaikus(action.updatedAt)
                        if (haikus.isEmpty()) onChangeIsAllFetched(Genre.HAIKU)
                        homeState = homeState.copy(haikus = homeState.haikus.plus(haikus))
                    }
                }

                is HomeAction.GetBeforeNewTankas -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val tankas = postRepository.onGetBeforeTankas(action.updatedAt)
                        if (tankas.isEmpty()) onChangeIsAllFetched(Genre.TANKA)
                        homeState = homeState.copy(tankas = homeState.tankas.plus(tankas))
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

                is HomeAction.ChangeEngagementOfTanka -> {
                    val newTankas =
                        homeState.tankas.map { tanka ->
                            if (action.newTanka.id == tanka.id) action.newTanka else tanka
                        }
                    homeState = homeState.copy(tankas = newTankas)
                }

                is HomeAction.UpdateCurrentGenre -> {
                    homeState = homeState.copy(currentGenre = action.newGenre)
                }
            }
        }

        private fun onChangeIsAllFetched(genre: Genre) {
            homeState =
                when (genre) {
                    Genre.HAIKU -> homeState.copy(isAllHaikusFetched = !homeState.isAllHaikusFetched)
                    Genre.TANKA -> homeState.copy(isAllTankasFetched = !homeState.isAllTankasFetched)
                    else -> homeState.copy(isAllPostsFetched = !homeState.isAllPostsFetched)
                }
        }
    }
