package com.avicodes.herhero.presentation.di

import android.app.Application
import androidx.room.Room
import com.avicodes.herhero.data.db.GuardiansDao
import com.avicodes.herhero.data.db.GuardiansDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {


    @Provides
    @Singleton
    fun provideFireStoreDb(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideGuardianDb(app: Application): GuardiansDatabase {
        return Room.databaseBuilder(app, GuardiansDatabase::class.java, "guardian_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsDao(guardiansDatabase: GuardiansDatabase): GuardiansDao {
        return guardiansDatabase.guardiansDao()
    }
}