package com.aboe.trivilauncher.domain.use_case.get_notifications

import android.util.Log
import com.aboe.trivilauncher.domain.model.NotificationItem
import com.aboe.trivilauncher.domain.repository.NotificationRepository
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {

    private val TAG = "GetNotificationsUseCase"

    suspend operator fun invoke(): List<NotificationItem> {
        try {
            notificationRepository.deleteNotifications()
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting notifications: ${e.message}")
        }

        try {
            return notificationRepository.getAllNotifications()
        } catch (e: Exception) {
            Log.e(TAG, "Error getting notifications: ${e.message}")
            return emptyList()
        }
    }

}