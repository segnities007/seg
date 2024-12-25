package com.segnities007.seg.domain.repository

import com.segnities007.seg.data.model.Post

interface PostRepository {
    suspend fun createPost(post: Post): Boolean
    suspend fun getPost(id: Int): Post
    suspend fun updatePost(post: Post): Boolean
    suspend fun deletePost(id: Int): Boolean
}