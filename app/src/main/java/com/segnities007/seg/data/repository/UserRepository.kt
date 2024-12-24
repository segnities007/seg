package com.segnities007.seg.data.repository

import com.segnities007.seg.domain.model.User

interface UserRepository {
    suspend fun createUser(user: User): Boolean
    suspend fun getUser(id: String): User
    suspend fun updateUser(user: User): User
    suspend fun deleteUser(id: String): Boolean
}

