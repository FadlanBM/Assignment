package com.example.loremperpus.core.data.source.remote.response

import com.example.loremperpus.core.data.source.models.Lending

data class ListLending(
    val data: List<Lending>,
    val message: String,
    val status: String
)