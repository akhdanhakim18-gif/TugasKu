package com.example.tugasku.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException

// Inisialisasi Preferences DataStore tunggal untuk Context dengan corruption handler
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "tugasku_settings",
    corruptionHandler = ReplaceFileCorruptionHandler(
        produceNewData = { emptyPreferences() }
    )
)

/**
 * Repositori Preferences DataStore:
 * Mengelola penyimpanan lokal preferensi "Mode tampilan ringkas" (ON/OFF).
 * Nilai preferensi ini akan bertahan (persisten) meskipun aplikasi ditutup atau direstart.
 */
class SettingsPreference(context: Context) {

    private val dataStore: DataStore<Preferences> = context.applicationContext.dataStore

    private companion object {
        val KEY_MODE_RINGKAS = booleanPreferencesKey("mode_tampilan_ringkas")
    }

    /**
     * Aliran data (Flow) untuk mengamati perubahan status mode ringkas secara reaktif.
     * Menggunakan penanganan IOException agar pembacaan preferensi tetap aman.
     */
    val isModeRingkas: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[KEY_MODE_RINGKAS] ?: false
        }

    /**
     * Menyimpan perubahan preferensi mode tampilan ringkas ke DataStore.
     * Menggunakan withContext(Dispatchers.IO + NonCancellable) agar penulisan file ke disk
     * dipastikan selesai dan tidak dibatalkan di tengah jalan saat aplikasi ditutup oleh user.
     */
    suspend fun setModeRingkas(isRingkas: Boolean) {
        withContext(Dispatchers.IO + NonCancellable) {
            dataStore.edit { preferences ->
                preferences[KEY_MODE_RINGKAS] = isRingkas
            }
        }
    }
}
