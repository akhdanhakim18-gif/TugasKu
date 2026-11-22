package com.example.tugasku.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tugasku.data.repository.TugasRepository
import com.example.tugasku.data.settings.SettingsPreference

/**
 * Factory manual untuk membuat instance TugasViewModel dengan dependensi
 * TugasRepository dan SettingsPreference tanpa framework DI eksternal (Hilt/Koin).
 */
class TugasViewModelFactory(
    private val repository: TugasRepository,
    private val settingsPreference: SettingsPreference
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TugasViewModel::class.java)) {
            return TugasViewModel(repository, settingsPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
