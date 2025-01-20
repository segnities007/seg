package com.segnities007.seg.data.di

import com.segnities007.seg.data.repository.AuthRepositoryImpl
import com.segnities007.seg.data.repository.ImageRepositoryImpl
import com.segnities007.seg.data.repository.PostRepositoryImpl
import com.segnities007.seg.data.repository.StorageRepositoryImpl
import com.segnities007.seg.data.repository.UserRepositoryImpl
import com.segnities007.seg.domain.repository.AuthRepository
import com.segnities007.seg.domain.repository.ImageRepository
import com.segnities007.seg.domain.repository.PostRepository
import com.segnities007.seg.domain.repository.StorageRepository
import com.segnities007.seg.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(auth: Auth): AuthRepository = AuthRepositoryImpl(auth = auth)

    @Provides
    @Singleton
    fun provideUserRepository(
        auth: Auth,
        postgrest: Postgrest,
    ): UserRepository = UserRepositoryImpl(auth = auth, postgrest = postgrest)

    @Provides
    @Singleton
    fun provideImageRepository(
        postgrest: Postgrest,
        storageRepository: StorageRepository,
    ): ImageRepository = ImageRepositoryImpl(storageRepository = storageRepository)

    @Provides
    @Singleton
    fun providePostRepository(
        postgrest: Postgrest,
        imageRepository: ImageRepository,
    ): PostRepository =
        PostRepositoryImpl(
            postgrest = postgrest,
            imageRepository = imageRepository,
        )

    @Provides
    @Singleton
    fun provideStorageRepository(storage: Storage): StorageRepository =
        StorageRepositoryImpl(
            storage = storage,
        )
}
