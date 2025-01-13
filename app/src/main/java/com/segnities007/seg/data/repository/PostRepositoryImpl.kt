package com.segnities007.seg.data.repository

import android.util.Log
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.repository.ImageRepository
import com.segnities007.seg.domain.repository.PostRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Order
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
    private val imageRepository: ImageRepository,
): PostRepository {

    private val tag = "PostRepositoryImpl"
    private val tableName = "posts"

    override suspend fun createPost(
        description: String,
        user: User,
        byteArrayList: List<ByteArray>,
    ): Boolean {

        val imageIDs: MutableList<Int> = mutableListOf()

        try {
            for(byteArray in byteArrayList){
                imageIDs.add(imageRepository.postImage(byteArray).id)
            }

            val post = Post(
                userID = user.userID,
                name = user.name,
                description = description,
                imageIDs = imageIDs.toList(),
            )

            val result = postgrest.from(tableName).insert(post){
                    select()
                }.decodeSingle<Post>()

            val postID = result.id

            if(!user.posts.isNullOrEmpty()){
                user.copy(posts = user.posts + postID)
            }else{
                user.copy(posts = listOf(postID))
            }

            return true
        } catch (e: Exception){
            Log.d(tag, "error $e")
        }
        return false
    }

    suspend fun removeLikePost(post: Post){

        try {
            postgrest.from(tableName).update({
                    Post::likeCount setTo (post.likeCount - 1)
                }){
                    filter {
                        Post::id eq post.id
                    }
                }

        }catch (e: Exception){
            Log.e(tag, "failed to likePost $e")
            throw e
        }
    }

    suspend fun likePost(post: Post){

        try {
            postgrest.from(tableName).update({
                    Post::likeCount setTo (post.likeCount + 1)
                }){
                    filter {
                        Post::id eq post.id
                    }
                }

        }catch (e: Exception){
            Log.e(tag, "failed to likePost $e")
            throw e
        }
    }

    override suspend fun getUserPosts(userID: String): List<Post> {

        try {
            val result = postgrest.from(tableName).select {
                    filter { Post::userID eq userID }
                    order("create_at", Order.DESCENDING)
                    limit(count = 5)
                }.decodeList<Post>()
            return result

        } catch (e: Exception){
            Log.e(tag, "failed to getPost $e")
            throw e
        }
    }

    override suspend fun getNewPost(): Post {
        try {
            val result = postgrest.from(tableName).select {
                order("create_at", Order.DESCENDING)
            }.decodeSingle<Post>()
            return result

        }catch (e: Exception){
            Log.e(tag, "failed to get new post $e")
            throw e
        }
    }

    override suspend fun getNewPosts(): List<Post> {

        try {
            val result = postgrest.from(tableName).select {
                    order("create_at", Order.DESCENDING)
                    limit(count = 5)
                }.decodeList<Post>()
            return result

        } catch (e: Exception){
            Log.e(tag, "failed to get new posts $e")
            throw e
        }
    }

    override suspend fun onIncrementView(post: Post) {

        try {
            postgrest.from(tableName).update({
                Post::viewCount setTo  post.viewCount+1
            }){
                filter {
                    Post::id eq post.id
                }
            }

        } catch (e: Exception){
            Log.e(tag, "failed to increment view $e")
            throw e
        }
    }

    override suspend fun updatePost(post: Post): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deletePost(id: Int): Boolean {
        TODO("Not yet implemented")
    }

}