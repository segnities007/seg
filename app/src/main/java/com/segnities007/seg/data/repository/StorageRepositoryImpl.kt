package com.segnities007.seg.data.repository

import android.util.Log
import com.segnities007.seg.domain.repository.StorageRepository
import io.github.jan.supabase.storage.Storage
import javax.inject.Inject

class StorageRepositoryImpl
    @Inject
    constructor(
        private val storage: Storage,
    ) : StorageRepository {
        private val tag = "StorageRepositoryImpl"
        private val bucketName = "avatars"

        override suspend fun postImage(
            fileName: String,
            byteArray: ByteArray,
        ): String {
            try {
                val bucket = storage.from(bucketName)
                bucket.upload(fileName, byteArray) {
                    upsert = false
                }
                val url = storage.from(bucketName).publicUrl(fileName)

                return url
            } catch (e: Exception) {
                Log.e(tag, "failed to post image. err msg is $e")
                throw e
            }
        }

        override suspend fun deleteImage(url: String) {
            try {
                val fileName = url.substringAfterLast("/")
                val bucket = storage.from(bucketName)
                bucket.delete(fileName)
            } catch (e: Exception) {
                Log.e(tag, "failed to post image. err msg is $e")
                throw e
            }
        }
    }
