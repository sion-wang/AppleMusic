package com.sion.itunes.di

import com.google.gson.Gson
import com.sion.itunes.BuildConfig
import com.sion.itunes.model.api.search.SearchApiRepository
import com.sion.itunes.model.api.search.SearchApiService
import com.sion.itunes.model.api.ItunesInterceptor
import com.sion.itunes.model.api.search.ISearchApiRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val apiModule = module {
    single { provideItunesInterceptor() }
    single { provideHttpLoggingInterceptor() }
    single { provideOkHttpClient(get(), get()) }
    single { provideApiService(get()) }
    single { provideApiRepository(get()) }
}

fun provideItunesInterceptor(): ItunesInterceptor {
    return ItunesInterceptor()
}

fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }
}

fun provideOkHttpClient(
    itunesInterceptor: ItunesInterceptor,
    httpLoggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {
    val builder = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(itunesInterceptor)
        .addInterceptor(httpLoggingInterceptor)
    return builder.build()
}

fun provideApiService(okHttpClient: OkHttpClient): SearchApiService {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .client(okHttpClient)
        .baseUrl(BuildConfig.ITUNES_API_HOST)
        .build()
        .create(SearchApiService::class.java)
}

fun provideApiRepository(searchApiService: SearchApiService): ISearchApiRepository {
    return SearchApiRepository(searchApiService)
}