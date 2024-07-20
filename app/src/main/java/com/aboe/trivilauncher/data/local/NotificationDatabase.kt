package com.aboe.trivilauncher.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aboe.trivilauncher.data.local.entity.NotificationEntity

@Database(
    entities = [NotificationEntity::class],
    version = 5,
    exportSchema = false
)

abstract class NotificationDatabase : RoomDatabase() {

    abstract val notificationDao: NotificationDao

}