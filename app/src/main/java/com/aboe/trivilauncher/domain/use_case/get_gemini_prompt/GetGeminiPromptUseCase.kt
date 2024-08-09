package com.aboe.trivilauncher.domain.use_case.get_gemini_prompt

import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.util.Log
import com.aboe.trivilauncher.common.Constants
import com.aboe.trivilauncher.domain.use_case.get_app.GetAppUseCase
import com.aboe.trivilauncher.domain.use_case.get_app.StringType
import com.aboe.trivilauncher.domain.use_case.get_notifications.GetNotificationsUseCase
import com.aboe.trivilauncher.domain.use_case.get_user_location.GetUserLocationUseCase
import com.aboe.trivilauncher.domain.use_case.get_weather_forecast.GetWeatherForecastUseCase
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class GetGeminiPromptUseCase @Inject constructor(
    private val getNotifications: GetNotificationsUseCase,
    private val getUserLocation: GetUserLocationUseCase,
    private val getWeatherForecast: GetWeatherForecastUseCase,
    private val getApp: GetAppUseCase,
    @ApplicationContext private val context: Context
){
    val TAG = "getGeminiPrompt"

    // maybe use an object for this
    suspend operator fun invoke(prompt: String? = null, addContext: Boolean = true) : Pair<String, List<Content>> {
        val userName = "Not implemented"
        val userInfo = "Not implemented"

        val currentTime = SimpleDateFormat("HH:mm:ss, dd/MM/yyyy", Locale.getDefault()).format(Date())

        val (notifications, userLocation, installedApps) = coroutineScope {
            val notificationsDeferred = async { getNotifications() }
            val userLocationDeferred = async { getUserLocation() }
            val installedAppsDeferred = async { getAllAppPackagesAndNames(context) }

            Triple(
                notificationsDeferred.await(),
                userLocationDeferred.await(),
                installedAppsDeferred.await()
            )
        }

        val weatherForecast = userLocation?.let {
            getWeatherForecast(lat = it.latitude, lon = it.longitude)
        } ?: "No weather forecast available"

        val userLocationString = userLocation?.let { location ->
            getUserAddress(context, location.latitude, location.longitude)
        } ?: "Location not available"

        // use StringBuilder for better performance
        val systemContext = buildString {
            appendLine("--------------------------------------------------")
            appendLine("SYSTEM PROMPT: ${Constants.SYSTEM_PROMPT}")
//            appendLine("")
//            appendLine("PERSONALITY SETTING: behave like X")
            appendLine("--------------------------------------------------")
            appendLine("PROMPT CONTEXT:")
            // last request
            appendLine("Current time: $currentTime")
            appendLine("User name: $userName")
            appendLine("User info: $userInfo")
            appendLine("User location: $userLocationString")
            appendLine("")

            appendLine("Weather forecast (next ${Constants.MAX_WEATHER_FORECAST_ITEMS * 3} hours):")
            appendLine(weatherForecast)
            // appendLine("")

            appendLine("Notifications (past ${Constants.MAX_NOTIFICATION_AGE_HOURS} hours):")
            appendLine(notifications)
            //appendLine("")

            appendLine("Installed Apps used more than ${Constants.MIN_APP_USAGE_TIME_MINUTES} minutes in the last ${Constants.MAX_APP_USAGE_AGE_DAYS} days:")
            appendLine(installedApps)
            appendLine("")

//            appendLine("Recent interactions:")
//            appendLine("Not implemented")
//            appendLine("")
//
//            appendLine("Upcoming events:")
//            appendLine("Not implemented")
//            appendLine("")
//
//            appendLine("Knowledge base about user")
//            appendLine("Not implemented")
//            appendLine("")
            appendLine("--------------------------------------------------")
        }

        val history = if (addContext) listOf(content(role = "model") { text(systemContext) }) else emptyList()
        val promptString = prompt ?: Constants.DEFAULT_PROMPT

        return Pair(promptString, history)
    }

    // move to UseCase
    // responsible for ApkAssets spam, try caching
    private suspend fun getAllAppPackagesAndNames(context: Context): String {
        val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE)
                as UsageStatsManager

        val endTime = System.currentTimeMillis()
        val startTime = endTime - (Constants.MAX_APP_USAGE_AGE_DAYS * 24 * 60 * 60 * 1000)

        val appUsageMap = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        ).
        groupBy { usage ->
                try {
                    getApp(usage.packageName, StringType.APP_PACKAGE_NAME)?.label
                } catch (e: PackageManager.NameNotFoundException) {
                    null // Skip apps not found
                }
            }
            .filterKeys { it != null }
            .mapValues { (_, usageList) ->
                usageList.sumOf { it.totalTimeInForeground }
            }

        return appUsageMap.entries
            .filter { it.value > Constants.MIN_APP_USAGE_TIME_MINUTES * 60 * 1000 }
            .sortedByDescending { it.value }
            .joinToString("\n") { "- ${it.key}, ${it.value / (1000 * 60)} mins" }
    }

    // move to UseCase?
    private fun getUserAddress(context: Context, lat: Double, lon: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())

        return try {
            @Suppress("DEPRECATION")
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