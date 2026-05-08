package com.shilpakala.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import java.io.File

object ShareHelper {

    fun sharePhoto(
        context: Context,
        imageUri: String,
        caption: String?
    ) {
        val uriForShare = resolveShareUri(context, imageUri)

        val shareIntent = ShareCompat.IntentBuilder(context)
            .setType("image/*")
            .setStream(uriForShare)
            .setText(caption ?: "")
            .intent
            .apply {
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

        context.startActivity(
            Intent.createChooser(shareIntent, null)
        )
    }

    fun sharePhotoOnly(
        context: Context,
        imageUri: String
    ) {
        sharePhoto(context, imageUri, null)
    }

    private fun resolveShareUri(
        context: Context,
        imageUri: String
    ): Uri {
        val uri = Uri.parse(imageUri)
        return if (uri.scheme == "file") {
            val file = File(requireNotNull(uri.path))
            FileProvider.getUriForFile(
                context,
                "com.shilpakala.fileprovider",
                file
            )
        } else {
            uri
        }
    }
}

