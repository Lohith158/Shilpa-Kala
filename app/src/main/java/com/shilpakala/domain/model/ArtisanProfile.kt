package com.shilpakala.domain.model

data class ArtisanProfile(
    val id: Long,
    val name: String,
    val village: String,
    val craftType: String,
    val logoUri: String?
)
