package com.sion.itunes.view.users

import com.sion.itunes.model.vo.GithubUser

data class UserFuncItem (
    val onUserItemClick: ((GithubUser) -> Unit) = { _ -> },
)