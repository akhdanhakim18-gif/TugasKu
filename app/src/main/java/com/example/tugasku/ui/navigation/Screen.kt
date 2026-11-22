package com.example.tugasku.ui.navigation

/**
 * Definisi rute layar dan navigasi aplikasi TugasKu.
 */
sealed class Screen(val route: String) {
    // Layar utama: Daftar Tugas
    data object Daftar : Screen("daftar")

    // Layar Detail Tugas dengan parameter tugasId
    data object Detail : Screen("detail/{tugasId}") {
        fun createRoute(tugasId: Long): String = "detail/$tugasId"
    }

    // Layar Form Tambah / Ubah dengan parameter opsional tugasId
    data object Form : Screen("form?tugasId={tugasId}") {
        fun createRoute(tugasId: Long? = null): String {
            return if (tugasId != null && tugasId > 0) "form?tugasId=$tugasId" else "form"
        }
    }
}
