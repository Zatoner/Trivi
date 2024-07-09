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
            val bundle: Bundle = sbn.notification.extras
            val textCharSequence = bundle.getCharSequence("android.text")
            val subTextCharSequence = bundle.getCharSequence("android.subText")
            val titleCharSequence = bundle.getCharSequence("android.title")
            val bigTextCharSequence = bundle.getCharSequence("android.bigText")

            val text: String? = when (textCharSequence) {
                is SpannableString -> textCharSequence.toString()
                is String -> textCharSequence
                else -> null
            }

            val subText: String? = when (subTextCharSequence) {
                is SpannableString -> subTextCharSequence.toString()
                is String -> subTextCharSequence
                else -> null
            }

            val title: String? = when (titleCharSequence) {
                is SpannableString -> titleCharSequence.toString()
                is String -> titleCharSequence
                else -> null
            }

            val bigText: String? = when (bigTextCharSequence) {
                is SpannableString -> bigTextCharSequence.toString()
                is String -> bigTextCharSequence
                else -> null
            }


            val notification = NotificationEntity(
                id = sbn.id,
                title = title,
                subText = subText,
                text = text,
                bigText = bigText,
                packageName = sbn.packageName,
                timestamp = sbn.postTime
            )

            serviceScope.launch {
                addNotificationUseCase(notification)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

}