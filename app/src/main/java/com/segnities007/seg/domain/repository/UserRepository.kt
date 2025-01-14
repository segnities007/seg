package com.segnities007.seg.domain.repository

import com.segnities007.seg.data.model.User

interface UserRepository {
    fun confirmEmail(): Boolean
    suspend fun createUser(user: User)
    suspend fun getOtherUser(userID: String): User
    suspend fun getUser(): User
    suspend fun getUsers(userIDs: List<String>): List<User>
    suspend fun updateUser(user: User)
    suspend fun deleteUser(id: String)
    suspend fun followUser(myself: User, other: User)
    suspend fun onIncrementFollowCount(user: User)
    suspend fun onIncrementFollowerCount(user: User)
}

