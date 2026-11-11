package com.kharismastudio.campusconnect.di

import com.apollographql.apollo.ApolloClient
import com.kharismastudio.campusconnect.data.repository.InfoMitraRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApolloModule {

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://trevorblades.com")
            .build()
    }

    @Provides
    @Singleton
    fun provideInfoMitraRepository(
        apolloClient: ApolloClient
    ): InfoMitraRepository {
        return InfoMitraRepository(apolloClient)
    }
}
