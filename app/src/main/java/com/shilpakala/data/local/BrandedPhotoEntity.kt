package com.shilpakala.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "branded_photos")
data class BrandedPhoto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val photoUri: String,
    val productName: String,
    val material: String,
    val price: Double,
    val description: String,
    val createdAt: Long
)
