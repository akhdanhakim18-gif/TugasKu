package com.example.tugasku.data.repository

import com.example.tugasku.data.dao.TugasDao
import com.example.tugasku.data.entity.Tugas
import kotlinx.coroutines.flow.Flow

/**
 * Repository data Tugas:
 * Mengabstraksi sumber data (DAO Room) agar dapat dikonsumsi oleh lapisan presentasi/UI.
 */
class TugasRepository(private val tugasDao: TugasDao) {

    // Aliran data (Flow) seluruh daftar tugas
    val allTugas: Flow<List<Tugas>> = tugasDao.getAllTugas()

    // Mendapatkan aliran data satu tugas berdasarkan ID
    fun getTugasById(id: Long): Flow<Tugas?> {
        return tugasDao.getTugasById(id)
    }

    // Menambahkan tugas baru
    suspend fun insertTugas(tugas: Tugas): Long {
        return tugasDao.insertTugas(tugas)
    }

    // Memperbarui tugas
    suspend fun updateTugas(tugas: Tugas) {
        tugasDao.updateTugas(tugas)
    }

    // Menghapus tugas
    suspend fun deleteTugas(tugas: Tugas) {
        tugasDao.deleteTugas(tugas)
    }

    // Mengubah status selesai tugas
    suspend fun updateStatusSelesai(id: Long, selesai: Boolean) {
        tugasDao.updateStatusSelesai(id, selesai)
    }
}
