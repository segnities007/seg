package com.segnities007.seg.domain.repository

import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User

interface PostRepository {
    suspend fun createPost(description: String, user: User, byteArrayList: List<ByteArray>): Boolean
    suspend fun getUserPosts(userID: String): List<Post>
    suspend fun getNewPost(): Post
    suspend fun getNewPosts(): List<Post>
    suspend fun onIncrementView(post: Post)
    suspend fun updatePost(post: Post): Boolean
    suspend fun deletePost(id: Int): Boolean
}