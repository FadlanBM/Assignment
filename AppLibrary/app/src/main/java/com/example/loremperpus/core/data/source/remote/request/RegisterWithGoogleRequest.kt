package com.example.loremperpus.core.data.source.remote.request

data class RegisterWithGoogleRequest(
    val email: String,
    val google_id: String,
    val name: String
)