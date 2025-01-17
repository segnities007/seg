package com.segnities007.seg.data.repository

import android.util.Log
import com.segnities007.seg.domain.repository.AuthRepository
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Email
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val auth: Auth,
    ) : AuthRepository {
        private val tag = "AuthRepositoryImpl"

        // 前回ログインしていたかを確認
        suspend fun hasLogged(
            onNavigateToLogin: () -> Unit,
            onNavigateToHost: () -> Unit,
        ) {
            auth.awaitInitialization()
            val currentUser = auth.currentUserOrNull()

            if (currentUser != null) {
                onNavigateToHost()
            } else {
                onNavigateToLogin()
            }
        }

        suspend fun logout() {
            auth.signOut()
        }

        // EmailとPasswordを使用してサインインする
        override suspend fun signInWithEmailPassword(
            email: String,
            password: String,
        ): Boolean =
            try {
                auth.signInWith(Email) {
                    this.email = email
                    this.password = password
                }
                true
            } catch (e: Exception) {
                Log.e(tag, "$e")
                false
            }

        // EmailとPasswordを使用してサインアップする
        override suspend fun signUpWithEmailPassword(
            email: String,
            password: String,
        ): Boolean =
            try {
                auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                }
                true
            } catch (e: Exception) {
                Log.e(tag, "$e")
                false
            }
    }
