package com.example.loremperpus.core.data.source.models

data class Ratings(
    val ID: Int,
    val BorrowID: Int,
    val borrower_name: String,
    val message: String,
    val ratings: Int
)