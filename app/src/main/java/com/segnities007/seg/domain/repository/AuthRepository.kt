package com.segnities007.seg.domain.repository

interface AuthRepository {

    suspend fun signInWithEmailPassword(
        email: String,
        password: String,
    ): Boolean

    suspend fun signUpWithEmailPassword(
        email: String,
        password: String,
    ): Boolean
}