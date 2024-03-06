package com.example.loremperpus.core.data.source.remote.request

data class RatingsRequest(
    val buku_id: Int,
    val messages: String,
    val ratings: Float
)