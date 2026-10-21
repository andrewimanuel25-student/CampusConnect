package com.kharismastudio.campusconnect.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kharismastudio.campusconnect.data.MataKuliah
import data.app.src.main.java.com.kharisma.campusconnect.data.MataKuliah
import data.app.src.main.java.com.kharisma.campusconnect.data.daftarMataKuliahDummy

@Composable
fun HomeScreen(daftarMK: List<MataKuliah>) {
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

@Preview
@Composable
fun HomeScreen_Preview ()
{
    HomeScreen(daftarMataKuliahDummy);
}