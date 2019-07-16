package com.example.networking.repo

import com.example.networking.data.RedditData
import com.example.networking.data.RedditPost
import io.reactivex.Completable
import io.reactivex.Single

interface RedditDataSource {

    fun getRedditEntries(after: String, limit: Int): Single<RedditData>

    fun savePost(post: RedditPost): Completable

    fun getPost(postId: String): Single<RedditPost>

    fun refresh()
}