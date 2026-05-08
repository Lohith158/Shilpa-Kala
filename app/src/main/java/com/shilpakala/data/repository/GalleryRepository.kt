package com.shilpakala.data.repository

import android.content.Context
import android.net.Uri
import java.io.File
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class GalleryRepository(
    private val context: Context
) {
    /**
     * Best-effort deletion of the underlying media from storage.
     * - For MediaStore/content:// URIs: use ContentResolver.delete()
     * - For file:// URIs: delete the underlying File
     */
    suspend fun deletePhotoFile(photoUri: String) {
        val uri = runCatching { Uri.parse(photoUri) }.getOrNull() ?: return

        withContext(Dispatchers.IO) {
            try {
                when (uri.scheme) {
                    "file" -> uri.path?.let { File(it).delete() }
                    else -> context.contentResolver.delete(uri, null, null)
                }
            } catch (_: Exception) {
                // Deletion is best-effort. Room DB deletion is handled separately by the caller.
            }
        }
    }
}

