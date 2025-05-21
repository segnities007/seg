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
                is HomeAction.ChangeIsAllPostsFetched -> {
                    onChangeIsAllFetched(action.genre)
                }

                is HomeAction.GetNewPosts -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val posts = postRepository.onGetNewPosts(action.genre)
                        homeState =
                            when (action.genre) {
                                Genre.HAIKU -> homeState.copy(haikus = posts)
                                Genre.TANKA -> homeState.copy(tankas = posts)
                                Genre.KATAUTA -> homeState.copy(katautas = posts)
                                else -> homeState.copy(posts = posts)
                            }
                    }
                }

                is HomeAction.GetBeforeNewPosts -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val posts =
                            postRepository.onGetBeforePosts(
                                afterPostCreateAt = action.updatedAt,
                                genre = action.genre,
                            )
                        if (posts.isEmpty()) onChangeIsAllFetched(action.genre)
                        homeState =
                            when (action.genre) {
                                Genre.HAIKU -> homeState.copy(haikus = homeState.haikus.plus(posts))
                                Genre.TANKA -> homeState.copy(tankas = homeState.tankas.plus(posts))
                                Genre.KATAUTA -> homeState.copy(katautas = homeState.katautas.plus(posts))
                                else -> homeState.copy(posts = homeState.posts.plus(posts))
                            }
                    }
                }

                is HomeAction.ChangeEngagementOfPost -> {
                    val newPosts =
                        when (action.newPost.genre) {
                            Genre.HAIKU ->
                                homeState.haikus.map { haiku ->
                                    if (haiku.id == action.newPost.id) action.newPost else haiku
                                }

                            Genre.TANKA ->
                                homeState.tankas.map { tanka ->
                                    if (tanka.id == action.newPost.id) action.newPost else tanka
                                }

                            Genre.KATAUTA ->
                                homeState.katautas.map { katauta ->
                                    if (katauta.id == action.newPost.id) action.newPost else katauta
                                }

                            else ->
                                homeState.posts.map { post ->
                                    if (post.id == action.newPost.id) action.newPost else post
                                }
                        }
                    homeState =
                        when (action.newPost.genre) {
                            Genre.TANKA -> homeState.copy(tankas = newPosts)
                            Genre.HAIKU -> homeState.copy(haikus = newPosts)
                            Genre.KATAUTA -> homeState.copy(katautas = newPosts)
                            else -> homeState.copy(posts = newPosts)
                        }
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
                    Genre.KATAUTA -> homeState.copy(isAllKatautasFetched = !homeState.isAllKatautasFetched)
                    else -> homeState.copy(isAllPostsFetched = !homeState.isAllPostsFetched)
                }
        }
    }
