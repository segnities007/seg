package com.segnities007.seg.ui.screens.hub.post

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PostUiState(
    val inputText: String = "",
    val selectedUris: List<String> = listOf(),
    val byteArrayList: List<ByteArray> = listOf(),
)

data class PostUiAction(
    val onUpdateInputText: (newInputText: String) -> Unit,
    val onGetUris: (newUris: List<String>) -> Unit,
    val onGetByteArrayList: (byteArrayList: List<ByteArray>) -> Unit,
    val onCreatePost: (user: User, byteArrayList: List<ByteArray>) -> Unit,
)

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository,
): ViewModel() {

    var postUiState by mutableStateOf(PostUiState())
        private set

    private fun onGetByteArrayList(byteArrayList: List<ByteArray>){
        postUiState = postUiState.copy(byteArrayList = byteArrayList)
    }

    fun getPostUiAction(): PostUiAction{
        return PostUiAction(
            onUpdateInputText = this::onUpdateInputText,
            onGetUris = this::onGetUris,
            onCreatePost = this::onCreatePost,
            onGetByteArrayList = this::onGetByteArrayList,
        )
    }
    private fun onCreatePost(user: User, byteArrayList: List<ByteArray>){
        val description = postUiState.inputText
        viewModelScope.launch(Dispatchers.IO){
            postRepository.createPost(description = description, user = user, byteArrayList = byteArrayList)
        }
    }

    private fun onGetUris(newUris: List<String>){
        postUiState = postUiState.copy(selectedUris = newUris)
    }

    private fun onUpdateInputText(newInputText: String){
        postUiState = postUiState.copy(inputText = newInputText)
    }

}