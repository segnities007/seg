package com.segnities007.seg.data.repository

import android.util.Log
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.repository.UserRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: Auth,
    private val postgrest: Postgrest,
): UserRepository{

    private val tag = "UserRepository"
    private val tableName = "users"

    override fun confirmEmail(): Boolean {
        return try {
            val currentUser = auth.currentUserOrNull()
            currentUser?.emailConfirmedAt != null
        } catch (e: Exception) {
            Log.e(tag,"Error checking email confirmation: $e")
            false
        }
    }

    override suspend fun createUser(user: User): Boolean {
        auth.awaitInitialization()
        val id = auth.currentUserOrNull()?.id
        val user = user.copy(id = id.toString())
        try {
            postgrest.from(tableName).insert(user)
            return true
        } catch (e: Exception){
            Log.d("UserRepositoryImpl39", "error $e")
            return false
        }
    }

    override suspend fun getUser(): User {
        try {
            auth.awaitInitialization()
            val id = auth.currentUserOrNull()?.id
            val result = postgrest.from(tableName).select {
                    filter { User::id eq id.toString() }
                }
                .decodeSingle<User>()

            return result
        } catch (e: Exception){
            Log.e(tag, "failed to get user. error message is $e")
            throw Exception()
        }
    }

    override suspend fun getOtherUser(userID: String): User{
        try {
            val result = postgrest.from(tableName).select(
                    columns = Columns.list("name,user_id,birthday,is_prime,icon_id,follow_user_id_list,follow_count,follower_user_id_list,follower_count,create_at,post_id_list".trimIndent())
                ) {
                    filter { User::userID eq userID }
                }
                .decodeSingle<User>()

            return result
        } catch (e: Exception){
            Log.e(tag, "failed to get other user. error message is $e")
            throw Exception()
        }
    }

    override suspend fun updateUser(user: User): Boolean {
        try {
            postgrest.from(tableName).update<User>(user) {
                filter { User::id eq user.id }
            }
            return true
        } catch (e: Exception){
            Log.e(tag, "failed to update user. error message is $e")
        }
        return false
    }

    override suspend fun deleteUser(id: String): Boolean {
        try {
            postgrest.from(tableName).delete {
                select()
                filter { eq("id", id) }
            }.decodeSingle<User>()
            return true
        } catch (e: Exception){
            Log.e(tag, "failed to delete user. error message is $e")
        }
        return false
    }

}