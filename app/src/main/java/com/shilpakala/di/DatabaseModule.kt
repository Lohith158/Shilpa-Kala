package com.shilpakala.di

import android.content.Context
import androidx.room.Room
import com.shilpakala.data.local.ArtisanProfileDao
import com.shilpakala.data.local.BrandedPhotoDao
import com.shilpakala.data.local.ShilpaKalaDatabase
import com.shilpakala.data.repository.GalleryRepository
import com.shilpakala.data.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ShilpaKalaDatabase = Room.databaseBuilder(
        context,
        ShilpaKalaDatabase::class.java,
        "shilpakala.db"
    ).build()

    @Provides
    fun provideArtisanProfileDao(database: ShilpaKalaDatabase): ArtisanProfileDao =
        database.artisanProfileDao()

    @Provides
    fun provideBrandedPhotoDao(database: ShilpaKalaDatabase): BrandedPhotoDao =
        database.brandedPhotoDao()

    @Provides
    @Singleton
    fun provideProfileRepository(artisanProfileDao: ArtisanProfileDao): ProfileRepository =
        ProfileRepository(artisanProfileDao)

    @Provides
    @Singleton
    fun provideGalleryRepository(
        @ApplicationContext context: Context
    ): GalleryRepository = GalleryRepository(context)
}
