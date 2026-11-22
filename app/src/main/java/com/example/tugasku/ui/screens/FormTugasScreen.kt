package com.example.tugasku.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tugasku.ui.theme.TugasKuTheme

/**
 * Layar Form Tambah / Ubah Tugas (Satu layar melayani dua mode):
 * - Mode Tambah jika tugasId == null / 0L
 * - Mode Ubah jika tugasId != null
 *
 * Dilengkapi validasi: namaTugas tidak boleh kosong (menampilkan pesan error pada TextField).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormTugasScreen(
    tugasId: Long? = null,
    initialNamaTugas: String = "",
    initialMataKuliah: String = "",
    initialCatatan: String = "",
    onSimpanClick: (nama: String, matkul: String, catatan: String) -> Unit = { _, _, _ -> },
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val isEditMode = tugasId != null && tugasId > 0

    // State field form
    var namaTugas by remember(initialNamaTugas) { mutableStateOf(initialNamaTugas) }
    var mataKuliah by remember(initialMataKuliah) { mutableStateOf(initialMataKuliah) }
    var catatan by remember(initialCatatan) { mutableStateOf(initialCatatan) }

    // State validasi error nama tugas
    var isErrorNama by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = if (isEditMode) "Ubah Tugas" else "Tambah Tugas")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Input Nama Tugas (Wajib diisi)
            OutlinedTextField(
                value = namaTugas,
                onValueChange = {
                    namaTugas = it
                    if (isErrorNama && it.isNotBlank()) {
                        isErrorNama = false
                    }
                },
                label = { Text(text = "Nama Tugas *") },
                placeholder = { Text(text = "Contoh: Makalah Etika Profesi") },
                isError = isErrorNama,
                supportingText = {
                    if (isErrorNama) {
                        Text(
                            text = "Nama tugas tidak boleh kosong",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Input Mata Kuliah
            OutlinedTextField(
                value = mataKuliah,
                onValueChange = { mataKuliah = it },
                label = { Text(text = "Mata Kuliah") },
                placeholder = { Text(text = "Contoh: Pemrograman Bergerak") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Input Catatan (Multi-line)
            OutlinedTextField(
                value = catatan,
                onValueChange = { catatan = it },
                label = { Text(text = "Catatan") },
                placeholder = { Text(text = "Detail tugas, deadline, link pengumpulan, dll.") },
                minLines = 3,
                maxLines = 5,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tombol Simpan dengan validasi namaTugas tidak boleh kosong
            Button(
                onClick = {
                    if (namaTugas.isBlank()) {
                        isErrorNama = true
                    } else {
                        onSimpanClick(namaTugas.trim(), mataKuliah.trim(), catatan.trim())
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (isEditMode) "Perbarui Tugas" else "Simpan Tugas")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormTugasTambahPreview() {
    TugasKuTheme {
        FormTugasScreen(tugasId = null)
    }
}

@Preview(showBackground = true)
@Composable
fun FormTugasUbahPreview() {
    TugasKuTheme {
        FormTugasScreen(
            tugasId = 1L,
            initialNamaTugas = "Makalah Etika Profesi",
            initialMataKuliah = "Etika Profesi",
            initialCatatan = "Studi kasus keamanan sistem"
        )
    }
}
