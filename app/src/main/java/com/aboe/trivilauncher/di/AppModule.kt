package com.aboe.trivilauncher.di

import android.app.Application
import androidx.room.Room
import com.aboe.trivilauncher.data.local.NotificationDatabase
import com.aboe.trivilauncher.data.local.repository.NotificationRepositoryImpl
import com.aboe.trivilauncher.domain.repository.NotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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

}