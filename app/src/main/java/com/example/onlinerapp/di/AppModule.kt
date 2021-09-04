package com.example.onlinerapp.di

import android.content.Context
import com.example.onlinerapp.database.AppDatabase
import com.example.onlinerapp.database.CategoryDao
import com.example.onlinerapp.remote.CategoryRemoteDataSource
import com.example.onlinerapp.remote.CategoryService
import com.example.onlinerapp.repository.CategoryRepository
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
            CategoryService = retrofit.create(CategoryService::class.java)

    @Singleton
    @Provides
    fun provideCategoryRemoteDataSource(categoryService: CategoryService) = CategoryRemoteDataSource(categoryService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDB(appContext)

    @Singleton
    @Provides
    fun provideCategoryDao(db: AppDatabase) = db.categoryDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: CategoryRemoteDataSource,
                          localDataSource: CategoryDao) =
        CategoryRepository(remoteDataSource, localDataSource)
}