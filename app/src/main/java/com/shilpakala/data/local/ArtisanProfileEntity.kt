package com.shilpakala.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artisan_profiles")
data class ArtisanProfile(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val village: String,
    val craftType: String,
    val logoUri: String?
)
