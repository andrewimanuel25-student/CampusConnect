package com.kharismastudio.campusconnect.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "announcements")
data class AnnouncementEntity(
    @PrimaryKey
    val serverId: Int,
    val title: String,
    val body: String,
    val cachedAt: Long
)
