package com.kharismastudio.campusconnect.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.kharismastudio.campusconnect.data.repository.AnnouncementRepository
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable fun AnnouncementScreen(
    viewModel: AnnouncementViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState() //Modul 5
    val announcements by viewModel.announcements.collectAsState() //MODUL 6
    val isRefreshing by viewModel.isRefreshing.collectAsState() //MODUL 6
    val refreshError by viewModel.refreshError.collectAsState() //MODUL 6

    if (isRefreshing) { CircularProgressIndicator() } //MODUL 6

    refreshError?.let { message ->
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error
        )
    }

    if (announcements.isEmpty()) {
        Text("Belum ada pengumuman tersimpan.")
    } else {
        LazyColumn {
//            items(announcements) {item ->
//                // Gunakan kembali komponen kartu pengumuman
//                // yang telah dibuat pada Pertemuan 5.
//            }
        }
    }

                when (val currentState = state) {
                    is AnnouncementUiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is AnnouncementUiState.Success -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(
                                items = currentState.data,
                                key = { it.id }
                            ) {
                                    announcement -> Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 12.dp,
                                        vertical = 6.dp
                                    )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = announcement.judul,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = announcement.isi,
                                        modifier = Modifier.padding(top = 8.dp),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                            }
                        }
                    }

                    is AnnouncementUiState.Error -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = currentState.message
                            )

                            Button(
                                onClick = { viewModel.muatUlang() },
                                modifier = Modifier.padding(top = 16.dp)
                            ) {
                                Text("Coba Lagi")
                            }

                            Button(
                                onClick = { viewModel.refreshAnnouncements()
                                }
                            ) {
                                Text("Refresh")
                            }
                        }
                    }
                }
            }
        }
        }