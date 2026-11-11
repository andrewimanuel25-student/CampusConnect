package com.kharismastudio.campusconnect.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun InfoMitraScreen(
    viewModel: InfoMitraViewModel =
        hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    when (val currentState = state) {
        is InfoMitraUiState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement =
                    Arrangement.Center,
                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Text(
                    text = "Memuat informasi negara..."
                )
            }
        }

        is InfoMitraUiState.Success -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = currentState.data,
                    key = { it.kode }
                ) { negara ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = negara.nama,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Kode: ${negara.kode}"
                            )
                            Text(
                                text = "Ibu Kota: ${negara.ibuKota}"
                            )
                        }
                    }
                }
            }
        }

        is InfoMitraUiState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement =
                    Arrangement.Center,
                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {
                Text(currentState.message)
                Button(
                    onClick = {
                        viewModel.muatData()
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Coba Lagi")
                }
            }
        }
    }
}
