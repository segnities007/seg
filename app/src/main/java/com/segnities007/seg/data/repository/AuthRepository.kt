package com.segnities007.seg.data.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth

interface AuthRepository {
    fun provideSupabaseAuth(client: SupabaseClient): Auth
}