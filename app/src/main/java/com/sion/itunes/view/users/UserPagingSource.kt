package com.sion.itunes.view.users

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sion.itunes.model.api.ApiRepository
import com.sion.itunes.model.vo.GithubUser
import retrofit2.HttpException
import timber.log.Timber

class UserPagingSource(private val apiRepository: ApiRepository) : PagingSource<Int, GithubUser>() {
    override fun getRefreshKey(state: PagingState<Int, GithubUser>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubUser> {
        return try {
            val since = params.key ?: 0L
            val result = apiRepository.getUsers(since.toInt())
            if (!result.isSuccessful) throw HttpException(result)
            val users = result.body() ?: arrayListOf()

            LoadResult.Page(
                data = users,
                prevKey = null,
                nextKey = users.last().id
            )
        } catch (e: Exception) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }

}