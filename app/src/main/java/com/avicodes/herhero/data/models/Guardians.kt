package com.avicodes.herhero.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "guardians_table")
data class Guardians(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var isSuperGuard: Boolean = false,
    var phone: String = "",
): Serializable