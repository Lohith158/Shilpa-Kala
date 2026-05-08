package com.shilpakala.domain.model

data class BrandedPhoto(
    val id: Long,
    val photoUri: String,
    val productName: String,
    val material: String,
    val price: Double,
    val description: String,
    val createdAt: Long
)
