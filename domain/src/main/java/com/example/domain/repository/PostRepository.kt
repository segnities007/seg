package com.example.domain.repository

import com.example.domain.model.post.Genre
import com.example.domain.model.post.Post
import com.example.domain.model.user.User
import java.time.LocalDateTime

interface PostRepository {
    // about post
    suspend fun onCreatePost(
        description: String,
        user: User,
        genre: Genre,
    ): Boolean

    suspend fun onCreateComment(
        description: String,
        self: User,
        commentedPost: Post,
    ): Boolean

    suspend fun onGetPostsOfUser(userID: String): List<Post>

    suspend fun onGetBeforePostsOfUser(
        userID: String,
        updateAt: LocalDateTime,
    ): List<Post>

    suspend fun onGetBeforePosts(afterPostCreateAt: LocalDateTime): List<Post>

    suspend fun onGetPost(postID: Int): Post

    suspend fun onGetComment(commentID: Int): Post

    suspend fun onGetPosts(postIDs: List<Int>): List<Post>

    suspend fun onGetComments(comment: Post): List<Post>

    suspend fun onGetNewPost(): Post

    suspend fun onGetNewPosts(): List<Post>

    suspend fun onGetTrendPostOfToday(limit: Long = 10): List<Post>

    suspend fun onGetTrendPostOfWeek(limit: Long = 10): List<Post>

    suspend fun onGetTrendPostOfMonth(limit: Long = 10): List<Post>

    suspend fun onGetTrendPostOfYear(limit: Long = 10): List<Post>

    suspend fun onGetPostsByKeyword(keyword: String): List<Post>

    suspend fun onGetBeforePostsByKeyword(
        keyword: String,
        afterPostCreateAt: LocalDateTime,
    ): List<Post>

    suspend fun onGetPostsByKeywordSortedByViewCount(keyword: String): List<Post>

    suspend fun onGetBeforePostsByKeywordSortedByViewCount(
        keyword: String,
        viewCount: Int,
    ): List<Post>

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
}
