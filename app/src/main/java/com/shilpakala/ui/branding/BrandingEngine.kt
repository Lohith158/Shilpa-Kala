package com.shilpakala.ui.branding

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import kotlin.math.min

class BrandingEngine {

    fun brandPhoto(
        source: Bitmap,
        backgroundColor: Int,
        artisanName: String,
        craftType: String,
        productName: String,
        material: String,
        price: String
    ): Bitmap {
        val target = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(target)

        canvas.drawColor(backgroundColor)
        canvas.drawBitmap(source, 0f, 0f, null)

        val width = source.width.toFloat()
        val height = source.height.toFloat()
        val pad = min(width, height) * 0.035f

        drawArtisanStamp(
            canvas = canvas,
            left = pad,
            top = pad,
            text = "${artisanName.ifBlank { "Artisan" }} • ${craftType.ifBlank { "Craft" }}"
        )

        drawHeritageLabel(
            canvas = canvas,
            left = pad,
            right = width - pad,
            bottom = height - pad
        )

        drawProductTag(
            canvas = canvas,
            width = width,
            height = height,
            pad = pad,
            productName = productName,
            material = material,
            price = price
        )

        return target
    }

    private fun drawHeritageLabel(canvas: Canvas, left: Float, right: Float, bottom: Float) {
        val labelHeight = (bottom - left) * 0.09f
        val labelRect = RectF(left, bottom - labelHeight, right, bottom)
        val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            style = Paint.Style.FILL
        }
        canvas.drawRoundRect(labelRect, labelHeight / 2f, labelHeight / 2f, bgPaint)

        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#3A2818")
            textAlign = Paint.Align.CENTER
            textSize = labelHeight * 0.36f
            isFakeBoldText = true
        }
        val baseline = labelRect.centerY() - (textPaint.ascent() + textPaint.descent()) / 2f
        canvas.drawText("Handmade in Karnataka 🏺", labelRect.centerX(), baseline, textPaint)
    }

    private fun drawArtisanStamp(canvas: Canvas, left: Float, top: Float, text: String) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#F7F2E8")
            textSize = 36f
            isFakeBoldText = true
            setShadowLayer(4f, 0f, 2f, Color.parseColor("#66000000"))
        }
        canvas.drawText(text, left, top + paint.textSize, paint)
    }

    private fun drawProductTag(
        canvas: Canvas,
        width: Float,
        height: Float,
        pad: Float,
        productName: String,
        material: String,
        price: String
    ) {
        val tagWidth = width * 0.45f
        val tagHeight = height * 0.19f
        val rect = RectF(width - tagWidth - pad, height - tagHeight - (pad * 3.2f), width - pad, height - (pad * 2.2f))
        val tagPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#A4472F")
            style = Paint.Style.FILL
        }
        canvas.drawRoundRect(rect, 24f, 24f, tagPaint)

        val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#FDF4E6")
            textSize = rect.height() * 0.17f
        }
        val titlePaint = Paint(linePaint).apply { isFakeBoldText = true }
        val pricePaint = Paint(linePaint).apply {
            textSize = rect.height() * 0.23f
            isFakeBoldText = true
        }

        val x = rect.left + pad * 0.6f
        val title = if (productName.isBlank()) "Handcrafted Product" else productName
        val materialLine = if (material.isBlank()) "Material: --" else "Material: $material"
        val priceLine = "₹${price.ifBlank { "--" }}"

        var y = rect.top + rect.height() * 0.28f
        canvas.drawText(title, x, y, titlePaint)
        y += rect.height() * 0.24f
        canvas.drawText(materialLine, x, y, linePaint)
        y += rect.height() * 0.28f
        canvas.drawText(priceLine, x, y, pricePaint)
    }
}
