package com.segnities007.seg.data.repository

import android.util.Log
import androidx.navigation.NavHostController
import com.segnities007.seg.Hub
import com.segnities007.seg.Login
import com.segnities007.seg.domain.repository.AuthRepository
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Email
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: Auth,
) : AuthRepository {

    private val tag = "AuthRepositoryImpl"

    //前回ログインしていたかを確認
    suspend fun hasLogged(
        navController: NavHostController
    ) {
        auth.awaitInitialization()
        val currentUser = auth.currentUserOrNull()
        if (currentUser != null) {
            navController.navigate(route = Hub)
        } else {
            navController.navigate(route = Login)
        }
    }

    suspend fun logout(){
        auth.signOut()
    }

    //EmailとPasswordを使用してサインインする
    override suspend fun signInWithEmailPassword(
        email: String,
        password: String,
    ): Boolean {
        return try {
            auth.signInWith(Email){
                this.email = email
                this.password = password
            }
            true
        } catch (e: Exception){
            Log.e(tag, "$e")
            false
        }
    }

    //EmailとPasswordを使用してサインアップする
    override suspend fun signUpWithEmailPassword(
        email: String,
        password: String,
    ): Boolean {
        return try {
            auth.signUpWith(Email){
                this.email = email
                this.password = password
            }
            true

        } catch (e: Exception){
            Log.e(tag, "$e")
            false
        }
    }
}
