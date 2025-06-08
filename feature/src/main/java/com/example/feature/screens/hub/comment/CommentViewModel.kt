package com.example.feature.screens.hub.comment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.PostRepository
import com.example.feature.model.UiStatus
import com.example.feature.screens.hub.HubAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
    ) : ViewModel() {
        var commentState by mutableStateOf(CommentState())
            private set

        fun onCommentAction(action: CommentAction) {
            when (action) {
                is CommentAction.GetComments -> getComments(action)
                is CommentAction.ProcessOfEngagementAction,
                -> commentReducer(action, commentState)
            }
        }

        private fun getComments(action: CommentAction.GetComments) {
            commentState = commentState.copy(uiStatus = UiStatus.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val comments = postRepository.onGetComments(action.comment)
                    commentState = commentState.copy(comments = comments, uiStatus = UiStatus.Success)
                } catch (e: Exception) {
                    commentState =
                        commentState.copy(uiStatus = UiStatus.Error("コメントの取得に失敗しました。"))
                    action.onHubAction(HubAction.OpenSnackBar((commentState.uiStatus as UiStatus.Error).message))
                } finally {
                    commentState = commentState.copy(uiStatus = UiStatus.Initial)
                }
            }
        }
    }
