package com.example.networking.repo

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.networking.data.RedditPost
import com.google.gson.Gson
import io.reactivex.Single

internal const val SHARED_KEY = "key:shared_networking"
internal const val KEY_POST = "key:post_"

class RedditPersistence(
    val context: Context,
    val gson: Gson = Gson(),
    val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_KEY, MODE_PRIVATE)
) : RedditPersistenceContract {

    override fun savePost(post: RedditPost) {
        sharedPreferences.edit().putString(KEY_POST + post.id, gson.toJson(post)).apply()
    }

    override fun getPost(postId: String): Single<RedditPost> {
        return Single.fromCallable<RedditPost> {
            val serializedPost = sharedPreferences.getString(KEY_POST + postId, "")
            if (serializedPost.isNotBlank()) {
                gson.fromJson(serializedPost, RedditPost::class.java)
            } else {
                throw IllegalStateException("Could not find the post")
            }
        }
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }

}