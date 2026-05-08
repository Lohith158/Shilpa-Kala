package com.shilpakala.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ArtisanProfile::class, BrandedPhoto::class],
    version = 1,
    exportSchema = false
)
abstract class ShilpaKalaDatabase : RoomDatabase() {
    abstract fun artisanProfileDao(): ArtisanProfileDao
    abstract fun brandedPhotoDao(): BrandedPhotoDao
}
