package com.segnities007.seg.ui.screens.hub.setting.my_posts

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.ui.components.card.postcard.EngagementIconAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
data class MyPostsUiState(
    val self: User = User(),
    val posts: List<Post> = listOf(),
    val hasNoMorePost: Boolean = false,
)

@Immutable
data class MyPostsUiAction(
    val onChangeHasNoMorePost: () -> Unit,
    val onGetSelf: (self: User) -> Unit,
    val onGetPosts: () -> Unit,
    val onGetBeforePosts: (updatedAt: java.time.LocalDateTime) -> Unit,
)

@HiltViewModel
class MyPostsViewModel @Inject constructor(
    private val postRepository: PostRepository,
): ViewModel(){
    
    var myPostsUiState by mutableStateOf(MyPostsUiState())
        private set

    fun onGetMyPostsUiAction(): MyPostsUiAction{
        return MyPostsUiAction(
            onGetSelf = this::onGetSelf,
            onChangeHasNoMorePost = this::onChangeHasNoMorePost,
            onGetPosts = this::onGetPosts,
            onGetBeforePosts = this::onGetBeforePosts,
        )
    }

    fun onGetEngagementIconAction(): EngagementIconAction{
        return EngagementIconAction(
            onLike = this::onLike,
            onRepost = this::onRepost,
            onComment = this::onComment,
        )
    }

    private fun onChangeHasNoMorePost(){
        myPostsUiState = myPostsUiState.copy(hasNoMorePost = !myPostsUiState.hasNoMorePost)
    }

    private fun onGetSelf(self: User){
        myPostsUiState = myPostsUiState.copy(self = self)
    }

    private fun onGetPosts(){
        viewModelScope.launch(Dispatchers.IO){
            val posts = postRepository.onGetPostsOfUser(myPostsUiState.self.userID)
            myPostsUiState = myPostsUiState.copy(posts = posts)
        }
    }

    private fun onGetBeforePosts(createAt: java.time.LocalDateTime){
        viewModelScope.launch(Dispatchers.IO){
            val posts = postRepository.onGetBeforePostsOfUser(userID = myPostsUiState.self.userID, updateAt = createAt)
            if(posts.isEmpty()) onChangeHasNoMorePost()
            myPostsUiState = myPostsUiState.copy(posts = myPostsUiState.posts.plus(posts))
        }
    }
    
    
    private fun onUpdatePosts(newPost: Post) {
        val newPosts =
            myPostsUiState.posts.map { post ->
                if (newPost.id == post.id) newPost else post
            }

        myPostsUiState = myPostsUiState.copy(posts = newPosts)
    }

    private fun onLike(
        post: Post,
        myself: User,
        onAddOrRemoveFromMyList: () -> Unit,
    ) {
        val newPost: Post
        if (!myself.likes.contains(post.id)) {
            newPost = post.copy(likeCount = post.likeCount + 1)
            viewModelScope.launch(Dispatchers.IO) {
                postRepository.onLike(post = newPost, user = myself)
            }
        } else {
            newPost = post.copy(likeCount = post.likeCount - 1)
            viewModelScope.launch(Dispatchers.IO) {
                postRepository.onUnLike(post = newPost, user = myself)
            }
        }
        onUpdatePosts(newPost)
        onAddOrRemoveFromMyList()
    }

    private fun onRepost(
        post: Post,
        myself: User,
        onAddOrRemoveFromMyList: () -> Unit,
    ) {
        val newPost: Post
        if (!myself.reposts.contains(post.id)) {
            newPost = post.copy(repostCount = post.repostCount + 1)
            viewModelScope.launch(Dispatchers.IO) {
                postRepository.onRepost(post = newPost, user = myself)
            }
        } else {
            newPost = post.copy(repostCount = post.repostCount - 1)
            viewModelScope.launch(Dispatchers.IO) {
                postRepository.onUnRepost(post = newPost, user = myself)
            }
        }
        onUpdatePosts(newPost)
        onAddOrRemoveFromMyList()
    }

    private fun onComment(
        post: Post,
        comment: Post,
        myself: User,
        updateMyself: () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            // TODO
        }
    }
}

