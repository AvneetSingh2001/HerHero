package com.avicodes.herhero.presentation.di

import android.app.Application
import com.avicodes.herhero.presentation.ui.adapters.GuardiansAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Provides
    @Singleton
    fun provideGuardianAdapter(application: Application) : GuardiansAdapter {
        return GuardiansAdapter(application)
    }
}