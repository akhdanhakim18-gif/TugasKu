# TugasKu - Aplikasi Agenda Tugas Kuliah

Aplikasi Android Native berbasis **Kotlin** dan **Jetpack Compose** untuk memenuhi Ujian Tengah Semester (UTS) Pemrograman Bergerak.

---

## Identitas Pengembang
- **Nama**: [Akhdan Hakim]
- **NIM**: [0102523007]
- **Topik**: Topik B - Agenda Tugas Kuliah ("TugasKu")

---

## Deskripsi Aplikasi
**TugasKu** adalah aplikasi manajemen agenda perkuliahan yang membantu mahasiswa mencatat, mengelola, memantau, dan menyelesaikan tugas-tugas kuliah. Aplikasi ini dibangun secara natif menggunakan arsitektur modern Android (Clean Architecture: UI Layer dengan Jetpack Compose & ViewModel StateFlow, serta Data Layer dengan Room SQLite & Preferences DataStore).

---

## Tech Stack & Library
- **Bahasa Pemrograman**: Kotlin (Kotlin 2.0 / JVM 11, Min SDK 24)
- **UI Framework**: Jetpack Compose + Material 3
- **Navigasi**: Navigation Compose (Single Activity, NavHost)
- **Arsitektur & State Management**: Android Architecture Components (ViewModel + StateFlow + UiState)
- **Database Lokal**: Room Database + Kotlin Symbol Processing (KSP)
- **Penyimpanan Preferensi**: AndroidX Preferences DataStore
- **Concurrency & Reaktif**: Kotlin Coroutines & Asynchronous Flow

---

## Fitur Utama
1. **Daftar Tugas (Home Screen)**:
   - Menampilkan seluruh agenda tugas kuliah menggunakan `LazyColumn`.
   - Floating Action Button (FAB) `+` untuk membuka form penambahan tugas baru.
   - Status indikator visual yang jelas ("Selesai" / "Belum selesai").
   - Menampilkan pesan *"Belum ada tugas"* di tengah layar ketika data masih kosong (Empty State).
2. **Detail Tugas**:
   - Menampilkan informasi lengkap tugas: Nama Tugas, Mata Kuliah, Catatan, dan Status Penyelesaian.
   - Switch toggle untuk mengubah status tugas langsung antara "Belum selesai" dan "Selesai".
   - Tombol **Ubah** untuk mengarahkan pengguna ke form edit tugas.
   - Tombol **Hapus** dengan dialog konfirmasi (`AlertDialog`) sebelum data dihapus secara permanen.
3. **Form Tambah & Ubah Tugas**:
   - Satu layar serbaguna yang melayani mode Tambah dan Ubah tugas.
   - Validasi input: Nama Tugas wajib diisi, menampilkan pesan error yang jelas pada `OutlinedTextField` jika dikosongkan.
4. **Mode Tampilan Ringkas (Preferences DataStore)**:
   - Switch toggle "Ringkas" pada TopAppBar layar utama.
   - Saat aktif (ON): catatan disembunyikan dan padding kartu tugas lebih ringkas.
   - Preferensi tersimpan permanen menggunakan Preferences DataStore dan tetap bertahan meskipun aplikasi ditutup paksa atau di-restart.
5. **Persistensi Data Penuh**:
   - Seluruh data agenda tugas tersimpan di database lokal SQLite via Room dan tidak hilang saat aplikasi ditutup.

---

## Struktur Paket
```text
com.example.tugasku/
├── data/
│   ├── dao/
│   │   └── TugasDao.kt
│   ├── database/
│   │   └── TugasDatabase.kt
│   ├── entity/
│   │   └── Tugas.kt
│   ├── repository/
│   │   └── TugasRepository.kt
│   └── settings/
│       └── SettingsPreference.kt
├── ui/
│   ├── navigation/
│   │   ├── Screen.kt
│   │   └── TugasKuNavHost.kt
│   ├── screens/
│   │   ├── DaftarTugasScreen.kt
│   │   ├── DetailTugasScreen.kt
│   │   └── FormTugasScreen.kt
│   ├── theme/
│   │   ├── Color.kt
│   │   ├── Theme.kt
│   │   └── Type.kt
│   └── viewmodel/
│       ├── TugasUiState.kt
│       ├── TugasViewModel.kt
│       └── TugasViewModelFactory.kt
├── MainActivity.kt
└── TugasKuApplication.kt
```

---

## Cara Menjalankan Aplikasi
1. Buka project **TugasKu** di Android Studio (disarankan versi Ladybug / Koala atau yang lebih baru).
2. Tunggu proses **Gradle Sync** selesai secara otomatis.
3. Pastikan perangkat fisik Android sudah terhubung (USB Debugging aktif) atau buat Android Virtual Device (Emulator) dengan API minimal 24.
4. Klik tombol **Run 'app'** (ikon segitiga hijau ▶️ atau tekan shortcut `Shift + F10`).
5. Aplikasi siap digunakan dan diuji.

---

## Tautan Video Demonstrasi
- **Link Video Demo**: [Tautan Video Demo di YouTube / Google Drive](https://...)
*(Silakan ganti tautan di atas dengan link video demonstrasi aplikasi Anda)*
