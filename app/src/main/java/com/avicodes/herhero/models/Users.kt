package com.avicodes.herhero.models

data class Users(
    var id : String = "",
    var name : String = "",
    var imageUrl: String = "",
    var guardians: ArrayList<String> = ArrayList(),
    )