package com.shilpakala.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shilpakala.data.local.ArtisanProfile
import com.shilpakala.data.local.ArtisanProfileDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val artisanProfileDao: ArtisanProfileDao
) : ViewModel() {

    private val _profile = MutableStateFlow<ArtisanProfile?>(null)
    val profile: StateFlow<ArtisanProfile?> = _profile.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            artisanProfileDao.getAll().collectLatest { profiles ->
                _profile.value = profiles.firstOrNull()
            }
        }
    }

    fun saveProfile(name: String, village: String, craftType: String) {
        viewModelScope.launch {
            val currentProfile = _profile.value
            val profileToSave = ArtisanProfile(
                id = currentProfile?.id ?: 0L,
                name = name.trim(),
                village = village.trim(),
                craftType = craftType,
                logoUri = currentProfile?.logoUri
            )
            artisanProfileDao.insert(profileToSave)
        }
    }
}
