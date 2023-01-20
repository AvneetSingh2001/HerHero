package com.avicodes.herhero.presentation.di

import android.app.Activity
import android.provider.Settings.Global.getString
import com.avicodes.herhero.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @Singleton
    fun provideFirbaseAuth(): FirebaseAuth  {
        return FirebaseAuth.getInstance()
    }
}