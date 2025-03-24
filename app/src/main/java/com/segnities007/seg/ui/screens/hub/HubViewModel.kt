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

@HiltViewModel
class HubViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : TopLayerViewModel() {
        var hubState by mutableStateOf(HubState())
            private set

        fun getHubUiAction(): HubAction =
            HubAction(
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
            hubState = hubState.copy(user = newSelf)
            onUpdateMyself()
        }

        private fun onUpdateMyself() {
            viewModelScope.launch(Dispatchers.IO) {
                userRepository.onUpdateUser(hubState.user)
            }
        }

        private fun onChangeIsHideTopBar() {
            hubState = hubState.copy(isHideTopBar = !hubState.isHideTopBar)
        }

        private fun onResetIsHideTopBar() {
            hubState = hubState.copy(isHideTopBar = false)
        }

        private fun onSetComment(comment: Post) {
            hubState = hubState.copy(comment = comment)
        }

        private fun onSetAccounts(accounts: List<String>) {
            hubState = hubState.copy(accounts = accounts)
        }

        private fun onSetUserID(userID: String) {
            hubState = hubState.copy(userID = userID)
        }

        private fun onGetUser() {
            viewModelScope.launch(Dispatchers.IO) {
                val user = userRepository.onGetUser()
                hubState = hubState.copy(user = user, userID = user.userID)
            }
        }

        private fun onChangeCurrentRouteName(newRouteName: String) {
            hubState = hubState.copy(currentRouteName = newRouteName)
        }

        private fun onAddPostIDToMyLikes(postID: Int) {
            val newPosts = hubState.user.likes.plus(postID)
            val updatedUser = hubState.user.copy(likes = newPosts)
            hubState = hubState.copy(user = updatedUser)
            onUpdateMyself()
        }

        private fun onRemovePostIDFromMyLikes(postID: Int) {
            val newPosts = hubState.user.likes.minus(postID)
            val updatedUser = hubState.user.copy(likes = newPosts)
            hubState = hubState.copy(user = updatedUser)
            onUpdateMyself()
        }

        private fun onAddPostIDToMyReposts(postID: Int) {
            val newPosts = hubState.user.reposts.plus(postID)
            val updatedUser = hubState.user.copy(reposts = newPosts)
            hubState = hubState.copy(user = updatedUser)
            onUpdateMyself()
        }

        private fun onRemovePostIDFromMyReposts(postID: Int) {
            val newPosts = hubState.user.reposts.minus(postID)
            val updatedUser = hubState.user.copy(reposts = newPosts)
            hubState = hubState.copy(user = updatedUser)
            onUpdateMyself()
        }
    }
