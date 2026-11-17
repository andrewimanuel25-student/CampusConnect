package com.kharismastudio.campusconnect.data.repository

import com.kharismastudio.campusconnect.data.local.AnnouncementDao
import com.kharismastudio.campusconnect.data.local.AnnouncementEntity
import com.kharismastudio.campusconnect.data.remote.AnnouncementApi
import com.kharismastudio.shared.Pengumuman
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnnouncementRepository @Inject constructor(
    private val api: AnnouncementApi,
    private val dao: AnnouncementDao
) {
    // UI selalu membaca data dari Room (Single Source of Truth)
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

    // Mengambil data terbaru dari REST API dan memperbarui cache Room
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
