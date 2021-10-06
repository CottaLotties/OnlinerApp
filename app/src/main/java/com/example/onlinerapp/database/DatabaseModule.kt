package com.example.onlinerapp.database

import android.content.Context
import androidx.room.Room
import com.example.onlinerapp.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

// Module that provides database for the application
@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao {
        return appDatabase.categoryDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
                appContext,
                AppDatabase::class.java,
                appContext.resources.getString(R.string.app_db_name)
        ).build()
    }
}