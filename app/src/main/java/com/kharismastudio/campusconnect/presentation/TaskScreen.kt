package com.kharismastudio.campusconnect.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kharisma.campusconnect.shared.Tugas
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TaskScreen(
    viewModel: TaskViewModel = hiltViewModel()
) {
    // 1. Mengamati daftar tugas dan pesan error dari TaskViewModel
    val tasks by viewModel.tasks.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // 2. Mengurutkan daftar berdasarkan deadline paling dekat (Langkah 3.t)
    val sortedTasks = remember(tasks) {
        tasks.sortedBy { it.deadlineMillis }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Daftar Tugas Kuliah",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Menampilkan pesan jika terjadi error pengambilan data Firestore
        errorMessage?.let { pesanError ->
            Text(
                text = pesanError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // 3. Menampilkan daftar tugas dengan LazyColumn (Langkah 3.q)
        if (sortedTasks.isEmpty()) {
            Text(
                text = "Belum ada tugas kuliah.",
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(sortedTasks) { task ->
                    TaskItemCard(task = task)
                }
            }
        }
    }
}

@Composable
private fun TaskItemCard(task: Tugas) {
    // 4. Menentukan status terlambat terhadap waktu saat ini (Langkah 3.r)
    val terlambat = task.deadlineMillis < System.currentTimeMillis()

    // Memformat milidetik menjadi tanggal & jam yang mudah dibaca
    val formattedDate = remember(task.deadlineMillis) {
        val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        sdf.format(Date(task.deadlineMillis))
    }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Judul Tugas & ID Mata Kuliah
            Text(
                text = "${task.judul} (MK ID: ${task.matakuliahId})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Deskripsi Tugas
            Text(
                text = task.deskripsi,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 5. Label status merah jika terlambat, atau tanggal jika aman (Langkah 3.s)
            if (terlambat) {
                Text(
                    text = "Terlambat (Deadline: $formattedDate)",
                    color = Color.Red,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodySmall
                )
            } else {
                Text(
                    text = "Deadline: $formattedDate",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
