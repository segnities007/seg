package com.example.core_di

import com.example.data.repository.AuthRepositoryImpl
import com.example.data.repository.ImageRepositoryImpl
import com.example.data.repository.PostRepositoryImpl
import com.example.data.repository.StorageRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.ImageRepository
import com.example.domain.repository.PostRepository
import com.example.domain.repository.StorageRepository
import com.example.domain.repository.UserRepository
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
    fun provideImageRepository(storageRepository: StorageRepository): ImageRepository =
        ImageRepositoryImpl(storageRepository = storageRepository)

    @Provides
    @Singleton
    fun provideUserRepository(
        auth: Auth,
        postgrest: Postgrest,
        imageRepository: ImageRepository,
    ): UserRepository =
        UserRepositoryImpl(
            auth = auth,
            postgrest = postgrest,
            imageRepository = imageRepository,
        )

    @Provides
    @Singleton
    fun providePostRepository(
        postgrest: Postgrest,
        userRepository: UserRepository,
    ): PostRepository =
        PostRepositoryImpl(
            postgrest = postgrest,
            userRepository = userRepository,
        )

    @Provides
    @Singleton
    fun provideStorageRepository(storage: Storage): StorageRepository =
        StorageRepositoryImpl(
            storage = storage,
        )
}
