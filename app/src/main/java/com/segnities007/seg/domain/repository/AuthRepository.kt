package com.segnities007.seg.domain.repository

import android.content.Context
import io.github.jan.supabase.SupabaseClient
import kotlinx.coroutines.CoroutineScope

interface AuthRepository {
    suspend fun loginWithGoogle(
        context: Context,
    ): Boolean

    suspend fun signInWithEmailPassword(
        email: String,
        password: String,
    ): Boolean

    suspend fun signUpWithEmailPassword(
        email: String,
        password: String,
    ): Boolean
}