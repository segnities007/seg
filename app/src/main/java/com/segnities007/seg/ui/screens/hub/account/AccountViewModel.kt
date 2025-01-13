package com.segnities007.seg.ui.screens.hub.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Image
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.repository.ImageRepository
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AccountUiState(
    val user: User = User(),
    val icon: Image = Image(),

    val posts: List<Post> = listOf(),
    val images: List<List<Image>> = listOf(),

    val users: List<User> = listOf(),
)

data class AccountUiAction(
    val onGetOtherUser: (userID: String) -> Unit,
    val onGetUserPosts: (userID: String) -> Unit,
    val onGetUsers: (userIDs: List<User>) -> Unit,
    val onGetIcon: (iconID: Int) -> Unit,
    val onSetUsers: (userIDs: List<String>) -> Unit,
    val onFollow: (myself: User, other: User) -> Unit,
)

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val imageRepository: ImageRepository,
): ViewModel() {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    var accountUiState by mutableStateOf(AccountUiState())
        private set

    fun getAccountUiAction(): AccountUiAction{
        return AccountUiAction(
            onGetOtherUser = this::onGetOtherUser,
            onGetUserPosts = this::onGetUserPosts,
            onGetIcon = this::onGetIcon,
            onFollow = this::onFollow,
            onSetUsers = this::onSetUsers,
            onGetUsers = this::onGetUsers,
        )
    }

    private fun onSetUsers(userIDs: List<String>){
        viewModelScope.launch(Dispatchers.IO){
            val users = userRepository.getUsers(userIDs)
            onGetUsers(users)
        }
    }

    private fun onFollow(myself: User, other: User){
        viewModelScope.launch(Dispatchers.IO){
            userRepository.followUser(myself, other)
        }
    }

    private fun onGetUsers(users: List<User>){
        viewModelScope.launch(Dispatchers.IO){
            accountUiState = accountUiState.copy(users = users)
        }
    }

    private fun onGetOtherUser(userID: String){
        viewModelScope.launch(ioDispatcher){
            val user = userRepository.getOtherUser(userID)
            accountUiState = accountUiState.copy(user = user)
        }
    }

    private fun onGetIcon(iconID: Int){
        viewModelScope.launch(Dispatchers.IO){
            val icon = imageRepository.getImage(iconID)
            accountUiState = accountUiState.copy(icon = icon)
        }
    }


    private fun onGetUserPosts(userID: String){
        viewModelScope.launch(ioDispatcher){
            val posts = postRepository.getUserPosts(userID)
            val images: MutableList<List<Image>> = mutableListOf()
            for(post in posts){
                val imageList = mutableListOf<Image>()
                if(!post.imageIDs.isNullOrEmpty()){
                    for(imageID in post.imageIDs){

                        imageList.add(
                            imageRepository.getImage(imageID)
                        )
                    }
                }
                imageList.toList()
                images.add(
                    imageList
                )
            }

            accountUiState = accountUiState.copy(posts = posts, images = images)
        }
    }

}