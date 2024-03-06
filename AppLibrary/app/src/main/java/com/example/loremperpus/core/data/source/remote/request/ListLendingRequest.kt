package com.example.loremperpus.core.data.source.remote.request

data class ListLendingRequest(
    val book_id: Int,
    val lending_id: Int,
    val no_inventaris: String
)