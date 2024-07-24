package com.aboe.trivilauncher.domain.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class NotificationItem(
    val id: Long,
    val title: String?,
    val subText: String?,
    val text: String?,
    val bigText: String?,
    val packageName: String,
    val timestamp: Long
) {
    override fun toString(): String {

        val time = SimpleDateFormat(
            "HH:mm",
            Locale.getDefault()
        ).format(Date(timestamp))

        val titleString = title ?: "No Title"
        val subTextString = if (subText != null) "[${subText}]" else ""
        val textString = (text ?: "").replace("\n", " ")

        return "- $time: $titleString $subTextString($packageName) - $textString "
    }
}
