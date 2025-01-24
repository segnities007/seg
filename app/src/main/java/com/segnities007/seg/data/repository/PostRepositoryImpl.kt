package com.segnities007.seg.data.repository

import android.util.Log
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.repository.ImageRepository
import com.segnities007.seg.domain.repository.PostRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Order
import java.time.LocalDateTime
import javax.inject.Inject

class PostRepositoryImpl
    @Inject
    constructor(
        private val postgrest: Postgrest,
        private val imageRepository: ImageRepository,
    ) : PostRepository {
        private val tag = "PostRepositoryImpl"

        // table name
        private val users = "users"
        private val posts = "posts"

        // about post
        override suspend fun createPost(
            description: String,
            user: User,
            byteArrayList: List<ByteArray>,
        ): Boolean {
            try {
                val urls: MutableList<String> = mutableListOf()
                for (i in byteArrayList.indices) {
                    urls.add(
                        imageRepository.postImage(
                            byteArrayList[i],
                            fileName = "${user.name}+${LocalDateTime.now()}+$i.png",
                        ),
                    )
                }

                val post =
                    Post(
                        userID = user.userID,
                        name = user.name,
                        description = description,
                        imageURLs = urls.toList(),
                    )

                postgrest
                    .from(posts)
                    .insert(post) {
                        select()
                    }.decodeSingle<Post>()

                return true
            } catch (e: Exception) {
                Log.e(tag, e.toString())
            }
            return false
        }

        override suspend fun onGetUserPosts(userID: String): List<Post> {
            try {
                val result =
                    postgrest
                        .from(posts)
                        .select {
                            filter { Post::userID eq userID }
                            order("create_at", Order.DESCENDING)
                            limit(count = 7)
                        }.decodeList<Post>()
                return result
            } catch (e: Exception) {
                Log.e(tag, e.toString())
                throw e
            }
        }

        override suspend fun onGetPost(postID: Int): Post {
            try {
                val result =
                    postgrest
                        .from(posts)
                        .select {
                            filter {
                                Post::id eq postID
                            }
                        }.decodeSingle<Post>()
                return result
            } catch (e: Exception) {
                Log.e(tag, "failed to get post $e")
                throw e
            }
        }

        override suspend fun onGetNewPost(): Post {
            try {
                val result =
                    postgrest
                        .from(posts)
                        .select {
                            order("create_at", Order.DESCENDING)
                        }.decodeSingle<Post>()
                return result
            } catch (e: Exception) {
                Log.e(tag, "failed to get new post $e")
                throw e
            }
        }

        override suspend fun onGetBeforePosts(afterPostCreateAt: LocalDateTime): List<Post> {
            try {
                val result =
                    postgrest
                        .from(posts)
                        .select {
                            order("create_at", Order.DESCENDING)
                            filter {
                                lt("create_at", afterPostCreateAt)
                            } // targetPost より古い投稿をフィルタリング
                            limit(7) // その中で最新の1件を取得
                        }.decodeList<Post>()
                val list = result.minus(result.first())
                Log.d("PostRepository", "$result")
                return list
            } catch (e: Exception) {
                Log.e(tag, "failed to get new post $e")
                throw e
            }
        }

        override suspend fun onGetNewPosts(): List<Post> {
            try {
                val result =
                    postgrest
                        .from(posts)
                        .select {
                            order("create_at", Order.DESCENDING)
                            limit(count = 7)
                        }.decodeList<Post>()
                return result
            } catch (e: Exception) {
                Log.e(tag, "failed to get new posts $e")
                throw e
            }
        }

        override suspend fun onGetTrendPostOfWeek(limit: Long): List<Post> {
            try {
                val yesterday = LocalDateTime.now().minusDays(7)

                val result =
                    postgrest
                        .from(posts)
                        .select {
                            filter {
                                // 今日の投稿を取得
                                gte("update_at", yesterday)
                            }
                            order("view_count", Order.DESCENDING)
                            limit(count = limit)
                        }.decodeList<Post>()

                return result
            } catch (e: Exception) {
                Log.e(tag, "153: $e")
                throw e
            }
        }

        override suspend fun onGetTrendPostOfMonth(limit: Long): List<Post> {
            try {
                val month = LocalDateTime.now().minusDays(30)

                val result =
                    postgrest
                        .from(posts)
                        .select {
                            filter {
                                // 今日の投稿を取得
                                gte("update_at", month)
                            }
                            order("view_count", Order.DESCENDING)
                            limit(count = limit)
                        }.decodeList<Post>()

                return result
            } catch (e: Exception) {
                Log.e(tag, "190: $e")
                throw e
            }
        }

        override suspend fun onGetTrendPostOfYear(limit: Long): List<Post> {
            try {
                val year = LocalDateTime.now().minusDays(365)

                val result =
                    postgrest
                        .from(posts)
                        .select {
                            filter {
                                // 今日の投稿を取得
                                gte("update_at", year)
                            }
                            order("view_count", Order.DESCENDING)
                            limit(count = limit)
                        }.decodeList<Post>()

                return result
            } catch (e: Exception) {
                Log.e(tag, "213: $e")
                throw e
            }
        }

        override suspend fun onGetTrendPostOfToday(limit: Long): List<Post> {
            try {
                val yesterday = LocalDateTime.now().minusDays(7)

                val result =
                    postgrest
                        .from(posts)
                        .select {
                            filter {
                                // 今日の投稿を取得
                                gte("update_at", yesterday)
                            }
                            order("view_count", Order.DESCENDING)
                            limit(count = limit)
                        }.decodeList<Post>()

                return result
            } catch (e: Exception) {
                Log.e(tag, "153: $e")
                throw e
            }
        }

        override suspend fun onIncrementView(post: Post) {
            try {
                postgrest.from(posts).update({
                    Post::viewCount setTo (post.viewCount + 1)
                }) {
                    filter {
                        Post::id eq post.id
                    }
                }
            } catch (e: Exception) {
                Log.e(tag, "failed to increment view $e")
                throw e
            }
        }

        override suspend fun onUpdatePost(post: Post) {
            try {
                postgrest.from(posts).update(post)
            } catch (e: Exception) {
                Log.e(tag, e.toString())
            }
        }

        override suspend fun onDeletePost(post: Post) {
            try {
                postgrest.from(posts).delete {
                    filter { Post::id eq post.id }
                }
                for (url in post.imageURLs) {
                    imageRepository.deleteImage(url)
                }
            } catch (e: Exception) {
                Log.e(tag, e.toString())
            }
        }

        // about like

        override suspend fun onLike(
            post: Post,
            user: User,
        ) {
            try {
                // increment like count
                postgrest.from(posts).update({
                    Post::likeCount setTo (post.likeCount)
                }) {
                    filter {
                        Post::id eq post.id
                    }
                }

                // add like list of user
                val updatedUser = user.copy(likes = user.likes.plus(post.id))

                postgrest.from(users).update({
                    val columnName = "post_like_id_list"
                    set(columnName, updatedUser.likes)
                }) {
                    filter {
                        User::id eq updatedUser.id
                    }
                }

                Log.d(tag, "success like")
            } catch (e: Exception) {
                Log.e(tag, "like: $e")
            }
        }

        override suspend fun onUnLike(
            post: Post,
            user: User,
        ) {
            try {
                // decrement like count
                postgrest.from(posts).update({
                    Post::likeCount setTo post.likeCount
                }) {
                    filter {
                        Post::id eq post.id
                    }
                }

                // remove like list of user
                val updatedUser = user.copy(likes = user.likes.minus(post.id))

                postgrest.from(users).update({
                    val columnName = "post_like_id_list"
                    set(columnName, updatedUser.likes)
                }) {
                    filter {
                        User::id eq updatedUser.id
                    }
                }

                Log.d(tag, "success c like")
            } catch (e: Exception) {
                Log.e(tag, "cancel like: $e")
            }
        }

        // about repost

        override suspend fun onRepost(
            post: Post,
            user: User,
        ) {
            try {
                // increment post count
                postgrest.from(posts).update({
                    Post::repostCount setTo post.repostCount
                }) {
                    filter {
                        Post::id eq post.id
                    }
                }

                // add repost list of user
                val updatedUser = user.copy(reposts = user.reposts.plus(post.id))

                postgrest.from(users).update({
                    val columnName = "post_repost_id_list"
                    set(columnName, updatedUser.reposts)
                }) {
                    filter {
                        User::id eq updatedUser.id
                    }
                }
            } catch (e: Exception) {
                Log.e(tag, e.toString())
            }
        }

        override suspend fun onUnRepost(
            post: Post,
            user: User,
        ) {
            try {
                // decrement post count
                postgrest.from(posts).update({
                    Post::repostCount setTo post.repostCount
                }) {
                    filter {
                        Post::id eq post.id
                    }
                }

                // add repost list of user
                val updatedUser = user.copy(reposts = user.reposts.minus(post.id))

                postgrest.from(users).update({
                    val columnName = "post_repost_id_list"
                    set(columnName, updatedUser.reposts)
                }) {
                    filter {
                        User::id eq updatedUser.id
                    }
                }
            } catch (e: Exception) {
                Log.e(tag, e.toString())
            }
        }

        // about comment

        override suspend fun onComment(
            post: Post,
            comment: Post,
            user: User,
        ) {
            TODO("Not yet implemented")
        }

        override suspend fun onUnComment(
            post: Post,
            comment: Post,
            user: User,
        ) {
            TODO("Not yet implemented")
        }
    }
