package com.example.kotlinreddit.ui.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.networking.data.RedditDataChild
import kotlinx.android.synthetic.main.row_reddit_post.view.*


class RedditPostViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bindRedditPostToView(dataChild: RedditDataChild) {
        view.redditPostTitle.text = dataChild.redditPost.title
        view.redditPostAuthor.text = dataChild.redditPost.author
    }
}