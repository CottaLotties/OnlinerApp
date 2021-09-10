package com.example.onlinerapp.di

import android.content.Context
import com.example.onlinerapp.database.AppDatabase
import com.example.onlinerapp.database.CategoryDao
import com.example.onlinerapp.remote.RemoteDataSource
import com.example.onlinerapp.remote.RemoteService
import com.example.onlinerapp.repository.Repository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// DI class; a module for providing all the dependencies for our application
@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://catalog.api.onliner.by/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideCategoryService(retrofit: Retrofit):
            RemoteService = retrofit.create(RemoteService::class.java)

    @Singleton
    @Provides
    fun provideCategoryRemoteDataSource(categoryService: RemoteService) = RemoteDataSource(categoryService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDB(appContext)

    @Singleton
    @Provides
    fun provideCategoryDao(db: AppDatabase) = db.categoryDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: RemoteDataSource,
                          localDataSource: CategoryDao) =
        Repository(remoteDataSource, localDataSource)
}