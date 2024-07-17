package com.aboe.trivilauncher.domain.use_case.get_gemini_prompt

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.util.Log
import com.aboe.trivilauncher.common.Constants
import com.aboe.trivilauncher.domain.use_case.get_notifications.GetNotificationsUseCase
import com.aboe.trivilauncher.domain.use_case.get_user_location.GetUserLocationUseCase
import com.aboe.trivilauncher.domain.use_case.get_weather_forecast.GetWeatherForecastUseCase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class GetGeminiPrompt @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val getUserLocationUseCase: GetUserLocationUseCase,
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase,
    private val app: Application
){

    val TAG = "getGeminiPrompt"

    suspend operator fun invoke(prompt: String = "default") : String {
        val actualPrompt = if (prompt == "default") Constants.DEFAULT_PROMPT else prompt

        val userName = ""
        val userInfo = ""

        val currentTime = SimpleDateFormat("HH:mm:ss, dd/MM/yyyy", Locale.getDefault()).format(Date())
        val installedApps = getAllAppPackagesAndNames(app.packageManager)
        val notifications = getNotificationsUseCase()
        val userLocation = getUserLocationUseCase()

        val weatherForecast = userLocation?.let {
            getWeatherForecastUseCase(lat = it.latitude, lon = it.longitude)
        } ?: "No weather forecast available"

        val userLocationString = userLocation?.let {
            getUserAddress(app, it.latitude, it.longitude)
        } ?: "Location not available"

        val finalPrompt = buildString {
            appendLine("SYSTEM PROMPT: ${Constants.SYSTEM_PROMPT}")
            appendLine("--------------------------------------------------")
            appendLine("PROMPT: $actualPrompt")
            appendLine("--------------------------------------------------")
            appendLine("USER CONTEXT:")
            appendLine("User name: $userName")
            appendLine("User info: $userInfo")
            appendLine("Current time: $currentTime")
            appendLine("User location: $userLocationString")
            appendLine("Weather forecast:")
            appendLine(weatherForecast)
            appendLine("-----")
            appendLine("Installed apps:")
            appendLine(installedApps)
            appendLine("Notifications:")
            appendLine(notifications)
        }

        return finalPrompt
    }

    private fun getAllAppPackagesAndNames(packageManager: PackageManager): String {
        return packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .joinToString("\n") { appInfo ->
                val packageName = appInfo.packageName
                val appName = appInfo.loadLabel(packageManager).toString()
                "app: $appName, package: $packageName"
            }
    }

    private fun getUserAddress(context: Context, lat: Double, lon: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())

        return try {
            geocoder.getFromLocation(lat, lon, 1)
                ?.firstOrNull()?.let { address ->
                    val city = address.locality ?: ""
                    val state = address.adminArea ?: ""
                    val country = address.countryName ?: ""

                    "$city, $state, $country"
                } ?: "Unknown"
        }
        catch (e: Exception) {
            Log.e(TAG, "Error getting user address ${e.message}", e)
            "Unknown"
        }
    }
}