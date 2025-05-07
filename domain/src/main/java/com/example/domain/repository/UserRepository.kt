package com.example.domain.repository

import com.example.domain.model.user.User
import java.time.LocalDateTime

interface UserRepository {
    fun onConfirmEmail(): Boolean

    suspend fun onCreateUser(user: User)

    suspend fun onCreateUserWithIcon(
        user: User,
        path: String,
        byteArray: ByteArray,
    )

    suspend fun onGetOtherUser(userID: String): User

    suspend fun onGetUser(): User

    suspend fun onGetUsers(userIDs: List<String>): List<User>

    suspend fun onGetUsersByKeyword(keyword: String): List<User>

    suspend fun onGetBeforeUsersByKeyword(
        keyword: String,
        afterUserCreateAt: LocalDateTime,
    ): List<User>

    suspend fun onUpdateUser(user: User)

    suspend fun onUpdatePostsOfUser(user: User)

    suspend fun onDeleteUser(id: String)

    suspend fun onFollowUser(
        myself: User,
        other: User,
    )

    suspend fun onUnFollowUser(
        myself: User,
        other: User,
    )
}
