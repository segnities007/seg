package com.segnities007.seg.domain.repository

import com.segnities007.seg.data.model.UserInfo

interface UserInfoRepository {
    suspend fun createUser(user: UserInfo): Boolean
    suspend fun getUser(id: String): UserInfo
    suspend fun updateUser(user: UserInfo): UserInfo
    suspend fun deleteUser(id: Int): Boolean
}