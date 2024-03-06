package com.example.loremperpus.core.data.source.remote.response

import com.example.loremperpus.core.data.source.models.Borrowers

data class MeResponse(
    val data: Borrowers?=null,
    val message: String,
    val status: String
)