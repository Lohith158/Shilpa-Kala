package com.shilpakala.data.repository

import com.shilpakala.data.local.ArtisanProfile
import com.shilpakala.data.local.ArtisanProfileDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val artisanProfileDao: ArtisanProfileDao
) {
    fun getProfile(): Flow<ArtisanProfile?> =
        artisanProfileDao.getAll().map { profiles -> profiles.firstOrNull() }

    suspend fun saveProfile(profile: ArtisanProfile): Long = artisanProfileDao.insert(profile)
}
