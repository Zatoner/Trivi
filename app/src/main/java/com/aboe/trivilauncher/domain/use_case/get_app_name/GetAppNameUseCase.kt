package com.aboe.trivilauncher.domain.use_case.get_app_name

import com.aboe.trivilauncher.domain.repository.AppRepository
import javax.inject.Inject

class GetAppNameUseCase @Inject constructor(
    private val appRepository: AppRepository
) {

    suspend operator fun invoke(packageName: String): String? {
        return appRepository.getAppByPackageName(packageName)?.label
    }

}