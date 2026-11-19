package com.kharismastudio.campusconnect.presentation

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage

@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val currentUser = FirebaseAuth.getInstance().currentUser
    val uid = currentUser?.uid
    val email = currentUser?.email ?: "Email tidak tersedia"

    // State untuk menyimpan URL foto profil dan status proses
    var photoUrl by remember { mutableStateOf<String?>(null) }
    var isUploading by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf<String?>(null) }
    var isError by remember { mutableStateOf(false) }

    // 1. Membaca dokumen users/{uid} dari Firestore saat layar dibuka (Langkah 4.n)
    LaunchedEffect(uid) {
        if (uid != null) {
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        photoUrl = document.getString("photoUrl")
                    }
                }
        }
    }

    // 2. Launcher untuk membuka galeri dan memproses gambar terpilih (Langkah 4.f - 4.m)
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            // Cek ukuran file (Langkah 4.h - 4.i)
            val fileSize = getFileSize(context, uri)
            val maxBytes = 2L * 1024L * 1024L // 2 MB

            if (fileSize <= 0L) {
                isError = true
                statusMessage = "Ukuran file tidak dapat divalidasi."
                return@rememberLauncherForActivityResult
            }

            if (fileSize > maxBytes) {
                isError = true
                statusMessage = "Ukuran file melebihi batas maksimal 2 MB."
                return@rememberLauncherForActivityResult
            }

            // Cek ketersediaan UID (Langkah 4.j - 4.k)
            if (uid == null) {
                isError = true
                statusMessage = "Sesi berakhir. Silakan login kembali."
                return@rememberLauncherForActivityResult
            }

            // Mulai proses upload ke Cloud Storage (Langkah 4.l)
            isUploading = true
            isError = false
            statusMessage = null

            val storageRef = FirebaseStorage.getInstance()
                .reference
                .child("profile_photos/$uid.jpg")

            storageRef.putFile(uri)
                .addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                        val urlString = downloadUri.toString()

                        // Simpan URL ke Firestore collection users (Langkah 4.m)
                        val userData = mapOf(
                            "email" to email,
                            "photoUrl" to urlString
                        )

                        FirebaseFirestore.getInstance()
                            .collection("users")
                            .document(uid)
                            .set(userData, SetOptions.merge())
                            .addOnSuccessListener {
                                photoUrl = urlString
                                isUploading = false
                                isError = false
                                statusMessage = "Foto profil berhasil diperbarui."
                            }
                            .addOnFailureListener { e ->
                                isUploading = false
                                isError = true
                                statusMessage = "Gagal menyimpan data profil: ${e.localizedMessage}"
                            }
                    }.addOnFailureListener { e ->
                        isUploading = false
                        isError = true
                        statusMessage = "Gagal mengambil URL foto: ${e.localizedMessage}"
                    }
                }
                .addOnFailureListener { e ->
                    isUploading = false
                    isError = true
                    statusMessage = "Upload gagal: ${e.localizedMessage}"
                }
        }
    }

    // 3. Tampilan UI Halaman Profil (Langkah 4.e & 4.o)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Profil Pengguna",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Foto Profil atau Placeholder jika URL belum tersedia
        if (!photoUrl.isNullOrBlank()) {
            AsyncImage(
                model = photoUrl,
                contentDescription = "Foto Profil",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Placeholder Foto Profil",
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Email Pengguna
        Text(
            text = email,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Indikator loading saat upload berlangsung
        if (isUploading) {
            CircularProgressIndicator(modifier = Modifier.padding(bottom = 16.dp))
        }

        // Pesan status (sukses / error validasi 2 MB / error upload)
        statusMessage?.let { msg ->
            Text(
                text = msg,
                color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Tombol Ganti Foto (Langkah 4.g)
        Button(
            onClick = { imagePicker.launch("image/*") },
            enabled = !isUploading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ganti Foto")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Tombol Logout (Langkah 2.o)
        OutlinedButton(
            onClick = { authViewModel.logout() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
    }
}

// Fungsi pembantu untuk memeriksa ukuran file (Langkah 4.h)
private fun getFileSize(
    context: Context,
    uri: Uri
): Long {
    return try {
        context.contentResolver
            .openAssetFileDescriptor(uri, "r")
            ?.use { descriptor ->
                descriptor.length
            } ?: -1L
    } catch (e: Exception) {
        -1L
    }
}
