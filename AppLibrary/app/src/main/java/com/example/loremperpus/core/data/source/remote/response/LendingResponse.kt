package com.example.loremperpus.core.data.source.remote.response

import com.example.loremperpus.core.data.source.models.Book
import com.example.loremperpus.core.data.source.models.Lending

data class LendingResponse(
    val data: Lending?=null,
    val message: String,
    val status: String
)