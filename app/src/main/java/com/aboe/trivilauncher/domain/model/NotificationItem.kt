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
        return "app: $packageName \n" +
                "timestamp: $timestamp \n" +
                "title: $title \n" +
                "subText: $subText \n" +
                "text: $text \n" +
                "bigText: $bigText \n"
    }
}
