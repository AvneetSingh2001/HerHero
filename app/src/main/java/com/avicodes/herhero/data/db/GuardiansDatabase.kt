package com.avicodes.herhero.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.avicodes.herhero.data.models.Guardians


@Database(
    entities = [Guardians::class],
    version = 1,
    exportSchema = false
)
abstract class GuardiansDatabase: RoomDatabase() {
    abstract fun guardiansDao() : GuardiansDao
}