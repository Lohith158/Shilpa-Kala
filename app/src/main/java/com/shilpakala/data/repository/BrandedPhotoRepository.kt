package com.shilpakala.data.repository

import com.shilpakala.data.local.BrandedPhoto
import com.shilpakala.data.local.BrandedPhotoDao
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class BrandedPhotoRepository @Inject constructor(
    private val brandedPhotoDao: BrandedPhotoDao
) {
    fun getAllPhotos(): Flow<List<BrandedPhoto>> = brandedPhotoDao.getAll()

    suspend fun insertBrandedPhoto(photo: BrandedPhoto): Long =
        brandedPhotoDao.insertBrandedPhoto(photo)

    suspend fun deleteBrandedPhoto(photo: BrandedPhoto) {
        brandedPhotoDao.delete(photo)
    }
}
