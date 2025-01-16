package com.segnities007.seg.domain.repository

import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User

interface PostRepository {

    //about post
    suspend fun createPost(description: String, user: User, byteArrayList: List<ByteArray>): Boolean
    suspend fun getUserPosts(userID: String): List<Post>
    suspend fun getPost(postID: Int): Post
    suspend fun getNewPost(): Post
    suspend fun getNewPosts(): List<Post>
    suspend fun updatePost(post: Post): Boolean
    suspend fun deletePost(id: Int): Boolean

    //about view
    suspend fun onIncrementView(post: Post)

    //about like
    suspend fun onLike(post: Post, user: User)
    suspend fun onCancelLike(post: Post, user: User)

    //about repost
    suspend fun onRepost(post: Post, user: User)
    suspend fun onCancelRepost(post: Post, user: User)

    //about comment
    suspend fun onComment(post: Post, comment: Post,  user: User)
    suspend fun onCancelComment(post: Post, comment: Post, user: User)

}