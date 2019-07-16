package com.example.networking

import android.content.Context
import android.support.test.InstrumentationRegistry
import com.example.networking.repo.RedditDataSource
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RedditRepositoryTest {

    lateinit var context: Context
    lateinit var redditRepository: RedditDataSource

    @Before
    fun setup() {
        context = InstrumentationRegistry.getContext()

        Networking.init(context)
        redditRepository = Networking.get().redditRepository
    }

    @After
    fun tearDown() {
        redditRepository.refresh()
    }

    @Test
    fun getRedditEntries_testLimit50() {
        // When
        val results = redditRepository.getRedditEntries("", 50).blockingGet()

        // Then
        assertEquals(50, results.children.size)
    }

    @Test
    fun getRedditEntries_testSavePost() {
        // Given
        val results = redditRepository.getRedditEntries("", 50).blockingGet()
        val firstPost = results.children.first().redditPost

        // When
        redditRepository.savePost(firstPost)

        // Then
        val savedPost = redditRepository.getPost(firstPost.id).blockingGet()
        assertEquals(firstPost.id, savedPost.id)
    }
}