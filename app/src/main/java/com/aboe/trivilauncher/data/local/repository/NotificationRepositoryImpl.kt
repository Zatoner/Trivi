package com.aboe.trivilauncher.data.local.repository

import android.app.Application
import android.content.pm.PackageManager
import com.aboe.trivilauncher.common.Constants
import com.aboe.trivilauncher.data.local.NotificationDao
import com.aboe.trivilauncher.data.local.entity.NotificationEntity
import com.aboe.trivilauncher.domain.model.NotificationItem
import com.aboe.trivilauncher.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDao: NotificationDao,
    app: Application
) : NotificationRepository {

    private val packageManager: PackageManager = app.packageManager

    override suspend fun getAllNotifications(): List<NotificationItem> {
        val notifications = notificationDao.getAllNotifications()

        return notifications.map {
            val appName = getAppNameFromPackageName(it.packageName)
            it.toNotificationItem().copy(packageName = appName ?: it.packageName)
        }
    }

    override suspend fun insertNotification(notification: NotificationEntity) {
        notificationDao.upsertNotification(notification)
    }

    override suspend fun deleteNotifications() {
        val currentTimeMillis = System.currentTimeMillis()
        val twentyFourHoursInMillis = Constants.MAX_NOTIFICATION_AGE_HOURS * 60 * 60 * 1000

        val cutoffTimestamp = currentTimeMillis - twentyFourHoursInMillis
        notificationDao.deleteNotifications(cutoffTimestamp)
    }

    // move this to data class?
    private fun getAppNameFromPackageName(packageName: String): String? {
        return try {
            val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
            packageManager.getApplicationLabel(applicationInfo).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }

}