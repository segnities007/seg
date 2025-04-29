package com.example.data.repository

import android.util.Log
import com.example.domain.repository.AuthRepository
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Email
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val auth: Auth,
    ) : AuthRepository {
        private val tag = "AuthRepositoryImpl" // tag for log

        override suspend fun hasLogged(
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

        override suspend fun logout() {
            try {
                auth.signOut()
            } catch (e: Exception) {
                Log.e(tag, "failed logout $e")
                throw e
            }
        }

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
                Log.e(tag, "failed signInWithEmailPassword $e")
                false
            }

        override suspend fun signUpWithEmailPassword(
            email: String,
            password: String,
        ): Boolean {
            return try {
                val result =
                    auth.signUpWith(Email) {
                        this.email = email
                        this.password = password
                    }
                if (result?.identities?.isEmpty() == true) {
                    return false
                }
                true
            } catch (e: Exception) {
                Log.e(tag, "failed signUpWithEmailPassword $e")
                false
            }
        }
    }
