package com.aboe.trivilauncher.domain.use_case.get_app

import com.aboe.trivilauncher.domain.model.CompactAppInfo
import com.aboe.trivilauncher.domain.repository.AppRepository
import javax.inject.Inject

enum class StringType {
    APP_NAME,
    APP_PACKAGE_NAME,
}

class GetAppUseCase @Inject constructor(
    private val appRepository: AppRepository
) {

    suspend operator fun invoke(packageName: String, type: StringType): CompactAppInfo? {
        return when (type) {
            StringType.APP_NAME -> appRepository.getAppByLabel(packageName)
            StringType.APP_PACKAGE_NAME -> appRepository.getAppByPackageName(packageName)
        }
    }

}