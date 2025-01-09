package com.segnities007.seg.ui.screens.hub.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.data.repository.PostRepositoryImpl
import com.segnities007.seg.data.repository.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AccountUiState(
    val user: User = User(),
    val posts: List<Post> = listOf(),
)

data class AccountUiAction(
    val getOtherUser: (userID: String) -> Unit,
    val getUserPosts: (userID: String) -> Unit,
)

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl,
    private val postRepositoryImpl: PostRepositoryImpl,
): ViewModel() {

    var accountUiState by mutableStateOf(AccountUiState())
        private set

    fun getAccountUiAction(): AccountUiAction{
        return AccountUiAction(
            getOtherUser = this::getOtherUser,
            getUserPosts = this::getUserPosts,
        )
    }

    private fun getOtherUser(userID: String){
        viewModelScope.launch(Dispatchers.IO){
            val user = userRepositoryImpl.getOtherUser(userID)
            accountUiState = accountUiState.copy(user = user)
        }
    }

    private fun getUserPosts(userID: String){
        viewModelScope.launch(Dispatchers.IO){
            val posts = postRepositoryImpl.getUserPosts(userID)
            accountUiState = accountUiState.copy(posts = posts)
        }
    }

}