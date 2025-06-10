package com.example.feature.screens.hub.post

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.post.Genre
import com.example.domain.presentation.navigation.NavigationHubRoute
import com.example.domain.repository.PostRepository
import com.example.feature.model.UiStatus
import com.example.feature.screens.hub.HubAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
    ) : ViewModel() {
        var postState by mutableStateOf(PostState())
            private set

        fun onPostAction(action: PostAction) {
            when (action) {
                is PostAction.CreatePost -> createPost(action)
                is PostAction.CreateComment -> createComment(action)
                is PostAction.UpdateGenre,
                is PostAction.UpdateIsLoading,
                is PostAction.UpdateInputText,
                -> postReducer(postState, action)
            }
        }

        private fun createPost(action: PostAction.CreatePost) {
            when (postState.genre) {
                Genre.HAIKU -> if (postState.inputText.length != 17) return
                Genre.TANKA -> if (postState.inputText.length != 31) return
                Genre.KATAUTA -> if (postState.inputText.length != 19) return
                Genre.SEDOUKA -> if (postState.inputText.length != 38) return
                else -> if (postState.inputText.length > 100) return
            }
            createNormalPost(
                action = action,
            )
        }

        private fun createComment(action: PostAction.CreateComment) {
            postState = postState.copy(uiStatus = UiStatus.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val result =
                        postRepository.onCreateComment(
                            description = postState.inputText,
                            self = action.hubState.self,
                            commentedPost = action.hubState.comment,
                        )
                    if (result) {
                        action.onHubAction(HubAction.GetUser)
                        val updatedCommentedPost =
                            postRepository.onGetPost(action.hubState.comment.id)
                        action.onHubAction(HubAction.SetComment(updatedCommentedPost))
                        viewModelScope.launch(Dispatchers.Main) {
                            action.onNavigate(NavigationHubRoute.Home)
                        }
                    }
                    postState = postState.copy(uiStatus = UiStatus.Success)
                } catch (e: Exception) {
                    postState =
                        postState.copy(uiStatus = UiStatus.Error("コメントの作成に失敗しました。"))
                    action.onHubAction(HubAction.OpenSnackBar((postState.uiStatus as UiStatus.Error).message))
                } finally {
                    postState = postState.copy(uiStatus = UiStatus.Initial)
                }
            }
        }

        private fun createNormalPost(action: PostAction.CreatePost) {
            postState = postState.copy(uiStatus = UiStatus.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val result =
                        postRepository.onCreatePost(
                            description = postState.inputText,
                            user = action.user,
                            genre = postState.genre,
                        )
                    if (result) {
                        action.onUpdateSelf()
                        viewModelScope.launch(Dispatchers.Main) {
                            action.onNavigate(NavigationHubRoute.Home)
                        }
                    }
                } catch (e: Exception) {
                    postState =
                        postState.copy(uiStatus = UiStatus.Error("ポストの作成に失敗しました。"))
                    action.onHubAction(HubAction.OpenSnackBar((postState.uiStatus as UiStatus.Error).message))
                } finally {
                    postState = postState.copy(uiStatus = UiStatus.Initial)
                }
            }
        }
    }
