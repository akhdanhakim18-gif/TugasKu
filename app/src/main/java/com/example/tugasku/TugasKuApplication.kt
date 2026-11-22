package com.example.tugasku

import android.app.Application
import com.example.tugasku.data.database.TugasDatabase
import com.example.tugasku.data.repository.TugasRepository
import com.example.tugasku.data.settings.SettingsPreference

/**
 * Kelas Application kustom untuk menyediakan instance Database, Repository, dan DataStore secara terpusat
 * tanpa menggunakan framework Dependency Injection eksternal seperti Hilt/Koin.
 */
class TugasKuApplication : Application() {
    val database by lazy { TugasDatabase.getDatabase(this) }
    val repository by lazy { TugasRepository(database.tugasDao()) }
    val settingsPreference by lazy { SettingsPreference(this) }
}
