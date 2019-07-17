package com.example.kotlinreddit.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinreddit.R
import com.example.kotlinreddit.ui.home.adapter.RedditPostAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


class HomeFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: HomeViewModel
    private val adapter: RedditPostAdapter by lazy { RedditPostAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        setRecyclerView()
        observerRedditResponses()
        viewModel.getRedditResponses()
    }

    private fun setRecyclerView() {
        homeRecyclerView.layoutManager = LinearLayoutManager(context)
        homeRecyclerView.adapter = adapter
    }

    private fun observerRedditResponses() {
        viewModel.redditResponseState.observe(this, Observer {
            when (it) {
                is RedditResponseState.None -> hideProgressBar()
                is RedditResponseState.Loading ->  showProgressBar()
                is Error -> {
                    hideProgressBar()
                    Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
                }
                is RedditResponseState.Success -> handleRedditSuccess(it)
            }
        })
    }

    private fun handleRedditSuccess(successfulState: RedditResponseState.Success) {
        hideProgressBar()
        adapter.setRedditPosts(successfulState.redditData.children)
    }

    private fun hideProgressBar() {
        homeProgressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        homeProgressBar.visibility = View.VISIBLE
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
