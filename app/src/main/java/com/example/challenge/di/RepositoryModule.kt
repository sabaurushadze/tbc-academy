package com.example.challenge.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.challenge.data.common.HandleResponse
import com.example.challenge.data.repository.connection.ConnectionsRepositoryImpl
import com.example.challenge.data.repository.datastore.DataStoreRepositoryImpl
import com.example.challenge.data.repository.log_in.LogInRepositoryImpl
import com.example.challenge.data.service.connection.ConnectionsService
import com.example.challenge.domain.repository.connection.ConnectionsRepository
import com.example.challenge.domain.repository.datastore.DataStoreRepository
import com.example.challenge.domain.repository.log_in.LogInRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDatastoreRepository(dataStoreManager: DataStoreRepositoryImpl): DataStoreRepository

    @Binds
    @Singleton
    abstract fun bindLogInRepository(
        impl: LogInRepositoryImpl,
    ): LogInRepository

    @Binds
    @Singleton
    abstract fun bindConnectionsRepository(
        impl: ConnectionsRepositoryImpl,
    ): ConnectionsRepository

}

//@Module
//@InstallIn(SingletonComponent::class)
//object RepositoryModule {
//
//    @Provides
//    @Singleton
//    fun provideDataStoreRepository(dataStore: DataStore<Preferences>): DataStoreRepository {
//        return DataStoreRepositoryImpl(dataStore = dataStore)
//    }
//
//
//    @Binds
//    @Singleton
//    abstract fun bindLogInRepository(
//        impl: LogInRepositoryImpl,
//    ): LogInRepository
//
//    @Singleton
//    @Provides
//    fun provideConnectionsRepository(
//        connectionsService: ConnectionsService,
//        handleResponse: HandleResponse
//    ): ConnectionsRepository {
//        return ConnectionsRepositoryImpl(
//            connectionsService = connectionsService,
//            handleResponse = handleResponse
//        )
//    }
//
//}

