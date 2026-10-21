package com.kharismastudio.campusconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kharisma.campusconnect.shared.CourseRepositoryImpl //dari modul
import com.kharismastudio.campusconnect.ui.theme.CampusConnectTheme
import com.kharismastudio.campusconnect.ui.theme.HomeScreen



//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            CampusConnectTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
//    }
//}

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            CampusConnectTheme {
//                Surface(modifier = Modifier.fillMaxSize()) {
//                    HomeScreen(daftarMK = daftarMataKuliahDummy)
//                }
//            }
//        }
//    }
//}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Buat instance dari repository modul shared
        val repository = CourseRepositoryImpl()

        setContent {
            CampusConnectTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Panggil HomeScreen dengan data dari repository baru
                    HomeScreen(daftarMK = repository.getAllCourses())
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CampusConnectTheme {
        Greeting("Android")
    }
}