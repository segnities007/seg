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
    val userID: String = "", // other user's userID
    val accounts: List<String> = listOf(),
    val currentRouteName: String = "Home",
    val comment: Post = Post(),
    val isHideTopBar: Boolean = false,
)

data class HubUiAction(
    val onUpdateSelf: (newSelf: User) -> Unit,
    val onChangeIsHideTopBar: () -> Unit,
    val onResetIsHideTopBar: () -> Unit,
    val onGetUser: () -> Unit,
    val onSetComment: (comment: Post) -> Unit,
    val onSetUserID: (userID: String) -> Unit,
    val onSetAccounts: (accounts: List<String>) -> Unit,
    val onAddPostIDToMyLikes: (postID: Int) -> Unit,
    val onRemovePostIDFromMyLikes: (postID: Int) -> Unit,
    val onAddPostIDToMyReposts: (postID: Int) -> Unit,
    val onRemovePostIDFromMyReposts: (postID: Int) -> Unit,
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
                onUpdateSelf = this::onUpdateSelf,
                onAddPostIDToMyLikes = this::onAddPostIDToMyLikes,
                onRemovePostIDFromMyLikes = this::onRemovePostIDFromMyLikes,
                onAddPostIDToMyReposts = this::onAddPostIDToMyReposts,
                onRemovePostIDFromMyReposts = this::onRemovePostIDFromMyReposts,
                onChangeCurrentRouteName = this::onChangeCurrentRouteName,
                onChangeIsHideTopBar = this::onChangeIsHideTopBar,
                onResetIsHideTopBar = this::onResetIsHideTopBar,
            )

        private fun onUpdateSelf(newSelf: User) {
            hubUiState = hubUiState.copy(user = newSelf)
            onUpdateMyself()
        }

        private fun onUpdateMyself() {
            viewModelScope.launch(Dispatchers.IO) {
                userRepository.onUpdateUser(hubUiState.user)
            }
        }

        private fun onChangeIsHideTopBar() {
            hubUiState = hubUiState.copy(isHideTopBar = !hubUiState.isHideTopBar)
        }

        private fun onResetIsHideTopBar() {
            hubUiState = hubUiState.copy(isHideTopBar = false)
        }

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
                val user = userRepository.onGetUser()
                hubUiState = hubUiState.copy(user = user, userID = user.userID)
            }
        }

        private fun onChangeCurrentRouteName(newRouteName: String) {
            hubUiState = hubUiState.copy(currentRouteName = newRouteName)
        }

        private fun onAddPostIDToMyLikes(postID: Int) {
            val newPosts = hubUiState.user.likes.plus(postID)
            val updatedUser = hubUiState.user.copy(likes = newPosts)
            hubUiState = hubUiState.copy(user = updatedUser)
            onUpdateMyself()
        }

        private fun onRemovePostIDFromMyLikes(postID: Int) {
            val newPosts = hubUiState.user.likes.minus(postID)
            val updatedUser = hubUiState.user.copy(likes = newPosts)
            hubUiState = hubUiState.copy(user = updatedUser)
            onUpdateMyself()
        }

        private fun onAddPostIDToMyReposts(postID: Int) {
            val newPosts = hubUiState.user.reposts.plus(postID)
            val updatedUser = hubUiState.user.copy(reposts = newPosts)
            hubUiState = hubUiState.copy(user = updatedUser)
            onUpdateMyself()
        }

        private fun onRemovePostIDFromMyReposts(postID: Int) {
            val newPosts = hubUiState.user.reposts.minus(postID)
            val updatedUser = hubUiState.user.copy(reposts = newPosts)
            hubUiState = hubUiState.copy(user = updatedUser)
            onUpdateMyself()
        }
    }
