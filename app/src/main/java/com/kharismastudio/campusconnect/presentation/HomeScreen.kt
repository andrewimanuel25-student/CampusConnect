package com.kharismastudio.campusconnect.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    // collectAsState() otomatis memicu recompose (gambar ulang UI) jika data di ViewModel berubah
    val daftarMK by viewModel.courses.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(daftarMK) { mk ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(mk.nama, style = MaterialTheme.typography.titleMedium)
                    Text("${mk.sks} SKS • ${mk.dosen}")
                }
            }
        }
    }
}