package com.kharismastudio.campusconnect.data.remote
import com.kharisma.campusconnect.data.remote.AnnouncementDto
import retrofit2.http.GET


interface AnnouncementApi {
    @GET("posts")
    suspend fun getAnnouncements(): List<AnnouncementDto>
}