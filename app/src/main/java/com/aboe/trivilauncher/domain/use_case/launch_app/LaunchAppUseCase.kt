package com.aboe.trivilauncher.domain.use_case.launch_app

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LaunchAppUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    operator fun invoke(appPackageName: String) : Boolean {
        val launchIntent = context.packageManager.getLaunchIntentForPackage(appPackageName)
        return if (launchIntent != null) {
            context.startActivity(launchIntent)
            true
        } else {
            false
        }
    }
}