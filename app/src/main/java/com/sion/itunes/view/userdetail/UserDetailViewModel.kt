package com.sion.itunes.view.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sion.itunes.model.api.ApiResult
import com.sion.itunes.model.vo.GithubUser
import com.sion.itunes.view.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class UserDetailViewModel : BaseViewModel() {

    private val _getUser = MutableLiveData<ApiResult<GithubUser>>()
    val getUser: LiveData<ApiResult<GithubUser>> = _getUser

    fun getUser(name: String) {
        viewModelScope.launch {
            apiRepository.getUserByName(name)
                .onStart { _getUser.value = ApiResult.loading() }
                .onCompletion { _getUser.value = ApiResult.loaded() }
                .catch { _getUser.value = ApiResult.error(it) }
                .collect {
                    _getUser.value = ApiResult.success(it.body())
                }
        }
    }
}