package com.example.networking.data

import com.google.gson.annotations.SerializedName

data class RedditResponse(
    @field:SerializedName("kind")
    val kind: String = "",
    @field:SerializedName("data")
    val data: RedditData
)