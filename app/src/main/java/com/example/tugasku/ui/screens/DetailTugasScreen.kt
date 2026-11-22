package com.example.tugasku.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tugasku.data.entity.Tugas
import com.example.tugasku.ui.theme.TugasKuTheme

/**
 * Layar Detail Tugas:
 * Menampilkan rincian data tugas, toggle status selesai/belum selesai,
 * tombol ubah untuk navigasi ke form edit, serta tombol hapus dengan konfirmasi dialog.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTugasScreen(
    tugas: Tugas?,
    onBackClick: () -> Unit = {},
    onUbahClick: (Long) -> Unit = {},
    onHapusClick: (Long) -> Unit = {},
    onToggleSelesai: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    // State lokal untuk menampilkan dialog konfirmasi hapus
    var tampilkanDialogHapus by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail Tugas") },
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
        if (tugas == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Tugas tidak ditemukan",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Card Informasi Utama Tugas
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Column {
                            Text(
                                text = "Nama Tugas",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = tugas.namaTugas,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        if (tugas.mataKuliah.isNotBlank()) {
                            Column {
                                Text(
                                    text = "Mata Kuliah",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = tugas.mataKuliah,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        if (tugas.catatan.isNotBlank()) {
                            Column {
                                Text(
                                    text = "Catatan",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = tugas.catatan,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }

                // Card Toggle Status Penyelesaian
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Status Penyelesaian",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = if (tugas.selesai) "Selesai" else "Belum selesai",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = if (tugas.selesai)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.error
                            )
                        }
                        Switch(
                            checked = tugas.selesai,
                            onCheckedChange = onToggleSelesai
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Baris Tombol Aksi: Ubah & Hapus
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Tombol Ubah Tugas
                    Button(
                        onClick = { onUbahClick(tugas.id) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Ubah")
                    }

                    // Tombol Hapus Tugas
                    OutlinedButton(
                        onClick = { tampilkanDialogHapus = true },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text(text = "Hapus")
                    }
                }
            }

            // Dialog Konfirmasi Hapus Tugas
            if (tampilkanDialogHapus) {
                AlertDialog(
                    onDismissRequest = { tampilkanDialogHapus = false },
                    title = {
                        Text(text = "Hapus Tugas")
                    },
                    text = {
                        Text(text = "Apakah Anda yakin ingin menghapus tugas \"${tugas.namaTugas}\"? Tindakan ini tidak dapat dibatalkan.")
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                tampilkanDialogHapus = false
                                onHapusClick(tugas.id)
                            }
                        ) {
                            Text(
                                text = "Hapus",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { tampilkanDialogHapus = false }
                        ) {
                            Text(text = "Batal")
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailTugasScreenPreview() {
    TugasKuTheme {
        DetailTugasScreen(
            tugas = dummyTugasList[0]
        )
    }
}
