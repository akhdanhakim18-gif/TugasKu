package com.example.tugasku.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tugasku.data.dao.TugasDao
import com.example.tugasku.data.entity.Tugas

/**
 * Kelas konfigurasi Database Room utama untuk aplikasi TugasKu.
 * Mengimplementasikan pola singleton agar hanya ada satu instance database yang terbuka.
 */
@Database(entities = [Tugas::class], version = 1, exportSchema = false)
abstract class TugasDatabase : RoomDatabase() {

    abstract fun tugasDao(): TugasDao

    companion object {
        @Volatile
        private var INSTANCE: TugasDatabase? = null

        fun getDatabase(context: Context): TugasDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TugasDatabase::class.java,
                    "tugasku_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
