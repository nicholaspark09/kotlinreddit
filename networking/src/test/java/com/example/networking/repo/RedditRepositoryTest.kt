package com.example.networking.repo

import com.example.networking.api.RedditApi
import com.example.networking.data.RedditData
import com.example.networking.data.RedditPost
import com.example.networking.data.RedditResponse
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RedditRepositoryTest {

    private val AFTER = "test:after_id"
    private val LIMIT = 100
    private val POST_ID = "test:id"

    @MockK lateinit var redditApi: RedditApi
    @MockK lateinit var persistence: RedditPersistenceContract
    @MockK lateinit var redditPost: RedditPost
    @MockK lateinit var redditData: RedditData
    @MockK lateinit var redditResponse: RedditResponse

    private lateinit var redditRepository: RedditDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        redditRepository = RedditRepository(redditApi, persistence)
    }

    @Test
    fun getRedditEntries_testApi() {
        // Given
        every { redditApi.getPosts(AFTER, LIMIT) } returns Single.just(redditResponse)
        every { redditResponse.data } returns redditData

        // When
        val single = redditRepository.getRedditEntries(AFTER, LIMIT)

        // Then
        single.subscribe { it ->
            assertEquals(redditData, it)
            verify { redditApi.getPosts(AFTER, LIMIT) }
        }
    }

    @Test
    fun savePost_testSavedToPersistence() {
        // Given
        every { persistence.savePost(redditPost) } just Runs

        // When
        redditRepository.savePost(redditPost).subscribe()

        // Then
        verify { persistence.savePost(redditPost) }
    }

    @Test
    fun getPost_testGetFromPersistence() {
        // Given
        every { persistence.getPost(POST_ID) } returns Single.just(redditPost)

        // When
        val single = redditRepository.getPost(POST_ID)

        // Then
        single.subscribe { it ->
            assertEquals(redditPost, it)
            verify { persistence.getPost(POST_ID) }
        }
    }

    @Test
    fun refresh_testPersistenceCleared() {
        // When
        redditRepository.refresh()

        // Then
        verify { persistence.clear() }
    }
}