package com.shilpakala.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shilpakala.data.local.BrandedPhoto
import com.shilpakala.data.repository.BrandedPhotoRepository
import com.shilpakala.data.repository.GalleryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.collect

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val brandedPhotoRepository: BrandedPhotoRepository,
    private val galleryRepository: GalleryRepository
) : ViewModel() {

    private val _photos = MutableStateFlow<List<BrandedPhoto>>(emptyList())
    val photos: StateFlow<List<BrandedPhoto>> = _photos.asStateFlow()

    init {
        // Load photos from Room on init.
        viewModelScope.launch {
            brandedPhotoRepository.getAllPhotos().collect { photos ->
                // Keep the UI requirement explicit even though the DAO already sorts.
                _photos.value = photos.sortedByDescending { it.createdAt }
            }
        }
    }

    fun deletePhoto(photo: BrandedPhoto) {
        viewModelScope.launch {
            // 1) Delete from Room DB (this will update the UI via the Flow collector).
            brandedPhotoRepository.deleteBrandedPhoto(photo)
            // 2) Best-effort delete from storage for the underlying media.
            galleryRepository.deletePhotoFile(photo.photoUri)
        }
    }
}

