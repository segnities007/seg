package com.segnities007.seg.ui.components.card.postcard

import androidx.compose.runtime.Immutable
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User

@Immutable
data class PostsState(
    val posts: List<Post> = listOf(),
    val post: Post = Post(), // using view comment
    val hasNoMorePost: Boolean = false,
)

// Like Iconがクリックされたら、そのポストを修正してセットし直したリストを返す。
fun PostsState.getUpdatedPostAndPostsForLikeClick(
    self: User,
    clickedPost: Post,
): Pair<Post, List<Post>> {
    val newClickedPost =
        clickedPost.copy(
            likeCount = if (self.likes.contains(clickedPost.id)) clickedPost.likeCount - 1 else clickedPost.likeCount + 1,
        )
    val updatedPosts =
        posts
            .minus(newClickedPost)
            .plus(newClickedPost)

    return Pair(newClickedPost, updatedPosts)
}

suspend fun PostsState.updateUsingOnLikeOfPostRepository(process: suspend () -> Unit) {
    process()
}
