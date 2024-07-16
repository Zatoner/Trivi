package com.aboe.trivilauncher.domain.model

data class NotificationItem(
    val id: Int,
    val title: String,
    val subText: String,
    val text: String,
    val bigText: String,
    val packageName: String,
    val timestamp: String
) {
    override fun toString(): String {
        return """
            Notification at $timestamp:
                id: $id
                app: $packageName
                title: $title
                subText: $subText
                text: $text
                bigText: $bigText
        """.trimIndent()
    }
}
