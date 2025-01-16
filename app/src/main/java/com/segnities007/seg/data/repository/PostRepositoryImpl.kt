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
    //table name
    private val users = "users"
    private val posts = "posts"


    //about post


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


            val result = postgrest.from(posts).insert(post){
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
            Log.d(tag, e.toString())
        }
        return false
    }

    override suspend fun getUserPosts(userID: String): List<Post> {

        try {
            val result = postgrest.from(posts).select {
                    filter { Post::userID eq userID }
                    order("create_at", Order.DESCENDING)
                    limit(count = 7)
                }.decodeList<Post>()
            return result

        } catch (e: Exception){
            Log.e(tag, e.toString())
            throw e
        }
    }

    override suspend fun getPost(postID: Int): Post {
        try {

            val result = postgrest.from(posts).select {
                filter {
                    Post::id eq postID
                }
            }.decodeSingle<Post>()
            return result

        }catch (e: Exception){
            Log.e(tag, "failed to get post $e")
            throw e
        }
    }

    override suspend fun getNewPost(): Post {
        try {
            val result = postgrest.from(posts).select {
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
            val result = postgrest.from(posts).select {
                    order("create_at", Order.DESCENDING)
                    limit(count = 7)
                }.decodeList<Post>()
            return result

        } catch (e: Exception){
            Log.e(tag, "failed to get new posts $e")
            throw e
        }
    }

    override suspend fun onIncrementView(post: Post) {

        try {
            postgrest.from(posts).update({
                Post::viewCount setTo  (post.viewCount + 1)
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
        try {

        } catch (e: Exception){
            Log.e(tag, e.toString())
        }
    }

    override suspend fun deletePost(id: Int): Boolean {
        TODO("Not yet implemented")
        try {

        } catch (e: Exception){
            Log.e(tag, e.toString())
        }
    }





    //about like





    override suspend fun onLike(post: Post, user: User) {

        try {

            //increment like count
            postgrest.from(posts).update({
                Post::likeCount setTo (post.likeCount + 1)
            }){
                filter {
                    Post::id eq post.id
                }
            }


            //add like list of user
            val updatedUser: User

            if(!user.likes.isNullOrEmpty()){
                updatedUser = user.copy(likes = user.likes.plus(post.id))
            }else{
                updatedUser = user.copy(likes = listOf(post.id))
            }

            postgrest.from(users).update({
                val columnName = "post_like_id_list"
                set(columnName, updatedUser.likes)
            }){
                filter {
                    User::id eq updatedUser.id
                }
            }

            Log.d(tag, "success like")


        } catch (e: Exception){
            Log.e(tag, "like: $e")
        }
    }



    override suspend fun onCancelLike(post: Post, user: User) {
        try {


            //decrement like count
            postgrest.from(posts).update({
                Post::likeCount setTo post.likeCount - 1
            }){
                filter {
                    Post::id eq post.id
                }
            }


            //remove like list of user
            val updatedUser = user.copy(likes = user.likes?.minus(post.id))

            postgrest.from(users).update({
                val columnName = "post_like_id_list"
                set(columnName, updatedUser.likes)
            }){
                filter {
                    User::id eq updatedUser.id
                }
            }


            Log.d(tag, "success c like")


        } catch (e: Exception){
            Log.e(tag, "cancel like: $e")
        }
    }





    //about repost

    override suspend fun onRepost(post: Post, user: User) {
        try {

            //increment post count
            postgrest.from(posts).update({
                Post::repostCount setTo post.repostCount+1
            }){
                filter {
                    Post::id eq post.id
                }
            }


            //add repost list of user
            val updatedUser: User
            if(!user.reposts.isNullOrEmpty()){
                updatedUser = user.copy(reposts = user.reposts.plus(post.id))
            }else{
                updatedUser = user.copy(reposts = listOf(post.id))
            }

            postgrest.from(users).update({
                val columnName = "post_repost_id_list"
                set(columnName, updatedUser.reposts)
            }){
                filter {
                    User::id eq updatedUser.id
                }
            }


        } catch (e: Exception){
            Log.e(tag, e.toString())
        }
    }

    override suspend fun onCancelRepost(post: Post, user: User) {
        try {


            //decrement post count
            postgrest.from(posts).update({
                Post::repostCount setTo post.repostCount-1
            }){
                filter {
                    Post::id eq post.id
                }
            }


            //add repost list of user
            val updatedUser = user.copy(reposts = user.reposts?.minus(post.id))

            postgrest.from(users).update({
                val columnName = "post_repost_id_list"
                set(columnName, updatedUser.reposts)
            }){
                filter {
                    User::id eq updatedUser.id
                }
            }


        } catch (e: Exception){
            Log.e(tag, e.toString())
        }
    }





    //about comment

    override suspend fun onComment(post: Post, comment: Post,  user: User) {
        TODO("Not yet implemented")
        try {

        } catch (e: Exception){
            Log.e(tag, e.toString())
        }
    }

    override suspend fun onCancelComment(post: Post, comment: Post, user: User) {
        TODO("Not yet implemented")
        try {

        } catch (e: Exception){
            Log.e(tag, e.toString())
        }
    }

}