package com.example.onlinerapp.di

import com.example.onlinerapp.database.CategoryDao
import com.example.onlinerapp.notifications.NotificationManager
import com.example.onlinerapp.remote.RemoteDataSource
import com.example.onlinerapp.remote.OnlinerAPI
import com.example.onlinerapp.repository.Repository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// DI class; a module for providing all the dependencies for our application
@Module
@InstallIn(SingletonComponent::class)
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
            OnlinerAPI = retrofit.create(OnlinerAPI::class.java)

    @Singleton
    @Provides
    fun provideCategoryRemoteDataSource(categoryService: OnlinerAPI) = RemoteDataSource(categoryService)

    //@Singleton
    //@Provides
    //fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDB(appContext)

    //@Singleton
    //@Provides
    //fun provideCategoryDao(db: AppDatabase) = db.categoryDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: RemoteDataSource,
                          localDataSource: CategoryDao) =
        Repository(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideNotificationManager(repository: Repository) =
            NotificationManager(repository)
}