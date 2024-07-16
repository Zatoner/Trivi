package com.aboe.trivilauncher.domain.use_case.get_notifications

import android.util.Log
import com.aboe.trivilauncher.domain.repository.NotificationRepository
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {

    private val TAG = "GetNotificationsUseCase"

    suspend operator fun invoke(): String {
        try {
            notificationRepository.deleteNotifications()
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting notifications: ${e.message}")
        }

        return try {
            val notifications =  notificationRepository.getAllNotifications()

            if (notifications.isEmpty()) {
                return "No notifications found"
            }

            val result = buildString {
                notifications.forEach { notification ->
                    appendLine(notification.toString())
                }
            }

            result
        } catch (e: Exception) {
            Log.e(TAG, "Error getting notifications: ${e.message}")
            "Could not get notifications"
        }
    }

}