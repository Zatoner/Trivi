package com.aboe.trivilauncher.domain.repository

import com.aboe.trivilauncher.data.local.entity.NotificationEntity
import com.aboe.trivilauncher.domain.model.NotificationItem

interface NotificationRepository {

    suspend fun getAllNotifications(): List<NotificationItem>

    suspend fun insertNotification(notification: NotificationEntity)

    suspend fun deleteNotifications()

}