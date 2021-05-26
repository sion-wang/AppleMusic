package com.sion.itunes.model.api.search

import com.sion.itunes.*
import com.sion.itunes.model.vo.SearchResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchApiRepositoryTest {

    private lateinit var fakeSearchApiService: SearchApiService

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