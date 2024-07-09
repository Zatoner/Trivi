package com.aboe.trivilauncher.domain.use_case.add_notification

import android.util.Log
import com.aboe.trivilauncher.data.local.entity.NotificationEntity
import com.aboe.trivilauncher.domain.repository.NotificationRepository
import javax.inject.Inject

class AddNotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {

    private val TAG = "AddNotificationUseCase"

    suspend operator fun invoke(notification: NotificationEntity) {
        try {
            notificationRepository.insertNotification(notification)
        } catch (e: Exception) {
            Log.e(TAG, "Error adding notification: ${e.message}")
        }

        try {
            notificationRepository.deleteNotifications()
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting notifications: ${e.message}")
        }
    }
}