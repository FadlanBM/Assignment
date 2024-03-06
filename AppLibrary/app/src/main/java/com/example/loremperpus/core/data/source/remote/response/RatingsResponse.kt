package com.example.loremperpus.core.data.source.remote.response

import android.media.Rating
import com.example.loremperpus.core.data.source.models.Ratings

data class RatingsResponse(
    val data: List<Ratings>,
    val message: String,
    val status: String
)