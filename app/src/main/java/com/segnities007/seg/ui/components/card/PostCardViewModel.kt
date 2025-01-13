package com.segnities007.seg.ui.components.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.domain.repository.ImageRepository
import com.segnities007.seg.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

//data class PostCardUiState()

data class PostCardUiAction(
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

    fun onGetPostCardUiAction(): PostCardUiAction{
        return PostCardUiAction(
            onIncrementLikeCount = this::onIncrementLikeCount,
            onDecrementLikeCount = this::onDecrementLikeCount,
            onIncrementRepostCount = this::onIncrementRepostCount,
            onDecrementRepostCount = this::onDecrementRepostCount,
            onIncrementCommentCount = this::onIncrementCommentCount,
            onDecrementCommentCount = this::onDecrementCommentCount,
            onIncrementViewCount = this::onIncrementViewCount
        )
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

