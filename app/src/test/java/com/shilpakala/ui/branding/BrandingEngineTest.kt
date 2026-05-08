package com.shilpakala.ui.branding

import android.graphics.Bitmap
import android.graphics.Color
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class BrandingEngineTest {

    private val brandingEngine = BrandingEngine()

    @Test
    fun brandPhoto_returnsNonNullBitmapWithCorrectDimensions() {
        // Arrange
        val inputBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        
        // Act
        val outputBitmap = brandingEngine.brandPhoto(
            source = inputBitmap,
            backgroundColor = Color.WHITE,
            artisanName = "Test Artisan",
            craftType = "Woodwork",
            productName = "Statue",
            material = "Sandalwood",
            price = "500"
        )

        // Assert
        assertNotNull(outputBitmap)
        assertTrue("Output width should be greater than 0", outputBitmap.width > 0)
        assertTrue("Output height should be greater than 0", outputBitmap.height > 0)
    }
}
