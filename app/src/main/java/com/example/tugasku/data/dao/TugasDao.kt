package com.example.tugasku.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tugasku.data.entity.Tugas
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) untuk tabel "tugas".
 * Menyediakan abstraksi query database SQLite menggunakan Flow untuk observasi reaktif.
 */
@Dao
interface TugasDao {

    // Mengambil semua data tugas dengan Flow, diurutkan dari ID terbesar (terbaru)
    @Query("SELECT * FROM tugas ORDER BY id DESC")
    fun getAllTugas(): Flow<List<Tugas>>

    // Mengambil satu data tugas berdasarkan id
    @Query("SELECT * FROM tugas WHERE id = :id LIMIT 1")
    fun getTugasById(id: Long): Flow<Tugas?>

    // Menyisipkan tugas baru ke dalam database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTugas(tugas: Tugas): Long

    // Memperbarui informasi tugas yang sudah ada
    @Update
    suspend fun updateTugas(tugas: Tugas)

    // Menghapus data tugas
    @Delete
    suspend fun deleteTugas(tugas: Tugas)

    // Memperbarui status selesai/belum selesai tugas berdasarkan id
    @Query("UPDATE tugas SET selesai = :selesai WHERE id = :id")
    suspend fun updateStatusSelesai(id: Long, selesai: Boolean)
}
