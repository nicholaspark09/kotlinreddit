package com.example.networking.repo

import com.example.networking.data.RedditPost
import io.reactivex.Single

interface RedditPersistenceContract {

    fun savePost(post: RedditPost)

    fun getPost(postId: String): Single<RedditPost>

    fun clear()
}