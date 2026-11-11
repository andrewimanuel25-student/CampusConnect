package com.kharismastudio.campusconnect.di

import com.kharismastudio.campusconnect.data.remote.AnnouncementApi
import com.kharismastudio.campusconnect.data.repository.AnnouncementRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

import com.kharismastudio.campusconnect.data.NotificationSocketManager


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }
    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(
                "https://jsonplaceholder.typicode.com/"
            )
            .client(client)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }
    @Provides
    @Singleton
    fun provideAnnouncementApi(
        retrofit: Retrofit
    ): AnnouncementApi {
        return retrofit.create(
            AnnouncementApi::class.java
        )
    }
    @Provides
    @Singleton
    fun provideAnnouncementRepository(
        api: AnnouncementApi
    ): AnnouncementRepository {
        return AnnouncementRepository(api)

    }
    @Provides
    @Singleton
    fun provideNotificationSocketManager(): NotificationSocketManager {
        return NotificationSocketManager()
    }
}