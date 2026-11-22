package com.example.tugasku.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tugasku.TugasKuApplication
import com.example.tugasku.ui.screens.DaftarTugasScreen
import com.example.tugasku.ui.screens.DetailTugasScreen
import com.example.tugasku.ui.screens.FormTugasScreen
import com.example.tugasku.ui.viewmodel.TugasViewModel
import com.example.tugasku.ui.viewmodel.TugasViewModelFactory

/**
 * NavHost utama aplikasi TugasKu:
 * Mengatur alur navigasi antar layar.
 * Menghubungkan Composable UI ke TugasViewModel: Composable hanya mengamati state dan mengirimkan event,
 * tanpa ada logika bisnis di dalam fungsi Composable.
 */
@Composable
fun TugasKuNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val application = context.applicationContext as TugasKuApplication

    // Menginisialisasi ViewModel menggunakan Factory manual dengan Repository dan DataStore
    val viewModel: TugasViewModel = viewModel(
        factory = TugasViewModelFactory(
            repository = application.repository,
            settingsPreference = application.settingsPreference
        )
    )

    // Mengamati StateFlow UiState dari ViewModel secara reaktif
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.Daftar.route,
        modifier = modifier
    ) {
        // Layar 1: Daftar Tugas
        composable(route = Screen.Daftar.route) {
            DaftarTugasScreen(
                daftarTugas = uiState.daftarTugas,
                isModeRingkas = uiState.isModeRingkas,
                onModeRingkasChanged = { viewModel.setModeRingkas(it) },
                onItemClick = { tugasId ->
                    navController.navigate(Screen.Detail.createRoute(tugasId))
                },
                onTambahClick = {
                    navController.navigate(Screen.Form.createRoute())
                }
            )
        }

        // Layar 2: Detail Tugas
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("tugasId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val tugasId = backStackEntry.arguments?.getLong("tugasId") ?: 0L
            val tugas = uiState.daftarTugas.firstOrNull { it.id == tugasId }

            DetailTugasScreen(
                tugas = tugas,
                onBackClick = {
                    navController.popBackStack()
                },
                onUbahClick = { id ->
                    navController.navigate(Screen.Form.createRoute(id))
                },
                onHapusClick = { id ->
                    viewModel.hapusTugas(id)
                    navController.popBackStack()
                },
                onToggleSelesai = { selesaiBaru ->
                    viewModel.toggleStatusSelesai(tugasId, selesaiBaru)
                }
            )
        }

        // Layar 3: Form Tambah / Ubah Tugas
        composable(
            route = Screen.Form.route,
            arguments = listOf(
                navArgument("tugasId") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStackEntry ->
            val tugasIdArg = backStackEntry.arguments?.getLong("tugasId") ?: -1L
            val isEditMode = tugasIdArg > 0
            val tugasToEdit = if (isEditMode) {
                uiState.daftarTugas.firstOrNull { it.id == tugasIdArg }
            } else {
                null
            }

            FormTugasScreen(
                tugasId = if (isEditMode) tugasIdArg else null,
                initialNamaTugas = tugasToEdit?.namaTugas ?: "",
                initialMataKuliah = tugasToEdit?.mataKuliah ?: "",
                initialCatatan = tugasToEdit?.catatan ?: "",
                onBackClick = {
                    navController.popBackStack()
                },
                onSimpanClick = { nama, matkul, catatan ->
                    if (isEditMode) {
                        viewModel.perbaruiTugas(
                            id = tugasIdArg,
                            namaTugas = nama,
                            mataKuliah = matkul,
                            catatan = catatan
                        )
                    } else {
                        viewModel.tambahTugas(
                            namaTugas = nama,
                            mataKuliah = matkul,
                            catatan = catatan
                        )
                    }
                    navController.popBackStack()
                }
            )
        }
    }
}
