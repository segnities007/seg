package com.segnities007.seg.ui.components.card

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

data class PostCardUiState(
    val icons: List<Image> = listOf(),
    val posts: List<Post> = listOf(),
    val imageLists: List<List<Image>> = listOf(),
)

data class PostCardUiAction(
    val onGetNewPosts: () -> Unit,
    val onGetImagesOfPostCard: (post: Post) -> Unit,
    val onIncrementLikeCount: (post: Post) -> Unit,
    val onDecrementLikeCount: (post: Post) -> Unit,
    val onIncrementRepostCount: (post: Post) -> Unit,
    val onDecrementRepostCount: (post: Post) -> Unit,
    val onIncrementCommentCount: (post: Post) -> Unit,
    val onDecrementCommentCount: (post: Post) -> Unit,
    val onIncrementViewCount: (post: Post) -> Unit,
)

@HiltViewModel
class PostCardViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val imageRepository: ImageRepository,
): ViewModel() {

    var postCardUiState by mutableStateOf(PostCardUiState())
        private set

    fun onGetPostCardUiAction(): PostCardUiAction{
        return PostCardUiAction(
            onGetNewPosts = this::onGetNewPosts,
            onGetImagesOfPostCard = this::onGetImagesOfPostCard,
            onIncrementLikeCount = this::onIncrementLikeCount,
            onDecrementLikeCount = this::onDecrementLikeCount,
            onIncrementRepostCount = this::onIncrementRepostCount,
            onDecrementRepostCount = this::onDecrementRepostCount,
            onIncrementCommentCount = this::onIncrementCommentCount,
            onDecrementCommentCount = this::onDecrementCommentCount,
            onIncrementViewCount = this::onIncrementViewCount,
        )
    }

    private fun onGetNewPosts(){
        viewModelScope.launch(Dispatchers.IO){
            val posts = postRepository.getNewPosts()
            val imageLists: MutableList<List<Image>> = postCardUiState.imageLists.toMutableList()
            val iconList = postCardUiState.icons.toMutableList()

            //get images of post
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
                imageLists.add(
                    imageList
                )
            }

            //get icon of post user
            for (post in posts){
                iconList.add(imageRepository.getImage(post.iconID))
            }

            postCardUiState = postCardUiState.copy(posts = posts, imageLists = imageLists, icons = iconList.toList())

        }
    }

    private fun onGetImagesOfPostCard(post: Post){
        viewModelScope.launch(Dispatchers.IO){
            if (post.imageIDs.isNullOrEmpty()){
                //画像が存在しない際は、前回の画像を残さないためにリセット
                postCardUiState = postCardUiState.copy(imageLists = postCardUiState.imageLists.plus(listOf()))
            }else{
                //画像が存在するなら取得
                val images = imageRepository.getImages(post.imageIDs)
                postCardUiState.imageLists.plus(images)
                postCardUiState = postCardUiState.copy()
            }
        }
    }

    private fun onIncrementLikeCount(post: Post){
    }

    private fun onDecrementLikeCount(post: Post){
    }

    private fun onIncrementRepostCount(post: Post){
    }

    private fun onDecrementRepostCount(post: Post){
    }

    private fun onIncrementCommentCount(post: Post){
    }

    private fun onDecrementCommentCount(post: Post){
    }

    private fun onIncrementViewCount(post: Post){
        viewModelScope.launch(Dispatchers.IO){
            postRepository.onIncrementView(post)
        }
    }

}

