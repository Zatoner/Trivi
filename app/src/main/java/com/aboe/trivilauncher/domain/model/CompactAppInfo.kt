package com.aboe.trivilauncher.domain.model

import android.graphics.drawable.Drawable

data class CompactAppInfo(
    val label: String,
    val packageName: String,
    val icon: Drawable,
    val isLaunchable: Boolean = false
)
