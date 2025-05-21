package com.example.feature.screens.hub.post

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.post.Genre
import com.example.domain.model.user.User
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
                    when (postState.genre) {
                        Genre.HAIKU -> if (postState.inputText.length != 17) return
                        Genre.TANKA -> if (postState.inputText.length != 31) return
                        Genre.KATAUTA -> if (postState.inputText.length != 19) return
                        else -> if (postState.inputText.length >= 100) return
                    }
                    createNormalPost(
                        user = action.user,
                        onUpdateSelf = action.onUpdateSelf,
                        onNavigate = action.onNavigate,
                    )
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

                is PostAction.UpdateGenre -> {
                    postState = postState.copy(genre = action.newGenre)
                }
            }
        }

        private fun onUpdateIsLoading(isLoading: Boolean) {
            postState = postState.copy(isLoading = isLoading)
        }

        private fun createNormalPost(
            user: User,
            onUpdateSelf: () -> Unit,
            onNavigate: (NavigationHubRoute) -> Unit,
        ) {
            val description = postState.inputText
            onUpdateIsLoading(true)
            viewModelScope.launch(Dispatchers.IO) {
                val result =
                    postRepository.onCreatePost(
                        description = description,
                        user = user,
                        genre = postState.genre,
                    )
                if (result) {
                    onUpdateSelf()
                    viewModelScope.launch(Dispatchers.Main) {
                        onNavigate(NavigationHubRoute.Home)
                    }
                }
                onUpdateIsLoading(false)
            }
        }
    }
