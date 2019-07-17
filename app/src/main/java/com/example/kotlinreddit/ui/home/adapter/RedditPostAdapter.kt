package com.example.kotlinreddit.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinreddit.R
import com.example.networking.data.RedditDataChild

class RedditPostAdapter : RecyclerView.Adapter<RedditPostViewHolder>() {

    private val data = mutableListOf<RedditDataChild>()

    fun setRedditPosts(posts: List<RedditDataChild>) {
        data.addAll(posts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RedditPostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_reddit_post, parent, false))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RedditPostViewHolder, position: Int) {
        holder.bindRedditPostToView(data[position])
    }

}