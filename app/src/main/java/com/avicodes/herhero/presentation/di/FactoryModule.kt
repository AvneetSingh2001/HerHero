package com.avicodes.herhero.presentation.di

import com.avicodes.herhero.domain.UserRepository
import com.avicodes.herhero.presentation.ui.AddGuardianViewModelFactory
import com.avicodes.herhero.presentation.ui.HomeScreenViewModel
import com.avicodes.herhero.presentation.ui.HomeScreenViewModelFactory
import com.avicodes.herhero.presentation.ui.authScreen.DetailsViewModelFactory
import com.avicodes.herhero.presentation.ui.authScreen.MainActivityViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {


    @Provides
    @Singleton
    fun provideMainViewModelFactory(userRepository: UserRepository) : MainActivityViewModelFactory {
        return MainActivityViewModelFactory(userRepository)
    }

    @Provides
    @Singleton
    fun provideDetailViewModelFactory(userRepository: UserRepository, auth: FirebaseAuth) : DetailsViewModelFactory{
        return DetailsViewModelFactory(userRepository, auth)
    }

    @Provides
    @Singleton
    fun provideHomeScreenViewModelFactory(userRepository: UserRepository, auth: FirebaseAuth) : HomeScreenViewModelFactory{
        return HomeScreenViewModelFactory(userRepository, auth)
    }

    @Provides
    @Singleton
    fun provideAddGuardianViewModelFactory(userRepository: UserRepository, auth: FirebaseAuth) : AddGuardianViewModelFactory{
        return AddGuardianViewModelFactory(userRepository, auth)
    }
}