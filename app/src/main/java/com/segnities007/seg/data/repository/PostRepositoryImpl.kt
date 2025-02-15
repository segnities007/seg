package com.segnities007.seg.data.repository

import android.util.Log
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.domain.repository.UserRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Order
import java.time.LocalDateTime
import javax.inject.Inject

class PostRepositoryImpl
    @Inject
    constructor(
        private val postgrest: Postgrest,
        private val userRepository: UserRepository,
    ) : PostRepository {
        private val tag = "PostRepositoryImpl"

        // table name
        private val users = "users"
        private val posts = "posts"

        // about post
        override suspend fun onCreatePost(
            description: String,
            user: User,
        ): Boolean {
            try {
                val post =
                    Post(
                        userID = user.userID,
                        name = user.name,
                        description = description,
                        iconURL = user.iconURL,
                    )

                val result =
                    postgrest
                        .from(posts)
                        .insert(post) {
                            select()
                        }.decodeSingle<Post>()

                val updatedUser = user.copy(posts = user.posts.plus(result.id))

                userRepository.onUpdatePostsOfUser(updatedUser)

                return true
            } catch (e: Exception) {
                Log.e(tag, "failed onCreatePost $e")
            }
            return false
        }

        override suspend fun onGetPostsOfUser(userID: String): List<Post> {
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
                Log.e(tag, "failed onGetPostsOfUser $e")
                throw e
            }
        }

        override suspend fun onGetBeforePostsOfUser(
            userID: String,
            updateAt: LocalDateTime,
        ): List<Post> {
            try {
                val count: Long = 7

                val result =
                    postgrest
                        .from(posts)
                        .select {
                            filter {
                                Post::userID eq userID
                                lt("create_at", updateAt)
                            }
                            order("create_at", Order.DESCENDING)
                            limit(count = count)
                        }.decodeList<Post>()
                if (result.isEmpty()) return result

                val list = result.minus(result.first())

                return list
            } catch (e: Exception) {
                Log.e(tag, "failed onGetBeforePostsOfUser $e")
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
                        }.decodeList<Post>()

                if (result.isEmpty()) {
                    var user = userRepository.onGetUser()
                    user =
                        user.copy(posts = user.posts.minus(postID), likes = user.likes.minus(postID), reposts = user.reposts.minus(postID))
                    userRepository.onUpdateUser(user)
                }

                return if (result.isNotEmpty()) result.first() else Post()
            } catch (e: Exception) {
                Log.e(tag, "failed onGetPost $e")
                throw e
            }
        }

        override suspend fun onGetPosts(postIDs: List<Int>): List<Post> {
            try {
                val list: MutableList<Post> = mutableListOf()
                for (id in postIDs) {
                    list.add(onGetPost(id))
                }

                return list.toList()
            } catch (e: Exception) {
                Log.e(tag, "failed onGetPosts $e")
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
                Log.e(tag, "failed onGetNewPost $e")
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

                if (result.isEmpty()) return result

                return list
            } catch (e: Exception) {
                Log.e(tag, "failed onGetBeforePosts $e")
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
                Log.e(tag, "failed onGetNewPosts $e")
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
                val yesterday = LocalDateTime.now().minusDays(1)

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

        override suspend fun onGetPostsByKeyword(keyword: String): List<Post> {
            try {
                val count: Long = 10

                val result =
                    postgrest
                        .from(posts)
                        .select {
                            filter {
                                Post::description like "%$keyword%"
                            }
                            order("create_at", Order.DESCENDING)
                            limit(count = count)
                        }.decodeList<Post>()

                return result
            } catch (e: Exception) {
                Log.e(tag, "failed onGetPostsByKeyword $e")
                throw e
            }
        }

        override suspend fun onGetBeforePostsByKeyword(
            keyword: String,
            afterPostCreateAt: LocalDateTime,
        ): List<Post> {
            try {
                val count: Long = 10

                val result =
                    postgrest
                        .from(posts)
                        .select {
                            filter {
                                lt("create_at", afterPostCreateAt)
                                Post::description like "%$keyword%"
                            }
                            order("create_at", Order.DESCENDING)
                            limit(count = count)
                        }.decodeList<Post>()
                if (result.isEmpty()) return result

                val list = result.minus(result.first())

                return list
            } catch (e: Exception) {
                Log.e(tag, "failed onGetBeforePostsByKeyword $e")
                throw e
            }
        }

        override suspend fun onGetPostsByKeywordSortedByViewCount(keyword: String): List<Post> {
            try {
                val count: Long = 10

                val result =
                    postgrest
                        .from(posts)
                        .select {
                            filter {
                                Post::description like "%$keyword%"
                            }
                            order("view_count", Order.DESCENDING)
                            limit(count = count)
                        }.decodeList<Post>()

                return result
            } catch (e: Exception) {
                Log.e(tag, "failed onGetPostsByKeywordSortedByViewCount $e")
                throw e
            }
        }

        override suspend fun onGetBeforePostsByKeywordSortedByViewCount(
            keyword: String,
            viewCount: Int,
        ): List<Post> {
            try {
                val count: Long = 10

                val result =
                    postgrest
                        .from(posts)
                        .select {
                            filter {
                                lt("view_count", viewCount)
                                Post::description like "%$keyword%"
                            }
                            order("view_count", Order.DESCENDING)
                            limit(count = count)
                        }.decodeList<Post>()

                if (result.isEmpty()) return result

                val list = result.minus(result.first())
                return list
            } catch (e: Exception) {
                Log.e(tag, "success onGetBeforePostsByKeywordSortedByViewCount $e")
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

                Log.d(tag, "success unlike")
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

                Log.d(tag, "success repost")
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

                Log.d(tag, "success un repost")
            } catch (e: Exception) {
                Log.e(tag, e.toString())
            }
        }
    }
