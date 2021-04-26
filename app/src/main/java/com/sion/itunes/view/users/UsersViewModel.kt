package com.sion.itunes.view.users

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sion.itunes.model.api.ApiRepository
import com.sion.itunes.model.vo.GithubUser
import com.sion.itunes.view.base.BaseViewModel
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