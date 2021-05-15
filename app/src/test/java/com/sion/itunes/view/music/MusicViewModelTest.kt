package com.sion.itunes.view.music

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import com.sion.itunes.*
import com.sion.itunes.db.ItunesDb
import com.sion.itunes.model.api.search.FakeSearchApiRepository
import com.sion.itunes.model.api.search.FakeSearchApiService
import com.sion.itunes.model.api.search.ISearchApiRepository
import com.sion.itunes.model.api.search.db.FakeItunesDb
import com.sion.itunes.model.api.search.db.FakeMusicDao
import com.sion.itunes.model.api.search.db.FakeRemoteKeyDao
import com.sion.itunes.model.vo.SearchResponse
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalPagingApi
class MusicViewModelTest {
    private lateinit var musicViewModel: MusicViewModel

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var searchApiRepository: ISearchApiRepository

    private lateinit var itunesDb: ItunesDb

    @Before
    fun setupViewModel() {
        val fakeSearchApiService =
            FakeSearchApiService(SearchResponse(allRemoteMusics.size, allRemoteMusics))
        searchApiRepository = FakeSearchApiRepository(fakeSearchApiService)
        itunesDb = FakeItunesDb(
            FakeMusicDao(ArrayList(allLocalMusics)), FakeRemoteKeyDao(ArrayList(allLocalRemoteKey))
        )
        musicViewModel = MusicViewModel(searchApiRepository, itunesDb)
    }

    @Test
    fun search_throughDb() {
        val flow = musicViewModel.search("", true)
        assertThat(flow, not(CoreMatchers.nullValue()))
    }

    @Test
    fun search_throughApi() {
        val flow = musicViewModel.search("", false)
        assertThat(flow, not(CoreMatchers.nullValue()))
    }
}