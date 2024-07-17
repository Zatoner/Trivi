package com.aboe.trivilauncher.di

import android.app.Application
import androidx.room.Room
import com.aboe.trivilauncher.data.local.NotificationDatabase
import com.aboe.trivilauncher.data.local.repository.NotificationRepositoryImpl
import com.aboe.trivilauncher.data.remote.OpenWeatherApi
import com.aboe.trivilauncher.data.remote.repository.WeatherRepositoryImpl
import com.aboe.trivilauncher.domain.repository.NotificationRepository
import com.aboe.trivilauncher.domain.repository.WeatherRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): NotificationDatabase {
        return Room.databaseBuilder(
            app,
            NotificationDatabase::class.java,
            "notifications_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNotificationRepository(db: NotificationDatabase, app: Application): NotificationRepository {
        return NotificationRepositoryImpl(db.notificationDao, app)
    }

    @Provides
    @Singleton
    fun provideOpenWeatherApi(): OpenWeatherApi {

//        val loggingInterceptor = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//
//        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor)
//            .build()

        return Retrofit.Builder()
            .baseUrl(OpenWeatherApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
            .build()
            .create(OpenWeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: OpenWeatherApi): WeatherRepository {
        return WeatherRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideFusedLocationClient(app: Application): FusedLocationProviderClient {
        return LocationServices
            .getFusedLocationProviderClient(app)
    }

}