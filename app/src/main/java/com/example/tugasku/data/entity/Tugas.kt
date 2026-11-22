package com.example.tugasku.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity Room untuk tabel "tugas".
 * Menyimpan data agenda tugas kuliah dengan primary key id auto-generate.
 */
@Entity(tableName = "tugas")
data class Tugas(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val namaTugas: String,
    val mataKuliah: String,
    val catatan: String,
    val selesai: Boolean = false
)
