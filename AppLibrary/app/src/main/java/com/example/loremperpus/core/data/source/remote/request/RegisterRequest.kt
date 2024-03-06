package com.example.loremperpus.core.data.source.remote.request

data class RegisterRequest(
    val address: String,
    val email: String,
    val google_id: String,
    val name: String,
    val phone_number: String
)