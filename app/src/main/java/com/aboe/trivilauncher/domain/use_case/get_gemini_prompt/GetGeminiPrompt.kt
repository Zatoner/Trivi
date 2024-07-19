package com.aboe.trivilauncher.domain.use_case.get_gemini_prompt

import android.app.Application
import android.app.usage.UsageStatsManager
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

        val userName = "Not implemented"
        val userInfo = "Not implemented"

        val currentTime = SimpleDateFormat("HH:mm:ss, dd/MM/yyyy", Locale.getDefault()).format(Date())
        // maybe not needed
        val installedApps = getAllAppPackagesAndNames(app)
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
            appendLine("Weather forecast (next ${Constants.MAX_WEATHER_FORECAST_ITEMS * 3} hours):")
            appendLine(weatherForecast)
            appendLine("-----")
            appendLine("Notifications (past ${Constants.MAX_NOTIFICATION_AGE_HOURS} hours):")
            appendLine(notifications)
            appendLine("-----")
            appendLine("Apps used more than ${Constants.MIN_APP_USAGE_TIME_MINUTES} minutes in the last ${Constants.MAX_APP_USAGE_AGE_DAYS} days:")
            appendLine(installedApps)
        }

        return finalPrompt
    }

    // move to UseCase
    private fun getAllAppPackagesAndNames(context: Context): String {
        val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE)
                as UsageStatsManager
        val packageManager = context.packageManager

        val endTime = System.currentTimeMillis()
        val startTime = endTime - (Constants.MAX_APP_USAGE_AGE_DAYS * 24 * 60 * 60 * 1000)

        val appUsageMap = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        ).
        groupBy { usage ->
                try {
                    val appInfo = packageManager.getApplicationInfo(usage.packageName, 0)
                    packageManager.getApplicationLabel(appInfo).toString()
                } catch (e: PackageManager.NameNotFoundException) {
                    null // Skip apps not found
                }
            }
            .filterKeys { it != null  }
            .mapValues { (_, usageList) ->
                usageList.sumOf { it.totalTimeInForeground }
            }

        return appUsageMap.entries
            .filter { it.value > Constants.MIN_APP_USAGE_TIME_MINUTES * 60 * 1000 }
            .sortedByDescending { it.value }
            .joinToString("\n") { "${it.key}, ${it.value / (1000 * 60)} mins" }
    }

    // move to UseCase?
    private fun getUserAddress(context: Context, lat: Double, lon: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())

        return try {
            geocoder.getFromLocation(lat, lon, 1)
                ?.firstOrNull()?.let { address ->
                    val city = address.locality ?: "Unknown city"
                    val state = address.adminArea ?: "Unknown state"
                    val country = address.countryName ?: "Unknown country"

                    "$city, $state, $country"
                } ?: "Unknown"
        }
        catch (e: Exception) {
            Log.e(TAG, "Error getting user address ${e.message}", e)
            "Unknown"
        }
    }
}