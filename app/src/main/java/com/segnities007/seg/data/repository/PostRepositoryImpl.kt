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

            val postID = result.id // getPostID()の結果を変数に格納
            Log.d("PostRepositoryImpl", "getPostID() returns: $postID") // ログ出力

            if(user.posts.isNotEmpty()){
                user.copy(posts = user.posts + postID)
            }else{
                user.copy(posts = listOf(postID))
            }
             // ここでエラーが発生している可能性

            Log.d("PostRepositoryImpl", "success create post")
            return true
        } catch (e: Exception){
            Log.d("UserRepositoryImpl39", "error $e")
            throw Exception() // このthrowは不要。後述
        }
        }

    override suspend fun getPosts(ids: List<Int>): List<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun getNewPosts(): List<Post> {
        val tableName = "posts"

        try {
            val result = supabaseClient
                .from(tableName)
                .select {
                    limit(count = 10)
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