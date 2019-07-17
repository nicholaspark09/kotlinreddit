package com.example.kotlinreddit.ui.home

import android.annotation.SuppressLint
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.networking.data.RedditData
import com.example.networking.repo.RedditDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@VisibleForTesting const val LIMIT = 20

class HomeViewModel @Inject constructor(
    @VisibleForTesting val redditRepo: RedditDataSource
): ViewModel() {

    var afterId: String = ""
    val redditResponseState = MutableLiveData<RedditResponseState>().apply { RedditResponseState.None }

    @SuppressLint("CheckResult")
    fun getRedditResponses() {
        redditResponseState.postValue(RedditResponseState.Loading)
        redditRepo.getRedditEntries(afterId, LIMIT)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    redditResponseState.postValue(RedditResponseState.Success(it))
                    afterId = it.after
                },
                {
                    redditResponseState.postValue(RedditResponseState.Error(it))
                }
            )
    }
}

sealed class RedditResponseState {
    object None : RedditResponseState()
    object Loading : RedditResponseState()
    data class Error(val throwable: Throwable) : RedditResponseState()
    data class Success(val redditData: RedditData) : RedditResponseState()
}