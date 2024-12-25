package com.segnities007.seg.domain.repository

import com.segnities007.seg.data.model.Follower

interface FollowerRepository {
    suspend fun createFollower(follower: Follower): Boolean
    suspend fun getFollower(id: Int): Follower
//    suspend fun updateFollower(follower: Follower): Boolean maybe not use
    suspend fun deleteFollower(id: Int): Boolean
}