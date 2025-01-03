package com.segnities007.seg.data.repository

import android.util.Log
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.repository.UserRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
): UserRepository{

    fun confirmEmail(): Boolean {
        return try {
            val currentUser = supabaseClient.auth.currentUserOrNull()
            currentUser?.emailConfirmedAt != null
        } catch (e: Exception) {
            Log.e("UserRepositoryImpl.kt","Error checking email confirmation: ${e.message}")
            false // エラーが発生した場合は未確認とみなす
        }
    }

    override suspend fun createUser(user: User): Boolean {
        supabaseClient.auth.awaitInitialization()
        val id = supabaseClient.auth.currentUserOrNull()?.id
        if(id == null){
            Log.e("UserRepository", "failed to create user. id is null")
            return false
        }
        Log.d("UserRepository", "1")
        val user = user.copy(id = id.toString())
        Log.d("UserRepository", "2")
        val tableName = "users"
        Log.d("UserRepository", "3")
        try {
            supabaseClient
                .from(tableName)
                .insert(user)
            return true
        } catch (e: Exception){
            Log.d("UserRepositoryImpl39", "error $e")
            return false
        }
    }

    override suspend fun getUser(): User {

        val tableName = "users"
        try {
            val result = supabaseClient
                .from(tableName)
                .select { filter { User::id eq supabaseClient.auth.currentUserOrNull()?.id.toString() } }
                .decodeSingle<User>()
            return result
        } catch (e: Exception){
            Log.e("UserRepository", "failed to get user. error message is $e")
            throw Exception()
        }
    }

    override suspend fun updateUser(user: User): Boolean {
        val tableName = "users"
        try {
            supabaseClient
                .from(tableName)
                .update({ set("user", user) }) {
                    filter { User::id eq user.id }
                }
            return true
        } catch (e: Exception){
            Log.e("UserRepository", "failed to update user. error message is $e")
        }
        return false
    }

    override suspend fun deleteUser(id: String): Boolean {
        val tableName = "users"
        try {
            supabaseClient
                .from(tableName)
                .delete {
                    select()
                    filter { eq("id", id) }
                }.decodeSingle<User>()
            return true
        } catch (e: Exception){
            Log.e("UserRepository", "failed to delete user. error message is $e")
        }
        return false
    }

}