package com.example.feature.screens.hub.post

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.presentation.navigation.NavigationHubRoute
import com.example.domain.repository.PostRepository
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
                is PostAction.CreatePost -> {
                    val description = postState.inputText
                    onUpdateIsLoading(true)
                    viewModelScope.launch(Dispatchers.IO) {
                        val result =
                            postRepository.onCreatePost(
                                description = description,
                                user = action.user,
                                genre = postState.genre,
                            )
                        if (result) {
                            action.onUpdateSelf()
                            viewModelScope.launch(Dispatchers.Main) {
                                action.onNavigate(NavigationHubRoute.Home)
                            }
                        }
                        onUpdateIsLoading(false)
                    }
                }

                is PostAction.UpdateInputText -> {
                    postState = postState.copy(inputText = action.newInputText)
                }

                is PostAction.UpdateIsLoading -> {
                    onUpdateIsLoading(action.isLoading)
                }

                is PostAction.CreateComment -> {
                    val description = postState.inputText
                    onUpdateIsLoading(true)
                    viewModelScope.launch(Dispatchers.IO) {
                        val result =
                            postRepository.onCreateComment(
                                description = description,
                                self = action.hubState.user,
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
                        onUpdateIsLoading(false)
                    }
                }
            }
        }

        private fun onUpdateIsLoading(isLoading: Boolean) {
            postState = postState.copy(isLoading = isLoading)
        }
    }
