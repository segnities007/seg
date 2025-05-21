package com.example.data.repository

import android.util.Log
import com.example.data.module.PostDto
import com.example.data.module.toPost
import com.example.domain.model.post.Genre
import com.example.domain.model.post.Post
import com.example.domain.model.user.User
import com.example.domain.repository.PostRepository
import com.example.domain.repository.UserRepository
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
            genre: Genre,
        ): Boolean {
            try {
                val post =
                    PostDto(
                        userID = user.userID,
                        name = user.name,
                        description = description,
                        iconURL = user.iconURL,
                        genre = genre.name,
                    )

                val result =
                    postgrest
                        .from(posts)
                        .insert(post) {
                            select()
                        }.decodeSingle<PostDto>()

                val updatedUser = user.copy(posts = user.posts.plus(result.id))

                userRepository.onUpdatePostsOfUser(updatedUser)

                return true
            } catch (e: Exception) {
                Log.e(tag, "failed onCreatePost $e")
            }
            return false
        }

        override suspend fun onCreateComment(
            description: String,
            self: User,
            commentedPost: Post,
        ): Boolean {
            try {
                val comment =
                    Post(
                        userID = self.userID,
                        name = self.name,
                        description = description,
                        iconURL = self.iconURL,
                    )

                val result =
                    postgrest
                        .from(posts)
                        .insert(comment) {
                            select()
                        }.decodeSingle<Post>()

                val updatedSelf = self.copy(posts = self.posts.plus(result.id))
                userRepository.onUpdateUser(updatedSelf)

                val newCommentedPost =
                    commentedPost.copy(commentIDs = commentedPost.commentIDs.plus(result.id))
                onUpdatePost(newCommentedPost)

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
                            filter { PostDto::userID eq userID }
                            order("create_at", Order.DESCENDING)
                            limit(count = 7)
                        }.decodeList<PostDto>()
                val posts = result.map { it.toPost() }
                return posts
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
                                PostDto::userID eq userID
                                lt("create_at", updateAt)
                            }
                            order("create_at", Order.DESCENDING)
                            limit(count = count)
                        }.decodeList<PostDto>()
                if (result.isEmpty()) return listOf()

                val list = result.minus(result.first())
                val posts = list.map { it.toPost() }

                return posts
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
                                PostDto::id eq postID
                            }
                        }.decodeList<PostDto>()

                // The process of removing deleted and nonexistent Posts from its own list.
                if (result.isEmpty()) {
                    var user = userRepository.onGetUser()
                    user =
                        user.copy(
                            posts = user.posts.minus(postID),
                            likes = user.likes.minus(postID),
                            reposts = user.reposts.minus(postID),
                        )
                    userRepository.onUpdateUser(user)
                }

                return if (result.isNotEmpty()) result.first().toPost() else Post()
            } catch (e: Exception) {
                Log.e(tag, "failed onGetPost $e")
                throw e
            }
        }

        override suspend fun onGetComment(commentID: Int): Post {
            try {
                val result =
                    postgrest
                        .from(posts)
                        .select {
                            filter {
                                PostDto::id eq commentID
                            }
                        }.decodeList<PostDto>()

                return if (result.isNotEmpty()) result.first().toPost() else Post()
            } catch (e: Exception) {
                Log.e(tag, "failed onGetComment $e")
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

        override suspend fun onGetComments(comment: Post): List<Post> {
            try {
                val list: MutableList<Post> = mutableListOf()
                for (id in comment.commentIDs) {
                    list.add(onGetPost(id))
                }

                // Removing comment from post's comments if comment was deleted.
                if (list.any { it.id == 0 }) {
                    list.removeIf { it.id == 0 }
                    val commentIDs = list.map { it.id }
                    val updatedComment = comment.copy(commentIDs = commentIDs.toList())
                    onUpdatePost(updatedComment)
                    return list.toList()
                }

                return list.toList()
            } catch (e: Exception) {
                Log.e(tag, "failed onGetComments $e")
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
                        }.decodeSingle<PostDto>()
                return result.toPost()
            } catch (e: Exception) {
                Log.e(tag, "failed onGetNewPost $e")
                throw e
            }
        }

        override suspend fun onGetNewHaiku(): Post {
            try {
                val result =
                    postgrest
                        .from(posts)
                        .select {
                            filter { PostDto::genre eq Genre.HAIKU.name }
                            order("create_at", Order.DESCENDING)
                        }.decodeSingle<PostDto>()
                return result.toPost()
            } catch (e: Exception) {
                Log.e(tag, "failed onGetNewHaiku $e")
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
                        }.decodeList<PostDto>()
                val list = result.minus(result.first())

                if (result.isEmpty()) return listOf()

                return list.map { it.toPost() }
            } catch (e: Exception) {
                Log.e(tag, "failed onGetBeforePosts $e")
                throw e
            }
        }

        override suspend fun onGetBeforeHaikus(afterPostCreateAt: LocalDateTime): List<Post> {
            try {
                val result =
                    postgrest
                        .from(posts)
                        .select {
                            order("create_at", Order.DESCENDING)
                            filter {
                                PostDto::genre eq Genre.HAIKU.name
                                lt("create_at", afterPostCreateAt)
                            }
                            limit(7)
                        }.decodeList<PostDto>()
                val list = result.minus(result.first())

                if (result.isEmpty()) return listOf()

                return list.map { it.toPost() }
            } catch (e: Exception) {
                Log.e(tag, "failed onGetBeforeHaikus $e")
                throw e
            }
        }

        override suspend fun onGetBeforeTankas(afterPostCreateAt: LocalDateTime): List<Post> {
            try {
                val result =
                    postgrest
                        .from(posts)
                        .select {
                            order("create_at", Order.DESCENDING)
                            filter {
                                PostDto::genre eq Genre.TANKA.name
                                lt("create_at", afterPostCreateAt)
                            }
                            limit(7)
                        }.decodeList<PostDto>()
                val list = result.minus(result.first())

                if (result.isEmpty()) return listOf()

                return list.map { it.toPost() }
            } catch (e: Exception) {
                Log.e(tag, "failed onGetBeforeTankas $e")
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
                        }.decodeList<PostDto>()
                return result.map { it.toPost() }
            } catch (e: Exception) {
                Log.e(tag, "failed onGetNewPosts $e")
                throw e
            }
        }

        override suspend fun onGetNewHaikus(): List<Post> {
            try {
                val result =
                    postgrest
                        .from(posts)
                        .select {
                            filter { PostDto::genre eq Genre.HAIKU.name }
                            order("create_at", Order.DESCENDING)
                            limit(count = 7)
                        }.decodeList<PostDto>()
                return result.map { it.toPost() }
            } catch (e: Exception) {
                Log.e(tag, "failed onGetNewHaikus $e")
                throw e
            }
        }

        override suspend fun onGetNewTankas(): List<Post> {
            try {
                val result =
                    postgrest
                        .from(posts)
                        .select {
                            filter { PostDto::genre eq Genre.TANKA.name }
                            order("create_at", Order.DESCENDING)
                            limit(count = 7)
                        }.decodeList<PostDto>()
                return result.map { it.toPost() }
            } catch (e: Exception) {
                Log.e(tag, "failed onGetNewTankas $e")
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
                        }.decodeList<PostDto>()

                return result.map { it.toPost() }
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
                        }.decodeList<PostDto>()

                return result.map { it.toPost() }
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
                        }.decodeList<PostDto>()

                return result.map { it.toPost() }
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
                        }.decodeList<PostDto>()

                return result.map { it.toPost() }
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
                        }.decodeList<PostDto>()

                return result.map { it.toPost() }
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
                                PostDto::description like "%$keyword%"
                            }
                            order("create_at", Order.DESCENDING)
                            limit(count = count)
                        }.decodeList<PostDto>()
                if (result.isEmpty()) return listOf()

                val list = result.minus(result.first())

                return list.map { it.toPost() }
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
                                PostDto::description like "%$keyword%"
                            }
                            order("view_count", Order.DESCENDING)
                            limit(count = count)
                        }.decodeList<PostDto>()

                return result.map { it.toPost() }
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
                                PostDto::description like "%$keyword%"
                            }
                            order("view_count", Order.DESCENDING)
                            limit(count = count)
                        }.decodeList<PostDto>()

                if (result.isEmpty()) return listOf()

                val list = result.minus(result.first())
                return list.map { it.toPost() }
            } catch (e: Exception) {
                Log.e(tag, "success onGetBeforePostsByKeywordSortedByViewCount $e")
                throw e
            }
        }

        override suspend fun onIncrementView(post: Post) {
            try {
                postgrest.from(posts).update({
                    PostDto::viewCount setTo (post.viewCount + 1)
                }) {
                    filter {
                        PostDto::id eq post.id
                    }
                }
            } catch (e: Exception) {
                Log.e(tag, "failed to increment view $e")
                throw e
            }
        }

        override suspend fun onUpdatePost(post: Post) {
            try {
                postgrest.from(posts).update(post) {
                    filter {
                        PostDto::id eq post.id
                    }
                }
            } catch (e: Exception) {
                Log.e(tag, "failed onUpdatePost: $e")
            }
        }

        override suspend fun onDeletePost(post: Post) {
            try {
                postgrest.from(posts).delete {
                    filter { PostDto::id eq post.id }
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
                    PostDto::likeCount setTo (post.likeCount)
                }) {
                    filter {
                        PostDto::id eq post.id
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
                    PostDto::likeCount setTo post.likeCount
                }) {
                    filter {
                        PostDto::id eq post.id
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
                    PostDto::repostCount setTo post.repostCount
                }) {
                    filter {
                        PostDto::id eq post.id
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
                    PostDto::repostCount setTo post.repostCount
                }) {
                    filter {
                        PostDto::id eq post.id
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
