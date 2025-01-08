package com.segnities007.seg.ui.screens.hub.post

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.User
import com.segnities007.seg.data.repository.PostRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PostUiState(
    val inputText: String = "",
    val selectedUris: List<String> = listOf(),
)

data class PostUiAction(
    val onInputTextChange: (newInputText: String) -> Unit,
    val onSelectedUrisChange: (newUris: List<String>) -> Unit,
    val onPostCreate: (user: User) -> Unit,
)

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepositoryImpl: PostRepositoryImpl,
): ViewModel() {

    var postUiState by mutableStateOf(PostUiState())
        private set

    fun getPostUiAction(): PostUiAction{
        return PostUiAction(
            onInputTextChange = this::onInputTextChange,
            onSelectedUrisChange = this::onSelectedUrisChange,
            onPostCreate = this::onPostCreate,
        )
    }

    private fun onPostCreate(user: User){
        viewModelScope.launch {
            postRepositoryImpl.createPost(description = postUiState.inputText, user = user)
        }
    }

    private fun onSelectedUrisChange(newUris: List<String>){
        postUiState = postUiState.copy(selectedUris = newUris)
    }

    private fun onInputTextChange(newInputText: String){
        postUiState = postUiState.copy(inputText = newInputText)
    }

}