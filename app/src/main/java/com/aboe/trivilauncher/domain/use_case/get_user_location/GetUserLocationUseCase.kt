package com.aboe.trivilauncher.domain.use_case.get_user_location

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class GetUserLocationUseCase @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    @ApplicationContext private val context: Context
) {
    val TAG = "getUserLocationUseCase"

    suspend operator fun invoke(): Location? {
        // do better permission handling in the future
        val fineLocationPerm = (ContextCompat
            .checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION))

        val coarseLocationPerm = (ContextCompat
            .checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION))

        if (fineLocationPerm != PackageManager.PERMISSION_GRANTED ||
            coarseLocationPerm != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Permission not granted")
            return null
        }

        return try {
            suspendCancellableCoroutine { continuation ->
                fusedLocationProviderClient
                    .lastLocation
                    .addOnSuccessListener { location ->
                        continuation.resume(location)
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error getting last known location ${e.message}", e)
                        continuation.resume(null)
                    }

                continuation.invokeOnCancellation {
                    Log.e(TAG, "getUserLocationUseCase cancelled")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting location: ${e.message}", e)
            null
        }
    }

}