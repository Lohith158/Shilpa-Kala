package com.shilpakala.ui.branding

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shilpakala.data.local.BrandedPhoto
import com.shilpakala.data.repository.BrandedPhotoRepository
import com.shilpakala.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@HiltViewModel
class BrandingViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val brandedPhotoRepository: BrandedPhotoRepository
) : ViewModel() {

    private val brandingEngine = BrandingEngine()
    private val backgroundPresets = listOf(
        0xFFFFFFFF.toInt(),
        0xFFE7D9BE.toInt(),
        0xFF6B4A2D.toInt(),
        0xFFE6E7EB.toInt(),
        0xFFB85A3D.toInt()
    )

    private val _selectedBackground = MutableStateFlow(0)
    val selectedBackground: StateFlow<Int> = _selectedBackground.asStateFlow()

    private val _productName = MutableStateFlow("")
    val productName: StateFlow<String> = _productName.asStateFlow()

    private val _material = MutableStateFlow("")
    val material: StateFlow<String> = _material.asStateFlow()

    private val _price = MutableStateFlow("")
    val price: StateFlow<String> = _price.asStateFlow()

    private val _brandedImageUri = MutableStateFlow<Uri?>(null)
    val brandedImageUri: StateFlow<Uri?> = _brandedImageUri.asStateFlow()

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing.asStateFlow()

    fun onBackgroundSelected(index: Int) {
        _selectedBackground.value = index.coerceIn(0, backgroundPresets.lastIndex)
    }

    fun onProductNameChanged(value: String) {
        _productName.value = value
    }

    fun onMaterialChanged(value: String) {
        _material.value = value
    }

    fun onPriceChanged(value: String) {
        _price.value = value
    }

    fun applyBranding(context: Context, sourceImageUri: String) {
        if (_isProcessing.value) return
        viewModelScope.launch {
            _isProcessing.value = true
            try {
                val sourceBitmap = loadBitmapFromUri(context, Uri.parse(sourceImageUri))
                val profile = profileRepository.getProfile().firstOrNull()
                val brandedBitmap = brandingEngine.brandPhoto(
                    source = sourceBitmap,
                    backgroundColor = backgroundPresets[_selectedBackground.value],
                    artisanName = profile?.name.orEmpty(),
                    craftType = profile?.craftType.orEmpty(),
                    productName = _productName.value,
                    material = _material.value,
                    price = _price.value
                )
                val uri = saveBrandedBitmapToAppStorage(context, brandedBitmap)
                _brandedImageUri.value = uri
            } finally {
                _isProcessing.value = false
            }
        }
    }

    suspend fun saveToGallery(context: Context): Uri? {
        val brandedUri = _brandedImageUri.value ?: return null
        val bitmap = loadBitmapFromUri(context, brandedUri)
        val resolver = context.contentResolver
        val fileName = "shilpakala_brand_${System.currentTimeMillis()}.jpg"
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/ShilpaKala")
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val galleryUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values) ?: return null
        resolver.openOutputStream(galleryUri)?.use { stream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream)
        }
        values.clear()
        values.put(MediaStore.Images.Media.IS_PENDING, 0)
        resolver.update(galleryUri, values, null, null)

        brandedPhotoRepository.insertBrandedPhoto(
            BrandedPhoto(
                photoUri = galleryUri.toString(),
                productName = _productName.value.trim(),
                material = _material.value.trim(),
                price = _price.value.toDoubleOrNull() ?: 0.0,
                description = "Handmade in Karnataka",
                createdAt = System.currentTimeMillis()
            )
        )
        return galleryUri
    }

    private fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap {
        context.contentResolver.openInputStream(uri)?.use { stream ->
            return BitmapFactory.decodeStream(stream)
                ?: error("Unable to decode bitmap from uri: $uri")
        }
        error("Unable to open input stream for uri: $uri")
    }

    private fun saveBrandedBitmapToAppStorage(context: Context, bitmap: Bitmap): Uri {
        val outputDir = File(context.filesDir, "branded").apply { mkdirs() }
        val file = File(outputDir, "branded_${System.currentTimeMillis()}.jpg")
        FileOutputStream(file).use { stream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream)
        }
        return Uri.fromFile(file)
    }
}
