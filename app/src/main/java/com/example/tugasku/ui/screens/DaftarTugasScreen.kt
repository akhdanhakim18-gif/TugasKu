package com.example.tugasku.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tugasku.data.entity.Tugas
import com.example.tugasku.ui.theme.TugasKuTheme

/**
 * Layar Daftar Tugas:
 * Menampilkan daftar agenda tugas dengan LazyColumn, tombol FAB untuk tambah tugas,
 * switch untuk mode tampilan ringkas, dan pesan "Belum ada tugas" saat list kosong.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaftarTugasScreen(
    daftarTugas: List<Tugas>,
    isModeRingkas: Boolean = false,
    onModeRingkasChanged: (Boolean) -> Unit = {},
    onItemClick: (Long) -> Unit = {},
    onTambahClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "TugasKu",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    // Switch untuk mengaktifkan / menonaktifkan mode tampilan ringkas
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            text = "Ringkas",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Switch(
                            checked = isModeRingkas,
                            onCheckedChange = onModeRingkasChanged
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            // FAB untuk menambah tugas baru
            FloatingActionButton(
                onClick = onTambahClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Tugas"
                )
            }
        }
    ) { innerPadding ->
        if (daftarTugas.isEmpty()) {
            // Tampilan empty state jika belum ada tugas yang terdaftar
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Belum ada tugas",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            // Daftar tugas menggunakan LazyColumn
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(if (isModeRingkas) 8.dp else 12.dp)
            ) {
                items(daftarTugas, key = { it.id }) { tugas ->
                    TugasItemCard(
                        tugas = tugas,
                        isModeRingkas = isModeRingkas,
                        onClick = { onItemClick(tugas.id) }
                    )
                }
            }
        }
    }
}

/**
 * Komponen Card item tugas individual.
 * Tampilan menyesuaikan status isModeRingkas (catatan disembunyikan & padding lebih kecil).
 */
@Composable
fun TugasItemCard(
    tugas: Tugas,
    isModeRingkas: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (tugas.selesai)
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
            else
                MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(if (isModeRingkas) 10.dp else 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = tugas.namaTugas,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Label status Selesai / Belum Selesai
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (tugas.selesai)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.errorContainer
                ) {
                    Text(
                        text = if (tugas.selesai) "Selesai" else "Belum selesai",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (tugas.selesai)
                            MaterialTheme.colorScheme.onPrimaryContainer
                        else
                            MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            if (tugas.mataKuliah.isNotBlank()) {
                Spacer(modifier = Modifier.height(if (isModeRingkas) 2.dp else 4.dp))
                Text(
                    text = tugas.mataKuliah,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }

            // Pada mode ringkas, catatan disembunyikan
            if (!isModeRingkas && tugas.catatan.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = tugas.catatan,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// Data dummy dengan tipe data eksplisit List<Tugas> untuk kebutuhan preview dan testing
val dummyTugasList: List<Tugas> = listOf(
    Tugas(
        id = 1,
        namaTugas = "Makalah Etika Profesi",
        mataKuliah = "Etika Profesi",
        catatan = "Membahas etika rekayasa perangkat lunak dan studi kasus keamanan sistem.",
        selesai = false
    ),
    Tugas(
        id = 2,
        namaTugas = "Laporan Praktikum Basis Data",
        mataKuliah = "Sistem Basis Data",
        catatan = "Normalisasi 1NF sampai 3NF beserta query DDL dan DML.",
        selesai = true
    ),
    Tugas(
        id = 3,
        namaTugas = "Projek Mobile Kotlin",
        mataKuliah = "Pemrograman Bergerak",
        catatan = "Membuat aplikasi agenda TugasKu dengan Jetpack Compose & Room.",
        selesai = false
    )
)

@Preview(showBackground = true)
@Composable
fun DaftarTugasScreenPreview() {
    TugasKuTheme {
        DaftarTugasScreen(
            daftarTugas = dummyTugasList,
            isModeRingkas = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DaftarTugasEmptyScreenPreview() {
    TugasKuTheme {
        DaftarTugasScreen(
            daftarTugas = emptyList(),
            isModeRingkas = false
        )
    }
}
