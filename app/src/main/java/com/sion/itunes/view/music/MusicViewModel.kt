package com.sion.itunes.view.music

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sion.itunes.model.api.ApiRepository
import com.sion.itunes.model.vo.Music
import com.sion.itunes.view.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class MusicViewModel: BaseViewModel() {
    fun search(keyword: String): Flow<PagingData<Music>> {
        return Pager(
            config = PagingConfig(
                pageSize = ApiRepository.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MusicPagingSource(apiRepository, keyword) }
        ).flow.cachedIn(viewModelScope)
    }
}