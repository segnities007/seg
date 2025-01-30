package com.segnities007.seg.ui.screens.hub

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.presentation.TopLayerViewModel
import com.segnities007.seg.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HubUiState(
    val user: User = User(), // myself
    val userID: String = "", // other user
    val accounts: List<String> = listOf(),
    val currentRouteName: String = "Home",
    val comment: Post = Post(),
)

data class HubUiAction(
    val onGetUser: () -> Unit,
    val onSetComment: (comment: Post) -> Unit,
    val onSetUserID: (userID: String) -> Unit,
    val onSetAccounts: (accounts: List<String>) -> Unit,
    val onAddPostIDToLikeList: (postID: Int) -> Unit,
    val onRemovePostIDFromLikeList: (postID: Int) -> Unit,
    val onAddPostIDToRepostList: (postID: Int) -> Unit,
    val onRemovePostIDFromRepostList: (postID: Int) -> Unit,
    val onChangeCurrentRouteName: (newRouteName: String) -> Unit,
)

@HiltViewModel
class HubViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : TopLayerViewModel() {
        var hubUiState by mutableStateOf(HubUiState())
            private set

        fun getHubUiAction(): HubUiAction =
            HubUiAction(
                onGetUser = this::onGetUser,
                onSetUserID = this::onSetUserID,
                onSetAccounts = this::onSetAccounts,
                onSetComment = this::onSetComment,
                onAddPostIDToLikeList = this::onAddPostIDToLikeList,
                onRemovePostIDFromLikeList = this::onRemovePostIDFromLikeList,
                onAddPostIDToRepostList = this::onAddPostIDToRepostList,
                onRemovePostIDFromRepostList = this::onRemovePostIDFromRepostList,
                onChangeCurrentRouteName = this::onChangeCurrentRouteName,
            )

        private fun onSetComment(comment: Post) {
            hubUiState = hubUiState.copy(comment = comment)
        }

        private fun onSetAccounts(accounts: List<String>) {
            hubUiState = hubUiState.copy(accounts = accounts)
        }

        private fun onSetUserID(userID: String) {
            hubUiState = hubUiState.copy(userID = userID)
        }

        private fun onGetUser() {
            viewModelScope.launch(Dispatchers.IO) {
                val user = userRepository.getUser()
                hubUiState = hubUiState.copy(user = user, userID = user.userID)
            }
        }

        private fun onChangeCurrentRouteName(newRouteName: String) {
            hubUiState = hubUiState.copy(currentRouteName = newRouteName)
        }

        // TODO
        private fun onAddPostIDToLikeList(postID: Int) {
            val updatedUser = hubUiState.user.copy(likes = hubUiState.user.likes.plus(postID))
            hubUiState = hubUiState.copy(user = updatedUser)
        }

        private fun onRemovePostIDFromLikeList(postID: Int) {
            val updatedUser = hubUiState.user.copy(likes = hubUiState.user.likes.minus(postID))
            hubUiState = hubUiState.copy(user = updatedUser)
        }

        private fun onAddPostIDToRepostList(postID: Int) {
            val newPosts = hubUiState.user.reposts.plus(postID)
            val updatedUser = hubUiState.user.copy(reposts = newPosts)
            hubUiState = hubUiState.copy(user = updatedUser)
        }

        private fun onRemovePostIDFromRepostList(postID: Int) {
            val newPosts = hubUiState.user.reposts.minus(postID)
            val updatedUser = hubUiState.user.copy(reposts = newPosts)

            hubUiState = hubUiState.copy(user = updatedUser)
        }
    }
