package com.segnities007.seg.domain.repository

import com.segnities007.seg.data.model.User
import java.time.LocalDateTime

interface UserRepository {
    fun confirmEmail(): Boolean

    suspend fun onCreateUser(user: User)

    suspend fun onCreateUserWithIcon(user: User, path: String, byteArray: ByteArray,)

    suspend fun getOtherUser(userID: String): User

    suspend fun getUser(): User

    suspend fun getUsers(userIDs: List<String>): List<User>

    suspend fun onGetUsersByKeyword(keyword: String): List<User>

    suspend fun onGetBeforeUsersByKeyword(
        keyword: String,
        afterUserCreateAt: LocalDateTime,
    ): List<User>

    suspend fun updateUser(user: User)

    suspend fun deleteUser(id: String)

    suspend fun followUser(
        myself: User,
        other: User,
    )

    suspend fun unFollowUser(
        myself: User,
        other: User,
    )

    suspend fun onIncrementFollowCount(user: User)

    suspend fun onDecrementFollowCount(user: User)

    suspend fun onIncrementFollowerCount(user: User)

    suspend fun onDecrementFollowerCount(user: User)
}
