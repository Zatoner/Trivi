package com.aboe.trivilauncher.domain.service

import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.text.SpannableString
import com.aboe.trivilauncher.data.local.entity.NotificationEntity
import com.aboe.trivilauncher.domain.use_case.add_notification.AddNotificationUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationListener : NotificationListenerService() {

    @Inject
    lateinit var addNotificationUseCase: AddNotificationUseCase

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        sbn?.let {
            val bundle = sbn.notification.extras

            val notification = NotificationEntity(
                id = sbn.id,
                title = bundle.getStringOrNull("android.title"),
                subText = bundle.getStringOrNull("android.subText"),
                text = bundle.getStringOrNull("android.text"),
                bigText = bundle.getStringOrNull("android.bigText"),
                packageName = sbn.packageName,
                timestamp = sbn.postTime
            )

            serviceScope.launch {
//                if (serviceScope.isActive)
                // dont add notfications from system apps
                addNotificationUseCase(notification)
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        // TODO: Handle notification removal
        // add a dismissed field to the notification entity
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    private fun Bundle.getStringOrNull(key: String): String? {
        return when (val charSequence = getCharSequence(key)) {
            is SpannableString -> charSequence.toString()
            is String -> charSequence
            else -> null
        }
    }

}