package com.example.feature.screens.hub.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.post.Genre
import com.example.domain.repository.PostRepository
import com.example.feature.model.UiStatus
import com.example.feature.screens.hub.HubAction
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
            homeState = homeState.copy(uiStatus = UiStatus.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val posts = postRepository.onGetNewPosts(action.genre)
                    homeState =
                        when (action.genre) {
                            Genre.HAIKU -> homeState.copy(haikus = posts)
                            Genre.TANKA -> homeState.copy(tankas = posts)
                            Genre.KATAUTA -> homeState.copy(katautas = posts)
                            Genre.SEDOUKA -> homeState.copy(sedoukas = posts)
                            else -> homeState.copy(posts = posts)
                        }.copy(uiStatus = UiStatus.Success)
                } catch (e: Exception) {
                    homeState =
                        homeState.copy(uiStatus = UiStatus.Error("ポストの取得に失敗しました。"))
                    action.onHubAction(HubAction.OpenSnackBar((homeState.uiStatus as UiStatus.Error).message))
                } finally {
                    homeState = homeState.copy(uiStatus = UiStatus.Initial)
                }
            }
        }

        private fun getBeforeNewPosts(action: HomeAction.GetBeforeNewPosts) {
            homeState = homeState.copy(uiStatus = UiStatus.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val posts =
                        postRepository.onGetBeforePosts(
                            afterPostCreateAt = action.updatedAt,
                            genre = action.genre,
                        )
                    if (posts.isEmpty()) homeState = homeReducer(action, homeState)
                    homeState =
                        when (action.genre) {
                            Genre.HAIKU -> homeState.copy(haikus = homeState.haikus.plus(posts))
                            Genre.TANKA -> homeState.copy(tankas = homeState.tankas.plus(posts))
                            Genre.KATAUTA -> homeState.copy(katautas = homeState.katautas.plus(posts))
                            Genre.SEDOUKA -> homeState.copy(sedoukas = homeState.sedoukas.plus(posts))
                            else -> homeState.copy(posts = homeState.posts.plus(posts))
                        }.copy(uiStatus = UiStatus.Success)
                } catch (e: Exception) {
                    homeState =
                        homeState.copy(uiStatus = UiStatus.Error("ポストの取得に失敗しました。"))
                    action.onHubAction(HubAction.OpenSnackBar((homeState.uiStatus as UiStatus.Error).message))
                } finally {
                    homeState = homeState.copy(uiStatus = UiStatus.Initial)
                }
            }
        }
    }
