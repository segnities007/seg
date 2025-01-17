package com.segnities007.seg.ui.screens.hub.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Image
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.domain.repository.ImageRepository
import com.segnities007.seg.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val posts: List<Post> = listOf(),
    val images: List<List<Image>> = listOf(),
    val icon: Image = Image(),
)

data class HomeUiAction(
    val onGetNewPosts: () -> Unit,
    val onGetIcon: (iconID: Int) -> Unit,
)

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
        private val imageRepository: ImageRepository,
    ) : ViewModel() {
        init {
            viewModelScope.launch(Dispatchers.IO) {
                onGetNewPosts()
            }
        }

        var homeUiState by mutableStateOf(HomeUiState())
            private set

        fun getHomeUiAction(): HomeUiAction =
            HomeUiAction(
                onGetNewPosts = this::onGetNewPosts,
                onGetIcon = this::onGetIcon,
            )

        private fun onGetIcon(iconID: Int) {
            viewModelScope.launch(Dispatchers.IO) {
                val icon = imageRepository.getImage(iconID)
                homeUiState = homeUiState.copy(icon = icon)
            }
        }

        private fun onGetNewPosts() {
            viewModelScope.launch(Dispatchers.IO) {
                val posts = postRepository.getNewPosts()
                val images: MutableList<List<Image>> = homeUiState.images.toMutableList()
                for (post in posts) {
                    val imageList = mutableListOf<Image>()
                    if (!post.imageIDs.isNullOrEmpty()) {
                        for (imageID in post.imageIDs) {
                            imageList.add(
                                imageRepository.getImage(imageID),
                            )
                        }
                    }
                    imageList.toList()
                    images.add(
                        imageList,
                    )
                }

                homeUiState = homeUiState.copy(posts = posts, images = images)
            }
        }
    }
