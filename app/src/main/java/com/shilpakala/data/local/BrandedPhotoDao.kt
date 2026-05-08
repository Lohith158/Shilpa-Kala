package com.shilpakala.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BrandedPhotoDao {
    @Query("SELECT * FROM branded_photos ORDER BY createdAt DESC")
    fun getAll(): Flow<List<BrandedPhoto>>

    @Query("SELECT * FROM branded_photos WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): BrandedPhoto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBrandedPhoto(photo: BrandedPhoto): Long

    @Update
    suspend fun update(photo: BrandedPhoto)

    @Delete
    suspend fun delete(photo: BrandedPhoto)
}
