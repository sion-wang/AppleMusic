package com.sion.itunes.model.api.search

import com.sion.itunes.model.vo.Music
import com.sion.itunes.model.vo.SearchResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ApiSearchRepositoryTest {
    private val music0 = Music(trackId = 0, artistName = "Sion")
    private val music1 = Music(trackId = 1, artistName = "Andy")
    private val music2 = Music(trackId = 2, artistName = "Sion")
    private val music3 = Music(trackId = 3, artistName = "Andy")
    private val music4 = Music(trackId = 4, artistName = "Android")
    private val music5 = Music(trackId = 5, artistName = "Sion")
    private val music6 = Music(trackId = 6, artistName = "Andy")
    private val music7 = Music(trackId = 7, artistName = "Sion")
    private val music8 = Music(trackId = 8, artistName = "Sion")
    private val music9 = Music(trackId = 9, artistName = "Android")
    private val allRemoteMusics = listOf(music0, music1, music2, music3, music4, music5, music6, music7, music8, music9).sortedBy { it.trackId }

    private lateinit var fakeSearchApiService: FakeSearchApiService

    private lateinit var searchApiRepository: SearchApiRepository

    @Before
    fun createRepository() {
        fakeSearchApiService = FakeSearchApiService(SearchResponse(allRemoteMusics.size, allRemoteMusics))
        searchApiRepository = SearchApiRepository(fakeSearchApiService)
    }

    @Test
    fun search_requests1to5() = runBlockingTest {
        val response = searchApiRepository.search("", 1, 5)
        val dataCount = response.body()?.resultCount?: 0
        val data = response.body()?.results?: arrayListOf()

        val expectList = listOf(music1, music2, music3, music4, music5)

        assertThat(dataCount, IsEqual(expectList.size))
        assertThat(data, IsEqual(expectList))
    }

    @Test
    fun search_requests0to4() = runBlockingTest {
        val response = searchApiRepository.search("", 0, 5)
        val dataCount = response.body()?.resultCount?: 0
        val data = response.body()?.results?: arrayListOf()

        val expectList = listOf(music0, music1, music2, music3, music4)

        assertThat(dataCount, IsEqual(expectList.size))
        assertThat(data, IsEqual(expectList))
    }

    @Test
    fun search_requestsToEnd() = runBlockingTest {
        val response = searchApiRepository.search("", 8, 5)
        val dataCount = response.body()?.resultCount?: 0
        val data = response.body()?.results?: arrayListOf()

        val expectList = listOf(music8, music9)

        assertThat(dataCount, IsEqual(expectList.size))
        assertThat(data, IsEqual(expectList))
    }

    @Test
    fun search_requestsKeyword() = runBlockingTest {
        val response = searchApiRepository.search("io", 1, 3)
        val dataCount = response.body()?.resultCount?: 0
        val data = response.body()?.results?: arrayListOf()

        val expectList = listOf(music2, music5, music7)

        assertThat(dataCount, IsEqual(expectList.size))
        assertThat(data, IsEqual(expectList))
    }
}