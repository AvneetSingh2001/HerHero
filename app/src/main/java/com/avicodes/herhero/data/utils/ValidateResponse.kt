package com.avicodes.herhero.data.utils


data class ValidateResponse(
    val nameEmpty: Boolean = false,
    val phoneEmpty: Boolean = false,
    val locEmpty: Boolean = false,
    val phoneNotValid: Boolean = false,
    val success: Boolean = false,
    val loading : Boolean = false,
) {
}