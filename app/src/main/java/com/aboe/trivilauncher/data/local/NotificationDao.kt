package com.aboe.trivilauncher.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aboe.trivilauncher.data.local.entity.NotificationEntity

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

    @Query("SELECT * FROM notifications ORDER BY timestamp DESC")
    suspend fun getAllNotifications(): List<NotificationEntity>

    // delete notifications which are older than timestamp
    @Query("DELETE FROM notifications WHERE timestamp < :timestamp")
    suspend fun deleteNotifications(timestamp: Long)

}