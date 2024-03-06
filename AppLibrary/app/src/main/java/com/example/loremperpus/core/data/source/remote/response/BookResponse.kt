package com.example.loremperpus.core.data.source.remote.response

import com.example.loremperpus.core.data.source.models.Book

data class BookResponse(
    val data: List<Book>,
    val message: String,
    val status: String
)