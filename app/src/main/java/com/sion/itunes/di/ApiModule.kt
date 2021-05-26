package com.sion.itunes.di

import android.util.Log
import com.google.gson.Gson
import com.sion.itunes.BuildConfig
import com.sion.itunes.model.api.search.SearchApiRepository
import com.sion.itunes.model.api.search.SearchApiService
import com.sion.itunes.model.api.ItunesInterceptor
import com.sion.itunes.model.api.search.ISearchApiRepository
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
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

fun provideHttpClient(): HttpClient {
    val TIME_OUT = 60_000
    val ktorHttpClient = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })

            engine {
                connectTimeout = TIME_OUT
                socketTimeout = TIME_OUT
            }
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("Logger Ktor =>", message)
                }
            }
            level = LogLevel.ALL
        }

        install(ResponseObserver) {
            onResponse { response ->
                Log.d("HTTP status :", "${response.status.value}")
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }
    return ktorHttpClient
}

fun provideJsonSerializer(): JsonSerializer {
    val serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    })
    return serializer
}

//fun provideHttpClientEngineConfig():AndroidEngineConfig{
//
//}

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

