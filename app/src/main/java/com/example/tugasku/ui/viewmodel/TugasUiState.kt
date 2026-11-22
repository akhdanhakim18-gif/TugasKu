package com.example.tugasku.ui.viewmodel

import com.example.tugasku.data.entity.Tugas

/**
 * State UI yang immutable untuk merepresentasikan kondisi tampilan aplikasi TugasKu.
 * Diobservasi secara reaktif oleh lapisan Composable melalui StateFlow.
 */
data class TugasUiState(
    val daftarTugas: List<Tugas> = emptyList(),
    val isModeRingkas: Boolean = false
)
