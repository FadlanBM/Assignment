package com.example.loremperpus.core.data.source.remote.response

import com.example.loremperpus.core.data.source.models.Book

data class ListHistorylResponse (
    val Book: List<Book>,
    val message: String,
    val status: String
)