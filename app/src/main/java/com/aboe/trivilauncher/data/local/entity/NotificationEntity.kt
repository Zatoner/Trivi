package com.aboe.trivilauncher.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aboe.trivilauncher.domain.model.NotificationItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
            title = title ?: "No Title",
            subText = subText ?: "No SubText",
            text = text ?: "No Text",
            bigText = bigText ?: "No BigText",
            packageName = packageName,

            timestamp = SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss",
                Locale.getDefault()
            ).format(Date(timestamp))
        )
    }
}
