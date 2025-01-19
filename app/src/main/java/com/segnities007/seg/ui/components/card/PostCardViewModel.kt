package com.segnities007.seg.ui.components.card

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Image
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.domain.repository.ImageRepository
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.navigation.hub.NavigationHubRoute
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
    val onClickIcon: (onHubNavigate: (Route) -> Unit,) -> Unit,
    val onClickPostCard: (onHubNavigate: (Route) -> Unit,) -> Unit,
    val onLike: (post: Post, myself: User, onGetUser: () -> Unit) -> Unit,
    val onRepost: (post: Post, myself: User, onGetUser: () -> Unit) -> Unit,
    val onComment: (post: Post, comment: Post, myself: User, onGetUser: () -> Unit) -> Unit,
    val onIncrementViewCount: (post: Post) -> Unit,
)

@HiltViewModel
class PostCardViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
        private val imageRepository: ImageRepository,
    ) : ViewModel() {
        var postCardUiState by mutableStateOf(PostCardUiState())
            private set

        fun onGetPostCardUiAction(): PostCardUiAction =
            PostCardUiAction(
                onGetNewPosts = this::onGetNewPosts,
                onGetImagesOfPostCard = this::onGetImagesOfPostCard,
                onClickIcon = this::onClickIcon,
                onClickPostCard = this::onClickPostCard,
                onLike = this::onLike,
                onRepost = this::onRepost,
                onComment = this::onComment,
                onIncrementViewCount = this::onIncrementViewCount,
            )

        private fun onGetNewPosts() {
            viewModelScope.launch(Dispatchers.IO) {
                val posts = postRepository.getNewPosts()
                val imageLists: MutableList<List<Image>> = postCardUiState.imageLists.toMutableList()
                val iconList = postCardUiState.icons.toMutableList()

                // get images of post
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
                    imageLists.add(
                        imageList,
                    )
                }

                // get icon of post user
                for (post in posts) {
                    iconList.add(imageRepository.getImage(post.iconID))
                }

                postCardUiState = postCardUiState.copy(posts = posts, imageLists = imageLists, icons = iconList.toList())
            }
        }

        private fun onUpdatePosts(newPost: Post) {
                val newPosts =
                    postCardUiState.posts.map { post ->
                        if (newPost.id == post.id) newPost else post
                    }

                postCardUiState = postCardUiState.copy(posts = newPosts)
        }

        private fun onGetImagesOfPostCard(post: Post) {
            viewModelScope.launch(Dispatchers.IO) {
                if (post.imageIDs.isNullOrEmpty()) {
                    // 画像が存在しない際は、前回の画像を残さないためにリセット
                    postCardUiState = postCardUiState.copy(imageLists = postCardUiState.imageLists.plus(listOf()))
                } else {
                    // 画像が存在するなら取得
                    val images = imageRepository.getImages(post.imageIDs)
                    postCardUiState.imageLists.plus(images)
                    postCardUiState = postCardUiState.copy()
                }
            }
        }

        private fun onClickIcon(onHubNavigate: (Route) -> Unit){
            onHubNavigate(NavigationHubRoute.Account)
        }

        private fun onClickPostCard(onHubNavigate: (Route) -> Unit,){
            onHubNavigate(NavigationHubRoute.Comment)
        }

        private fun onIncrementViewCount(post: Post) {
            viewModelScope.launch(Dispatchers.IO) {
                postRepository.onIncrementView(post)
            }
        }

        private fun onLike(
            post: Post,
            myself: User,
            onGetUser: () -> Unit,
        ) {
                val newPost: Post
                if (!myself.likes!!.contains(post.id)) {
                    newPost = post.copy(likeCount = post.likeCount+1)
                    viewModelScope.launch(Dispatchers.IO) {
                        postRepository.onLike(post = newPost, user = myself)
                    }
                } else {
                    newPost = post.copy(likeCount = post.likeCount-1)
                    viewModelScope.launch(Dispatchers.IO) {
                        postRepository.onUnLike(post = newPost, user = myself)
                }
            }
            onUpdatePosts(newPost)
            onGetUser()
        }

        private fun onRepost(
            post: Post,
            myself: User,
            onGetUser: () -> Unit,
        ) {
                val newPost: Post
                if (!myself.reposts!!.contains(post.id)) {
                    newPost = post.copy(repostCount = post.repostCount+1)
                    viewModelScope.launch(Dispatchers.IO) {
                        postRepository.onRepost(post = newPost, user = myself)
                    }
                } else {
                    newPost = post.copy(repostCount = post.repostCount-1)
                    viewModelScope.launch(Dispatchers.IO) {
                        postRepository.onUnRepost(post = newPost, user = myself)
                    }
            }
            onUpdatePosts(newPost)
            onGetUser()
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
