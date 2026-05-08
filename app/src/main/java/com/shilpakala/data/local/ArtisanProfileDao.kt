package com.shilpakala.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtisanProfileDao {
    @Query("SELECT * FROM artisan_profiles ORDER BY id DESC")
    fun getAll(): Flow<List<ArtisanProfile>>

    @Query("SELECT * FROM artisan_profiles WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): ArtisanProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: ArtisanProfile): Long

    @Update
    suspend fun update(profile: ArtisanProfile)

    @Delete
    suspend fun delete(profile: ArtisanProfile)
}
