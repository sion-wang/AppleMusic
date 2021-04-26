package com.sion.applemusic.view.users

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sion.applemusic.model.api.ApiRepository
import com.sion.applemusic.model.vo.GithubUser
import com.sion.applemusic.view.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class UsersViewModel : BaseViewModel() {
    fun getUsers(): Flow<PagingData<GithubUser>> {
        return Pager(
            config = PagingConfig(
                pageSize = ApiRepository.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UserPagingSource(apiRepository) }
        ).flow.cachedIn(viewModelScope)
    }
}