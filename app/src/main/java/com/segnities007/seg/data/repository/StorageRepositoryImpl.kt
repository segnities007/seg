package com.segnities007.seg.data.repository

import android.util.Log
import com.segnities007.seg.data.model.Image
import com.segnities007.seg.domain.repository.StorageRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import javax.inject.Inject

class StorageRepositoryImpl
    @Inject
    constructor(
        private val postgrest: Postgrest,
        private val storage: Storage,
    ) : StorageRepository {
        private val tag = "StorageRepositoryImpl"
        private val bucketName = "avatars"

        override suspend fun postImages(
            image: Image,
            byteArray: ByteArray,
        ): String {
            try {
                val bucket = storage.from(bucketName)
                val fileName = "${image.id}.png"

                val result =
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

        override suspend fun deleteImage(imageID: Int) {
            TODO("Not yet implemented")
        }
    }
