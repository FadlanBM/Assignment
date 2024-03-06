package com.example.loremperpus.core.data.source.models

data class Lending (
    val CreatedAt: String,
    val DeletedAt: Any,
    val Fine: Any,
    val ID: Int,
    val ListLending: Any,
    val UpdatedAt: String,
    val borrowdate: String,
    val borrowers_id: Int,
    var code: String,
    val employees_id: Int,
    val lastdate: String,
    val returnDate: String,
    val status: String
)