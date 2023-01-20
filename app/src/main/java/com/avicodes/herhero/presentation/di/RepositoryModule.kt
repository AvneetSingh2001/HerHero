package com.avicodes.herhero.presentation.di

import com.avicodes.herhero.data.repository.UserRepositoryImpl
import com.avicodes.herhero.data.repository.dataSource.UserDataSource
import com.avicodes.herhero.domain.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepositoryModule(userDataSource: UserDataSource): UserRepository {
        return UserRepositoryImpl(userDataSource)
    }
}