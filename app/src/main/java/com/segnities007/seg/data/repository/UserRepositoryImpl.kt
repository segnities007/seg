package com.segnities007.seg.data.repository

import android.util.Log
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.repository.UserRepository
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(
        private val auth: Auth,
        private val postgrest: Postgrest,
    ) : UserRepository {
        private val tag = "UserRepository"
        private val tableName = "users"

        override fun confirmEmail(): Boolean {
            try {
                val currentUser = auth.currentUserOrNull()
                return currentUser?.emailConfirmedAt != null
            } catch (e: Exception) {
                Log.e(tag, "Error checking email confirmation: $e")
            }
            return false
        }

        override suspend fun createUser(user: User) {
            auth.awaitInitialization()
            val id = auth.currentUserOrNull()?.id
            val updatedUser = user.copy(id = id.toString())
            try {
                postgrest.from(tableName).insert(updatedUser)
            } catch (e: Exception) {
                Log.d(tag, "error $e")
            }
        }

        override suspend fun getUser(): User {
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
                Log.e(tag, "failed to get user. error message is $e")
                throw Exception()
            }
        }

        override suspend fun getUsers(userIDs: List<String>): List<User> {
            val users: MutableList<User> = mutableListOf()
            for (id in userIDs) {
                users.add(getOtherUser(id))
            }
            return users.toList()
        }

        override suspend fun getOtherUser(userID: String): User {
            try {
                val result =
                    postgrest
                        .from(tableName)
                        .select(
                            columns =
                                Columns.list(
                                    (
                                        "name," +
                                            "user_id," +
                                            "birthday," +
                                            "is_prime," +
                                            "icon_url," +
                                            "follow_user_id_list," +
                                            "follow_count," +
                                            "follower_user_id_list," +
                                            "follower_count," +
                                            "create_at," +
                                            "post_id_list"
                                    ).trimIndent(),
                                ),
                        ) {
                            filter { User::userID eq userID }
                        }.decodeSingle<User>()

                return result
            } catch (e: Exception) {
                Log.e(tag, "failed to get other user. error message is $e")
                throw Exception()
            }
        }

        override suspend fun updateUser(user: User) {
            try {
                postgrest.from(tableName).update<User>(user) {
                    filter { User::id eq user.id }
                }
            } catch (e: Exception) {
                Log.e(tag, "failed to update user. error message is $e")
            }
        }

        override suspend fun deleteUser(id: String) {
            try {
                postgrest
                    .from(tableName)
                    .delete {
                        select()
                        filter { eq("id", id) }
                    }.decodeSingle<User>()
            } catch (e: Exception) {
                Log.e(tag, "failed to delete user. error message is $e")
            }
        }

        override suspend fun followUser(
            myself: User,
            other: User,
        ) {
            try {
                val newMyself = myself.copy(follows = myself.follows.plus(other.userID))
                val newOther = other.copy(followers = other.followers.plus(myself.userID))

                postgrest.from(tableName).update({
                    set("follow_user_id_list", newMyself.follows)
                }) {
                    filter { User::userID eq newMyself.userID }
                }

                postgrest.from(tableName).update({
                    set("follower_user_id_list", newOther.followers)
                }) {
                    filter { User::userID eq newOther.userID }
                }

                onIncrementFollowCount(newMyself)
                onIncrementFollowerCount(newOther)
            } catch (e: Exception) {
                Log.e(tag, "failed to follow user: $e")
            }
        }

        override suspend fun unFollowUser(
            myself: User,
            other: User,
        ) {
            try {
                val newMyself = myself.copy(follows = myself.follows.minus(other.userID))
                val newOther = other.copy(followers = other.followers.minus(myself.userID))
                postgrest.from(tableName).update({
                    set("follow_user_id_list", newMyself.follows)
                }) {
                    filter { User::userID eq newMyself.userID }
                }

                postgrest.from(tableName).update({
                    set("follower_user_id_list", newOther.followers)
                }) {
                    filter { User::userID eq newOther.userID }
                }

                onDecrementFollowCount(newMyself)
                onDecrementFollowerCount(newOther)
            } catch (e: Exception) {
                Log.e(tag, "failed to follow user: $e")
            }
        }

        override suspend fun onIncrementFollowCount(user: User) {
            try {
                postgrest.from(tableName).update({
                    User::followCount setTo user.followCount + 1
                }) {
                    filter { User::userID eq user.userID }
                }
            } catch (e: Exception) {
                Log.e(tag, "failed to follow increment: $e")
            }
        }

        override suspend fun onDecrementFollowCount(user: User) {
            try {
                postgrest.from(tableName).update({
                    User::followCount setTo user.followCount - 1
                }) {
                    filter { User::userID eq user.userID }
                }
            } catch (e: Exception) {
                Log.e(tag, "failed to follow decrement: $e")
            }
        }

        override suspend fun onIncrementFollowerCount(user: User) {
            try {
                postgrest.from(tableName).update({
                    User::followerCount setTo user.followerCount + 1
                }) {
                    filter { User::userID eq user.userID }
                }
            } catch (e: Exception) {
                Log.e(tag, "failed to follower increment: $e")
            }
        }

        override suspend fun onDecrementFollowerCount(user: User) {
            try {
                postgrest.from(tableName).update({
                    User::followerCount setTo user.followerCount - 1
                }) {
                    filter { User::userID eq user.userID }
                }
            } catch (e: Exception) {
                Log.e(tag, "failed to follower decrement: $e")
            }
        }
    }
