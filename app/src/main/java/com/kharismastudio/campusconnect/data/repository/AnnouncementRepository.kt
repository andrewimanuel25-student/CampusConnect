package com.kharismastudio.campusconnect.data.repository

import com.kharismastudio.campusconnect.data.remote.AnnouncementApi
import com.kharismastudio.shared.Pengumuman
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

sealed class Result<out T> {
    data class Success<T>(
        val data: T
    ) : Result<T>()
    data class Error(
        val message: String
    ) : Result<Nothing>()
}

class AnnouncementRepository @Inject constructor(
    private val api: AnnouncementApi
) {
    suspend fun getAnnouncements():
            Result<List<Pengumuman>> {
        return try {
            val response = api.getAnnouncements()
            val data = response.map { item ->
                Pengumuman(
                    id = item.id,
                    judul = item.title,
                    isi = item.body

                )
            }

            Result.Success(data)
        } catch (e: IOException) {
            Result.Error(
                "Tidak dapat terhubung ke server. Periksa koneksi internet."
            )
        } catch (e: HttpException) {
            Result.Error(
                "Server mengembalikan error HTTP ${e.code()}."
            )
        } catch (e: Exception) {
            Result.Error(
                "Gagal memuat pengumuman: ${e.message ?: "Kesalahan tidak diketahui"}"
            )
        }
    }
}