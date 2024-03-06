package com.example.loremperpus.core.data.source.remote.response

import com.example.loremperpus.core.data.source.models.Book

data class BookDetailResponse (
    val data: Book?=null,
    val message: String,
    val status: String
)