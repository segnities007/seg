package com.example.data.repository

import android.util.Log
import com.example.domain.model.user.User
import com.example.domain.repository.ImageRepository
import com.example.domain.repository.UserRepository
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import java.time.LocalDateTime
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(
        private val auth: Auth,
        private val postgrest: Postgrest,
        private val imageRepository: ImageRepository,
    ) : UserRepository {
        private val tag = "UserRepositoryImpl"
        private val tableName = "users"
        private val userColumn =
            (
                "name," + "user_id," + "birthday," + "is_prime," + "icon_url," +
                    "follow_user_id_list," + "follower_user_id_list," + "create_at," + "post_id_list"
            ).trimIndent()

        override fun onConfirmEmail(): Boolean {
            try {
                val currentUser = auth.currentUserOrNull()
                return currentUser?.emailConfirmedAt != null
            } catch (e: Exception) {
                Log.e(tag, "failed onConfirmEmail $e")
            }
            return false
        }

        override suspend fun onCreateUser(user: User) {
            auth.awaitInitialization()
            try {
                val id = auth.currentUserOrNull()?.id
                val updatedUser = user.copy(id = id.toString())
                postgrest.from(tableName).insert(updatedUser)
            } catch (e: Exception) {
                Log.e(tag, "failed onCreateUser $e")
            }
        }

        override suspend fun onCreateUserWithIcon(
            user: User,
            path: String,
            byteArray: ByteArray,
        ) {
            auth.awaitInitialization()
            try {
                val id = auth.currentUserOrNull()?.id
                val url = imageRepository.postAvatarImage(path, byteArray)
                val updatedUser = user.copy(id = id.toString(), iconURL = url)
                postgrest.from(tableName).insert(updatedUser)
            } catch (e: Exception) {
                Log.e(tag, "failed onCreateUserWithIcon $e")
            }
        }

        override suspend fun onGetUser(): User {
            try {
                auth.awaitInitialization()
                val id = auth.currentUserOrNull()?.id
                val result =
                    postgrest
                        .from(tableName)
                        .select {
                            filter { User::id eq id.toString() }
                        }.decodeSingle<User>()

                return result
            } catch (e: Exception) {
                Log.e(tag, "failed onGetUser $e")
                throw Exception()
            }
        }

        override suspend fun onGetUsers(userIDs: List<String>): List<User> {
            val users: MutableList<User> = mutableListOf()
            for (id in userIDs) {
                users.add(onGetOtherUser(id))
            }
            return users.toList()
        }

        override suspend fun onGetUsersByKeyword(keyword: String): List<User> {
            try {
                val result =
                    postgrest
                        .from(tableName)
                        .select(columns = Columns.list(userColumn)) {
                            filter { User::userID like "%$keyword%" }
                            order("create_at", Order.DESCENDING)
                        }.decodeList<User>()

                return result
            } catch (e: Exception) {
                Log.e(tag, "failed onGetUsersByKeyword $e")
                throw e
            }
        }

        override suspend fun onGetBeforeUsersByKeyword(
            keyword: String,
            afterUserCreateAt: LocalDateTime,
        ): List<User> {
            try {
                val result =
                    postgrest
                        .from(tableName)
                        .select(columns = Columns.list(userColumn)) {
                            filter {
                                User::userID like "%$keyword%"
                                lt("create_at", afterUserCreateAt)
                            }
                            order("create_at", Order.DESCENDING)
                        }.decodeList<User>()

                if (result.isEmpty()) return result

                val list = result.minus(result.first())

                return list
            } catch (e: Exception) {
                Log.e(tag, "failed onGetUsersByKeyword $e")
                throw e
            }
        }

        override suspend fun onGetOtherUser(userID: String): User {
            try {
                val result =
                    postgrest
                        .from(tableName)
                        .select(columns = Columns.list(userColumn)) {
                            filter { User::userID eq userID }
                        }.decodeSingle<User>()

                return result
            } catch (e: Exception) {
                Log.e(tag, "failed onGetOtherUser $e")
                throw e
            }
        }

        override suspend fun onUpdateUser(user: User) {
            try {
                postgrest.from(tableName).update<User>(user) {
                    filter { User::id eq user.id }
                }
            } catch (e: Exception) {
                Log.e(tag, "failed onUpdateUser $e")
            }
        }

        override suspend fun onUpdatePostsOfUser(user: User) {
            val posts = "post_id_list"

            try {
                postgrest.from(tableName).update({
                    set(posts, user.posts)
                }) {
                    filter { User::id eq user.id }
                }
            } catch (e: Exception) {
                Log.e(tag, "failed onUpdatePosts $e")
            }
        }

        override suspend fun onDeleteUser(id: String) {
            try {
                postgrest
                    .from(tableName)
                    .delete {
                        select()
                        filter { eq("id", id) }
                    }.decodeSingle<User>()
            } catch (e: Exception) {
                Log.e(tag, "failed onDeleteUser $e")
            }
        }

        override suspend fun onFollowUser(
            myself: User,
            other: User,
        ) {
            val follows = "follow_user_id_list"
            val followers = "follower_user_id_list"

            try {
                val newMyself = myself.copy(follows = myself.follows.plus(other.userID))
                val newOther = other.copy(followers = other.followers.plus(myself.userID))

                postgrest.from(tableName).update({
                    set(follows, newMyself.follows)
                }) {
                    filter { User::userID eq newMyself.userID }
                }

                postgrest.from(tableName).update({
                    set(followers, newOther.followers)
                }) {
                    filter { User::userID eq newOther.userID }
                }
            } catch (e: Exception) {
                Log.e(tag, "onFailed followUser $e")
            }
        }

        override suspend fun onUnFollowUser(
            myself: User,
            other: User,
        ) {
            val follows = "follow_user_id_list"
            val followers = "follower_user_id_list"
            try {
                val newMyself = myself.copy(follows = myself.follows.minus(other.userID))
                val newOther = other.copy(followers = other.followers.minus(myself.userID))
                postgrest.from(tableName).update({
                    set(follows, newMyself.follows)
                }) {
                    filter { User::userID eq newMyself.userID }
                }

                postgrest.from(tableName).update({
                    set(followers, newOther.followers)
                }) {
                    filter { User::userID eq newOther.userID }
                }
            } catch (e: Exception) {
                Log.e(tag, "failed onUnFollowUser $e")
            }
        }
    }
