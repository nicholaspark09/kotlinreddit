package com.example.networking.repo

import com.example.networking.api.RedditApi
import com.example.networking.data.RedditPost
import io.reactivex.Completable
import io.reactivex.Single

class RedditRepository (
    val redditApi: RedditApi,
    val persistence: RedditPersistenceContract
) : RedditDataSource {

    override fun getRedditEntries(after: String, limit: Int) =
        redditApi.getPosts(after, limit)
            .flatMap {
                Single.just(it.data)
            }

    override fun savePost(post: RedditPost): Completable {
        persistence.savePost(post)
        return Completable.complete()
    }

    override fun getPost(postId: String): Single<RedditPost> {
        return persistence.getPost(postId)
    }

    override fun refresh() {
        persistence.clear()
    }
}