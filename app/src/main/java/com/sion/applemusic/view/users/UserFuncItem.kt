package com.sion.applemusic.view.users

import com.sion.applemusic.model.vo.GithubUser

data class UserFuncItem (
    val onUserItemClick: ((GithubUser) -> Unit) = { _ -> },
)