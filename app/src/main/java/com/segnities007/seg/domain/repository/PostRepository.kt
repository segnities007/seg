package com.segnities007.seg.domain.repository

import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User

interface PostRepository {
    // about post
    suspend fun createPost(
        description: String,
        user: User,
        byteArrayList: List<ByteArray>,
    ): Boolean

    suspend fun onGetUserPosts(userID: String): List<Post>

    suspend fun onGetBeforePosts(afterPostCreateAt: java.time.LocalDateTime): List<Post>

    suspend fun onGetPost(postID: Int): Post

    suspend fun onGetNewPost(): Post

    suspend fun onGetNewPosts(): List<Post>

    suspend fun onGetTrendPostOfToday(limit: Long = 10): List<Post>

    suspend fun onGetTrendPostOfWeek(limit: Long = 10): List<Post>

    suspend fun onGetTrendPostOfMonth(limit: Long = 10): List<Post>

    suspend fun onGetTrendPostOfYear(limit: Long = 10): List<Post>

    suspend fun onUpdatePost(post: Post)

    suspend fun onDeletePost(post: Post)

    // about view
    suspend fun onIncrementView(post: Post)

    // about like
    suspend fun onLike(
        post: Post,
        user: User,
    )

    suspend fun onUnLike(
        post: Post,
        user: User,
    )

    // about repost
    suspend fun onRepost(
        post: Post,
        user: User,
    )

    suspend fun onUnRepost(
        post: Post,
        user: User,
    )

    // about comment
    suspend fun onComment(
        post: Post,
        comment: Post,
        user: User,
    )

    suspend fun onUnComment(
        post: Post,
        comment: Post,
        user: User,
    )
}
