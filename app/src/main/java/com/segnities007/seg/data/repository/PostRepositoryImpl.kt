package com.segnities007.seg.data.repository

import android.util.Log
import com.segnities007.seg.Hub
import com.segnities007.seg.Login
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.repository.PostRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
): PostRepository {

    override suspend fun createPost(
        description: String,
        user: User,
    ): Boolean {
        val tableName = "posts"
        val post = Post(
            userID = user.userID,
            name = user.name,
            description = description,
        )

        try {
            val result = supabaseClient
                .from(tableName)
                .upsert(post){
                    select()
                }.decodeSingle<Post>()

            val postID = result.id

            if(user.posts.isNotEmpty()){
                user.copy(posts = user.posts + postID)
            }else{
                user.copy(posts = listOf(postID))
            }

            Log.d("PostRepositoryImpl", "success create post")
            return true
        } catch (e: Exception){
            Log.d("UserRepositoryImpl39", "error $e")
        }
        return false
    }

    suspend fun removeLikePost(post: Post){
        val tableName = "posts"

        try {
            supabaseClient
                .from(tableName)
                .update({
                    Post::likeCount setTo (post.likeCount - 1)
                }){
                    filter {
                        Post::id eq post.id
                    }
                }

            Log.d("PostRepositoryImpl", "success remove like posts")

        }catch (e: Exception){
            Log.e("PostRepositoryImpl/likePost", "failed to likePost $e")
            throw e
        }
    }

    suspend fun likePost(post: Post){
        val tableName = "posts"

        try {
            supabaseClient
                .from(tableName)
                .update({
                    Post::likeCount setTo (post.likeCount + 1)
                }){
                    filter {
                        Post::id eq post.id
                    }
                }

            Log.d("PostRepositoryImpl", "success like posts")

        }catch (e: Exception){
            Log.e("PostRepositoryImpl/likePost", "failed to likePost $e")
            throw e
        }
    }

    override suspend fun getUserPosts(userID: String): List<Post> {
        val tableName = "posts"

        try {
            val result = supabaseClient
                .from(tableName)
                .select {
                    filter { Post::userID eq userID }
                    order("create_at", Order.DESCENDING)
                    limit(count = 20)
                }.decodeList<Post>()

            Log.d("PostRepositoryImpl", "success get user posts")

            return result

        } catch (e: Exception){
            Log.e("PostRepositoryImpl/GetPost", "failed to getPost $e")
            throw e
        }
    }

    override suspend fun getNewPosts(): List<Post> {
        val tableName = "posts"

        try {
            val result = supabaseClient
                .from(tableName)
                .select {
                    order("create_at", Order.DESCENDING)
                    limit(count = 20)
                }.decodeList<Post>()

            Log.d("PostRepositoryImpl", "success getNewPosts")

            return result

        } catch (e: Exception){
            Log.e("PostRepositoryImpl/GetPost", "failed to getPost $e")
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