package com.aboe.trivilauncher.domain.repository

import com.aboe.trivilauncher.domain.model.CompactAppInfo

interface AppRepository {

    suspend fun getInstalledApps(): List<CompactAppInfo>

    suspend fun getAppByPackageName(packageName: String): CompactAppInfo?

    suspend fun getAppByLabel(label: String): CompactAppInfo?

}