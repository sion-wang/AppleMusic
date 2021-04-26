package com.sion.applemusic.model.api

import okhttp3.Interceptor
import okhttp3.Response

class GithubInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest =
            chain.request().newBuilder().addHeader("Accept", "application/vnd.github.v3+json")
                .build()
        return chain.proceed(newRequest)
    }
}