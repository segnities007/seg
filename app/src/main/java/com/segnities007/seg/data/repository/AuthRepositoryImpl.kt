package com.segnities007.seg.data.repository

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.navigation.NavHostController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.segnities007.seg.Hub
import com.segnities007.seg.Login
import com.segnities007.seg.domain.repository.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.exceptions.RestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient ,
) : AuthRepository {

    //前回ログインしていたかを確認
    suspend fun hasLogged(
        navController: NavHostController
    ) {
        supabaseClient.auth.awaitInitialization()
        val currentUser = supabaseClient.auth.currentUserOrNull()
        Log.d("SplashAuth", "currentUser is $currentUser")
        if (currentUser != null) {
            navController.navigate(route = Hub)
        } else {
            navController.navigate(route = Login)
        }
    }

    //EmailとPasswordを使用してサインインする
    override suspend fun signInWithEmailPassword(
        email: String,
        password: String,
    ): Boolean {
        return try {
            supabaseClient.auth.signInWith(Email){
                this.email = email
                this.password = password
            }
            true
        } catch (e: Exception){
            Log.e("AuthRepositoryImpl", "$e")
            false
        }
    }

    //EmailとPasswordを使用してサインアップする
    override suspend fun signUpWithEmailPassword(
        email: String,
        password: String,
    ): Boolean {
        return try {
            supabaseClient.auth.signUpWith(Email){
                this.email = email
                this.password = password
            }
            true
        } catch (e: Exception){
            Log.e("AuthRepositoryImpl", "$e")
            false
        }
    }

    //googleアカウントを使用してサインインorサインアップする
    override suspend fun loginWithGoogle(
        context: Context,
    ): Boolean = withContext(Dispatchers.IO) {
        val credentialManager = CredentialManager.create(context)

        // nonce を生成して SHA-256 でハッシュ化
        val rawNonce = UUID.randomUUID().toString()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(rawNonce.toByteArray())
        val hashedNonce = digest.joinToString("") { "%02x".format(it) }

        // Google ID 取得オプションを作成
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("WEB_GOOGLE_CLIENT_ID")  // 実際のクライアントIDを指定
            .setNonce(hashedNonce)
            .build()

        // Credential リクエストを作成
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return@withContext try {
            // Credential を取得 (この呼び出しはブロックまたは内部でサスペンドする想定)
            val result = credentialManager.getCredential(request = request, context = context)

            // 取得した Credential から Google ID Token を取り出す
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(result.credential.data)
            val googleIdToken = googleIdTokenCredential.idToken

            // Supabase で ID Token 認証を実行
            supabaseClient.auth.signInWith(IDToken) {
                idToken = googleIdToken
                provider = Google
                nonce = rawNonce
            }

            true

        } catch (e: GetCredentialException) {
            Log.e("AuthRepositoryImpl1", "$e")
            false
        } catch (e: GoogleIdTokenParsingException) {
            Log.e("AuthRepositoryImpl2", "$e")
            false
        } catch (e: RestException) {
            Log.e("AuthRepositoryImpl3", "$e")
            false
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl4", "$e")
            false
        }
    }
}
