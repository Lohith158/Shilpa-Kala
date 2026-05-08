package com.shilpakala.ui.branding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.shilpakala.utils.ShareHelper
import kotlinx.coroutines.launch

private data class BackgroundPreset(
    val label: String,
    val color: Color
)

private val BackgroundPresets = listOf(
    BackgroundPreset("White", Color(0xFFFFFFFF)),
    BackgroundPreset("Jute", Color(0xFFE7D9BE)),
    BackgroundPreset("Dark Wood", Color(0xFF6B4A2D)),
    BackgroundPreset("Marble", Color(0xFFE6E7EB)),
    BackgroundPreset("Terracotta", Color(0xFFB85A3D))
)

@Composable
fun BrandingScreen(
    imageUri: String,
    navController: NavController,
    viewModel: BrandingViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val selectedBackground by viewModel.selectedBackground.collectAsState()
    val productName by viewModel.productName.collectAsState()
    val material by viewModel.material.collectAsState()
    val price by viewModel.price.collectAsState()
    val brandedUri by viewModel.brandedImageUri.collectAsState()
    val isProcessing by viewModel.isProcessing.collectAsState()

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .border(1.dp, Color(0xFFD9D9D9), RoundedCornerShape(12.dp))
        ) {
            AsyncImage(
                model = brandedUri ?: imageUri,
                contentDescription = "Captured product image",
                modifier = Modifier
                    .fillMaxSize()
            )

            if (brandedUri != null) {
                IconButton(
                    onClick = {
                        ShareHelper.sharePhoto(
                            context = context,
                            imageUri = brandedUri.toString(),
                            productName = productName.ifBlank { "Product" },
                            price = price.ifBlank { "0" }
                        )
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Share branded photo"
                    )
                }
            }
        }

        Text("Background")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            BackgroundPresets.forEachIndexed { index, preset ->
                val borderColor = if (selectedBackground == index) Color(0xFF1A73E8) else Color(0xFFBDBDBD)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { viewModel.onBackgroundSelected(index) }
                ) {
                    Box(
                        modifier = Modifier
                            .size(58.dp)
                            .background(preset.color, RoundedCornerShape(10.dp))
                            .border(
                                BorderStroke(2.dp, borderColor),
                                RoundedCornerShape(10.dp)
                            )
                    )
                    Text(preset.label)
                }
            }
        }

        OutlinedTextField(
            value = productName,
            onValueChange = viewModel::onProductNameChanged,
            label = { Text("Product Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = material,
            onValueChange = viewModel::onMaterialChanged,
            label = { Text("Material") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = price,
            onValueChange = viewModel::onPriceChanged,
            label = { Text("Price (₹)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = { viewModel.applyBranding(context, imageUri) },
            enabled = !isProcessing,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isProcessing) {
                CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
            } else {
                Text("Apply Branding")
            }
        }

        if (brandedUri != null) {
            Button(
                onClick = {
                    scope.launch {
                        val saved = viewModel.saveToGallery(context)
                        if (saved != null) {
                            ShareHelper.sharePhoto(
                                context = context,
                                imageUri = saved.toString(),
                                productName = productName.ifBlank { "Product" },
                                price = price.ifBlank { "0" }
                            )
                            navController.navigate("gallery")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save & Share")
            }
        }
    }
}
