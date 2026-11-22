package com.example.tugasku.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasku.data.entity.Tugas
import com.example.tugasku.data.repository.TugasRepository
import com.example.tugasku.data.settings.SettingsPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel utama untuk mengelola state dan logika bisnis aplikasi TugasKu.
 * Menghubungkan data dari TugasRepository dan preferensi dari SettingsPreference dengan UI melalui StateFlow immutable.
 */
class TugasViewModel(
    private val repository: TugasRepository,
    private val settingsPreference: SettingsPreference
) : ViewModel() {

    /**
     * StateFlow utama untuk UI (UiState).
     * Menggabungkan aliran data Room (repository.allTugas) dan preferensi DataStore (settingsPreference.isModeRingkas).
     * Menggunakan SharingStarted.Eagerly agar proses pembacaan file preferensi langsung aktif segera saat ViewModel dibuat.
     */
    val uiState: StateFlow<TugasUiState> = combine(
        repository.allTugas,
        settingsPreference.isModeRingkas
    ) { daftarTugas, isModeRingkas ->
        TugasUiState(
            daftarTugas = daftarTugas,
            isModeRingkas = isModeRingkas
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = TugasUiState()
    )

    // Mengubah status mode tampilan ringkas dan menyimpannya secara persisten ke DataStore
    fun setModeRingkas(ringkas: Boolean) {
        viewModelScope.launch(Dispatchers.IO + NonCancellable) {
            settingsPreference.setModeRingkas(ringkas)
        }
    }

    // Menambah tugas baru ke database melalui Repository
    fun tambahTugas(namaTugas: String, mataKuliah: String, catatan: String) {
        viewModelScope.launch {
            val tugasBaru = Tugas(
                namaTugas = namaTugas,
                mataKuliah = mataKuliah,
                catatan = catatan,
                selesai = false
            )
            repository.insertTugas(tugasBaru)
        }
    }

    // Memperbarui tugas yang ada
    fun perbaruiTugas(id: Long, namaTugas: String, mataKuliah: String, catatan: String) {
        viewModelScope.launch {
            val tugasLama = uiState.value.daftarTugas.firstOrNull { it.id == id }
            val tugasUpdate = Tugas(
                id = id,
                namaTugas = namaTugas,
                mataKuliah = mataKuliah,
                catatan = catatan,
                selesai = tugasLama?.selesai ?: false
            )
            repository.updateTugas(tugasUpdate)
        }
    }

    // Menghapus tugas berdasarkan id
    fun hapusTugas(id: Long) {
        viewModelScope.launch {
            val tugasHapus = uiState.value.daftarTugas.firstOrNull { it.id == id }
            if (tugasHapus != null) {
                repository.deleteTugas(tugasHapus)
            }
        }
    }

    // Mengubah status selesai / belum selesai
    fun toggleStatusSelesai(id: Long, selesai: Boolean) {
        viewModelScope.launch {
            repository.updateStatusSelesai(id, selesai)
        }
    }
}
