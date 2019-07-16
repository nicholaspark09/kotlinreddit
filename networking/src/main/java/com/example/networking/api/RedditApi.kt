package com.example.networking.api

import com.example.networking.data.RedditResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {

    @GET("top.json")
    fun getPosts(
        @Query("after") after: String,
        @Query("limit") limit: Int
    ): Single<RedditResponse>
}