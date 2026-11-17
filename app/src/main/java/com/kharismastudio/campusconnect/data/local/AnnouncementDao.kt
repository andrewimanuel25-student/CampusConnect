package com.kharismastudio.campusconnect.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface AnnouncementDao {
    @Query("SELECT * FROM announcements ORDER BY serverId ASC")
    fun observeAll(): Flow<List<AnnouncementEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<AnnouncementEntity>)

    @Query("DELETE FROM announcements")
    suspend fun deleteAll()

    @Transaction
    suspend fun replaceAll(items: List<AnnouncementEntity>) {
        deleteAll()
        insertAll(items)
    }
}
