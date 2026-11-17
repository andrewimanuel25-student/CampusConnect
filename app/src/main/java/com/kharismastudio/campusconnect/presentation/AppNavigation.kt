package com.kharismastudio.campusconnect.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kharisma.campusconnect.presentation.AnnouncementScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "CampusConnect",
                    style = MaterialTheme.typography.headlineMedium
                )
                Button(
                    onClick = { navController.navigate("announcement") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Pengumuman Kampus")
                }
                Button(
                    onClick = { navController.navigate("info_mitra") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Info Mitra")
                }
                Button(
                    onClick = { navController.navigate("notification") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Notifikasi Live")
                }
            }
        }

        composable("announcement") {
            AnnouncementScreen()
        }

        composable("info_mitra") {
            InfoMitraScreen()
        }

        composable("notification") {
            NotificationScreen()
        }
    }
}
