package com.sion.applemusic.model.vo

data class GithubUser(
    val id: Int = -1,
    val login: String = "",
    val name: String = "",
    val avatar_url: String = "",
    val site_admin: Boolean = false,
    val bio: String = "",
    val location: String = "",
    val blog: String = ""
)
