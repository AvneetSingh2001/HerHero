package com.avicodes.herhero.data.models

import java.security.Guard

data class Users(
    var id : String = "",
    var name : String? = null,
    var guardians: ArrayList<String>? = null,
    var superGuard: String? = null,
    var phone: String? = null,
    var location: String? = null
)