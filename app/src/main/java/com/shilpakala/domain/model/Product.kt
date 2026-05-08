package com.shilpakala.domain.model

data class Product(
    val id: Long,
    val name: String,
    val material: String,
    val price: Double,
    val description: String
)
