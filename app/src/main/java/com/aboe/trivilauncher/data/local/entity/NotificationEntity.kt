package com.aboe.trivilauncher.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aboe.trivilauncher.domain.model.NotificationItem

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey val id: Int,
    val title: String?,
    val subText: String?,
    val text: String?,
    val bigText: String?,
    val packageName: String,
    val timestamp: Long
) {
    fun toNotificationItem(): NotificationItem {
        return NotificationItem(
            id = id,
            title = title,
            subText = subText,
            text = text,
            bigText = bigText,
            packageName = packageName,
            timestamp = timestamp,
        )
    }
}
