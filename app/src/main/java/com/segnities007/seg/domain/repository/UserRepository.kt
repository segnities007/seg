package com.segnities007.seg.domain.repository

import com.segnities007.seg.data.model.User

interface UserRepository {
    suspend fun createUser(user: User): Boolean
    suspend fun getUser(): User
    suspend fun updateUser(user: User): Boolean
    suspend fun deleteUser(id: String): Boolean
}

