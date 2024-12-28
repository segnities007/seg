package com.segnities007.seg.data.repository

import android.util.Log
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.repository.UserRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
): UserRepository{

    override suspend fun createUser(user: User): Boolean {
        val fromName = "users"
        try {
            supabaseClient.from(fromName).insert(user)
            return true
        } catch (e: Exception){
            Log.e("UserRepository", "failed to create user. error message is $e")
        }
        return false
    }

    override suspend fun getUser(id: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User): User {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(id: String): Boolean {
        TODO("Not yet implemented")
    }

}