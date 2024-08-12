package com.aboe.trivilauncher.data.local.apps.repository

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.aboe.trivilauncher.domain.model.CompactAppInfo
import com.aboe.trivilauncher.domain.repository.AppRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : AppRepository {

    private var cachedApps: Map<String, CompactAppInfo>? = null
    private var appNameToPackageNameMap: Map<String, String>? = null

    private val packageManager: PackageManager = context.packageManager

    override suspend fun getInstalledApps(): List<CompactAppInfo> {
        cachedApps?.let { apps ->
            return apps.values.toList()
        }

        val launcherIntent = Intent(Intent.ACTION_MAIN)
        launcherIntent.addCategory(Intent.CATEGORY_LAUNCHER)

        val launchableActivities = packageManager.queryIntentActivities(launcherIntent, PackageManager.MATCH_ALL)
        val launchablePackageNames = launchableActivities.map {
            it.activityInfo.packageName
        }.toSet()

        val installedApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .map { appInfo ->
                val label = packageManager.getApplicationLabel(appInfo).toString()
                val packageName = appInfo.packageName
                val icon = packageManager.getApplicationIcon(appInfo)
                val isLaunchable = launchablePackageNames.contains(packageName)

                CompactAppInfo(label, packageName, icon, isLaunchable)
            }
            .associateBy { appInfo ->
                appInfo.packageName
            }

        cachedApps = installedApps
        appNameToPackageNameMap = installedApps.map { (packageName, appInfo) ->
            appInfo.label to packageName
        }.toMap()

        return installedApps.values.toList()
    }

    override suspend fun getAppByPackageName(packageName: String): CompactAppInfo? {
        cachedApps?.let { apps ->
            return apps[packageName]
        }

        return getInstalledApps().find { it.packageName == packageName }
    }

    override suspend fun getAppByLabel(label: String): CompactAppInfo? {
        cachedApps?.let { apps ->
            return apps.values.find { it.label == label }
        }

        return getInstalledApps().find { it.label == label }
    }

    fun invalidateCache() {
        cachedApps = null
        appNameToPackageNameMap = null
    }

}