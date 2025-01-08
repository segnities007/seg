package com.segnities007.seg.ui.screens.hub.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.repository.PostRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val posts: List<Post> = listOf(),
)

data class HomeUiAction(
    val onGetNewPosts: () -> Unit,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepositoryImpl: PostRepositoryImpl,
): ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO){
            onGetNewPosts()
        }
    }

    var homeUiState by mutableStateOf(HomeUiState())
        private set

    fun getHomeUiAction(): HomeUiAction{
        return HomeUiAction(
            onGetNewPosts = this::onGetNewPosts
        )
    }

    private  fun onGetNewPosts(){
        viewModelScope.launch {
            homeUiState = homeUiState.copy(posts = postRepositoryImpl.getNewPosts())
        }
    }

}