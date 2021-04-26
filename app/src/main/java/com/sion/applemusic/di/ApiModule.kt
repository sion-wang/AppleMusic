package com.sion.applemusic.di

import com.google.gson.Gson
import com.sion.applemusic.BuildConfig
import com.sion.applemusic.model.api.ApiRepository
import com.sion.applemusic.model.api.ApiService
import com.sion.applemusic.model.api.GithubInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val apiModule = module {
    single { provideGithubInterceptor() }
    single { provideHttpLoggingInterceptor() }
    single { provideOkHttpClient(get(), get()) }
    single { provideApiService(get()) }
    single { provideApiRepository(get()) }
}

fun provideGithubInterceptor(): GithubInterceptor {
    return GithubInterceptor()
}

fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }
}

fun provideOkHttpClient(
    githubInterceptor: GithubInterceptor,
    httpLoggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {
    val builder = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(githubInterceptor)
        .addInterceptor(httpLoggingInterceptor)
    return builder.build()
}

fun provideApiService(okHttpClient: OkHttpClient): ApiService {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .client(okHttpClient)
        .baseUrl(BuildConfig.APPLE_MUSIC_API_HOST)
        .build()
        .create(ApiService::class.java)
}

fun provideApiRepository(apiService: ApiService): ApiRepository {
    return ApiRepository(apiService)
}