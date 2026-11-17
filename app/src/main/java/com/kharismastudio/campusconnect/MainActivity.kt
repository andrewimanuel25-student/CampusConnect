package com.kharismastudio.campusconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.kharismastudio.campusconnect.presentation.AppNavigation
import com.kharismastudio.campusconnect.ui.theme.CampusConnectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // WAJIB ada agar hiltViewModel() di HomeScreen bisa bekerja
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CampusConnectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //HomeScreen() // Dipanggil bersih tanpa parameter manual lagi!
                    //AnnouncementScreen() // MODUL 5
                    AppNavigation()

                }
            }
        }
    }
}