package com.kharismastudio.campusconnect.data.remote
import java.security.Timestamp

data class TaskDocument(
    val judul: String = "",
    val deskripsi: String = "",
    val deadline: Timestamp? = null,
    val matakuliahId: Long = 0 )