package com.kharismastudio.campusconnect.data.repository

//import com.kharismastudio.campusconnect.data.local.AnnouncementDao
//import com.kharismastudio.campusconnect.data.local.AnnouncementEntity
//import com.kharismastudio.campusconnect.data.remote.AnnouncementApi
//import com.kharismastudio.shared.Pengumuman
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//import javax.inject.Inject
//import javax.inject.Singleton

import com.kharismastudio.campusconnect.data.remote.AnnouncementApi //MODUL 5
import com.kharismastudio.shared.Pengumuman //MODUL 5
import retrofit2.HttpException  //MODUL 5
import java.io.IOException //MODUL 5

import com.kharismastudio.campusconnect.data.local.AnnouncementDao //MODUL 6
import com.kharismastudio.campusconnect.data.local.AnnouncementEntity //MODUL 6
import kotlinx.coroutines.flow.Flow //MODUL 6
import kotlinx.coroutines.flow.map //MODUL 6
import javax.inject.Inject //MODUL 6
import javax.inject.Singleton //MODUL 6


sealed class Result<out T> {
    data class Success<T>(
        val data: T
    ) : Result<T>()

    data class Error(
        val message: String
    ) : Result<Nothing>()
}

//@Singleton
//class AnnouncementRepository @Inject constructor(
//    private val api: AnnouncementApi,
//    private val dao: AnnouncementDao
//) {
//    // UI selalu membaca data dari Room (Single Source of Truth)
//    fun observeAnnouncements(): Flow<List<Pengumuman>> {
//        return dao.observeAll().map { entities ->
//            entities.map { entity ->
//                Pengumuman(
//                    id = entity.serverId,
//                    judul = entity.title,
//                    isi = entity.body
//                )
//            }
//        }
//    }
//
//    // Mengambil data terbaru dari REST API dan memperbarui cache Room
//    suspend fun refresh() {
//        val response = api.getAnnouncements()
//        val entities = response.map { item ->
//            AnnouncementEntity(
//                serverId = item.id,
//                title = item.title,
//                body = item.body,
//                cachedAt = System.currentTimeMillis()
//            )
//        }
//        dao.replaceAll(entities)
//    }
//}

// MODUL 5
//class AnnouncementRepository(
//    private val api: AnnouncementApi
//) {
//    suspend fun getAnnouncements(): Result<List<Pengumuman>> {
//
//        return try {
//            val response = api.getAnnouncements()
//            val data = response.map { item ->
//                Pengumuman(
//                    id = item.id,
//                    judul = item.title,
//                    isi = item.body
//                )
//            }
//
//            Result.Success(data)
//
//        } catch (e: IOException) {
//            Result.Error( "Tidak dapat terhubung ke server. Periksa koneksi internet." )
//        } catch (e: HttpException) {
//            Result.Error( "Server mengembalikan error HTTP ${e.code()}." )
//        } catch (e: Exception) {
//            Result.Error( "Gagal memuat pengumuman: ${e.message ?: "Kesalahan tidak diketahui"}")
//        }
//    }
//}

// MODUL 6
@Singleton
class AnnouncementRepository @Inject constructor(
    private val api: AnnouncementApi,
    private val dao: AnnouncementDao
) {
        suspend fun getAnnouncements(): Result<List<Pengumuman>> {

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
            Result.Error( "Tidak dapat terhubung ke server. Periksa koneksi internet." )
        } catch (e: HttpException) {
            Result.Error( "Server mengembalikan error HTTP ${e.code()}." )
        } catch (e: Exception) {
            Result.Error( "Gagal memuat pengumuman: ${e.message ?: "Kesalahan tidak diketahui"}")
        }
    }

    // UI selalu membaca data dari Room.
    fun observeAnnouncements(): Flow<List<Pengumuman>> {
        return dao.observeAll().map { entities ->
            entities.map { entity ->
                Pengumuman(
                    id = entity.serverId,
                    judul = entity.title,
                    isi = entity.body
                )
            }
        }
    }

    // Mengambil data terbaru dari REST API
    // dan mengganti isi cache Room.
    suspend fun refresh() {
        val response = api.getAnnouncements()
        val entities = response.map { item ->
            AnnouncementEntity(
                serverId = item.id,
                title = item.title,
                body = item.body,
                cachedAt = System.currentTimeMillis()
            )
        }
        dao.replaceAll(entities)
    }
}