package com.shilpakala.ui.camera

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class CameraViewModel @Inject constructor() : ViewModel() {

    private val _capturedImageUri = MutableStateFlow<String?>(null)
    val capturedImageUri: StateFlow<String?> = _capturedImageUri.asStateFlow()

    private val _isCapturing = MutableStateFlow(false)
    val isCapturing: StateFlow<Boolean> = _isCapturing.asStateFlow()

    private val _captureRequestCount = MutableStateFlow(0)
    val captureRequestCount: StateFlow<Int> = _captureRequestCount.asStateFlow()

    fun capturePhoto() {
        if (_isCapturing.value) return
        _isCapturing.value = true
        _captureRequestCount.value += 1
    }

    fun onCaptureSuccess(uri: String) {
        _capturedImageUri.value = uri
        _isCapturing.value = false
    }

    fun onCaptureFailed() {
        _isCapturing.value = false
    }

    fun resetCapture() {
        _capturedImageUri.value = null
        _isCapturing.value = false
    }
}
