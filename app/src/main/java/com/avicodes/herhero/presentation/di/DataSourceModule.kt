package com.avicodes.herhero.presentation.di

import com.avicodes.herhero.data.db.GuardiansDao
import com.avicodes.herhero.data.repository.dataSource.LocalDataSource
import com.avicodes.herhero.data.repository.dataSource.LocalDataSourceImpl
import com.avicodes.herhero.data.repository.dataSource.UserDataSource
import com.avicodes.herhero.data.repository.dataSource.UserDataSourceImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun provideUserDataSource(
        firestoreDb : FirebaseFirestore,
        auth: FirebaseAuth
    ): UserDataSource {
        return UserDataSourceImpl(firestoreDb, auth)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(dao: GuardiansDao): LocalDataSource {
        return LocalDataSourceImpl(dao)
    }
}