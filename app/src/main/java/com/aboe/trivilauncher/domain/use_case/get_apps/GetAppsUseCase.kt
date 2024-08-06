package com.aboe.trivilauncher.domain.use_case.get_apps

import com.aboe.trivilauncher.domain.model.CompactAppInfo
import com.aboe.trivilauncher.domain.repository.AppRepository
import javax.inject.Inject

class GetAppsUseCase @Inject constructor(
    private val appRepository: AppRepository
) {
    suspend operator fun invoke(): List<CompactAppInfo> {
        return appRepository.getInstalledApps()
            .filter { it.isLaunchable }
            .sortedBy { it.label.lowercase() }
    }
}