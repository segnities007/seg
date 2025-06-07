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
                is HomeAction.UpdateCurrentGenre,
                is HomeAction.ChangeEngagementOfPost,
                is HomeAction.ChangeIsAllPostsFetched,
                -> homeState = homeReducer(action, homeState)

                is HomeAction.GetNewPosts -> getNewPosts(action)
                is HomeAction.GetBeforeNewPosts -> getBeforeNewPosts(action)
            }
        }

        private fun getNewPosts(action: HomeAction.GetNewPosts) {
            viewModelScope.launch(Dispatchers.IO) {
                val posts = postRepository.onGetNewPosts(action.genre)
                homeState =
                    when (action.genre) {
                        Genre.HAIKU -> homeState.copy(haikus = posts)
                        Genre.TANKA -> homeState.copy(tankas = posts)
                        Genre.KATAUTA -> homeState.copy(katautas = posts)
                        Genre.SEDOUKA -> homeState.copy(sedoukas = posts)
                        else -> homeState.copy(posts = posts)
                    }
            }
        }

        private fun getBeforeNewPosts(action: HomeAction.GetBeforeNewPosts) {
            viewModelScope.launch(Dispatchers.IO) {
                val posts =
                    postRepository.onGetBeforePosts(
                        afterPostCreateAt = action.updatedAt,
                        genre = action.genre,
                    )
                if (posts.isEmpty()) homeReducer(action, homeState)
                homeState =
                    when (action.genre) {
                        Genre.HAIKU -> homeState.copy(haikus = homeState.haikus.plus(posts))
                        Genre.TANKA -> homeState.copy(tankas = homeState.tankas.plus(posts))
                        Genre.KATAUTA -> homeState.copy(katautas = homeState.katautas.plus(posts))
                        Genre.SEDOUKA -> homeState.copy(sedoukas = homeState.sedoukas.plus(posts))
                        else -> homeState.copy(posts = homeState.posts.plus(posts))
                    }
            }
        }
    }
