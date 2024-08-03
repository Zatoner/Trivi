package com.aboe.trivilauncher.data.local.notifications.repository

import com.aboe.trivilauncher.common.Constants
import com.aboe.trivilauncher.data.local.notifications.NotificationDao
import com.aboe.trivilauncher.data.local.notifications.entity.NotificationEntity
import com.aboe.trivilauncher.domain.model.NotificationItem
import com.aboe.trivilauncher.domain.repository.AppRepository
import com.aboe.trivilauncher.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDao: NotificationDao,
    private val appRepository: AppRepository,
) : NotificationRepository {

    override suspend fun getAllNotifications(): List<NotificationItem> {
        val notifications = notificationDao.getAllNotifications()

        return notifications.map {
            val appName = appRepository.getAppByPackageName(it.packageName)?.label
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

}