package com.sion.itunes.di

//import android.util.Log
//import com.google.gson.Gson
//import com.sion.itunes.BuildConfig
//import com.sion.itunes.model.api.search.SearchApiRepository
//import com.sion.itunes.model.api.search.SearchApiService
//import com.sion.itunes.model.api.ItunesInterceptor
//import com.sion.itunes.model.api.search.ISearchApiRepository
//import io.ktor.client.*
//import io.ktor.client.engine.android.*
//import io.ktor.client.features.*
//import io.ktor.client.features.json.*
//import io.ktor.client.features.json.serializer.*
//import io.ktor.client.features.logging.*
//import io.ktor.client.features.observer.*
//import io.ktor.client.request.*
//import io.ktor.http.*
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
import android.util.Log
import com.sion.itunes.model.api.search.ISearchApiRepository
import com.sion.itunes.model.api.search.SearchApiRepository
import org.koin.dsl.module
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.util.concurrent.TimeUnit

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json

val apiModule = module {
//    single { provideItunesInterceptor() }
//    single { provideHttpLoggingInterceptor() }
//    single { provideOkHttpClient(get(), get()) }
//    single { provideApiService(get()) }
    single { provideApiRepository(get()) }
        single { okHttpKtor }
}

//fun provideItunesInterceptor(): ItunesInterceptor {
//    return ItunesInterceptor()
//}

//fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
//    return HttpLoggingInterceptor().also {
//        it.level = HttpLoggingInterceptor.Level.BODY
//    }
//}
//
//fun provideOkHttpClient(
//    itunesInterceptor: ItunesInterceptor,
//    httpLoggingInterceptor: HttpLoggingInterceptor
//): OkHttpClient {
//    val builder = OkHttpClient.Builder()
//        .connectTimeout(30, TimeUnit.SECONDS)
//        .readTimeout(60, TimeUnit.SECONDS)
//        .writeTimeout(60, TimeUnit.SECONDS)
//        .addInterceptor(itunesInterceptor)
//        .addInterceptor(httpLoggingInterceptor)
//    return builder.build()
//}
//
//
//
//fun provideApiService(okHttpClient: OkHttpClient): SearchApiService {
//    return Retrofit.Builder()
//        .addConverterFactory(GsonConverterFactory.create(Gson()))
//        .client(okHttpClient)
//        .baseUrl(BuildConfig.ITUNES_API_HOST)
//        .build()
//        .create(SearchApiService::class.java)
//}

fun provideApiRepository(client: HttpClient): ISearchApiRepository {
    return SearchApiRepository(client)
}

private val okHttpKtor = HttpClient(CIO) {
    install(JsonFeature) {
//        serializer = KotlinxSerializer(Json.nonstrict)
        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true//FIXME not work ?!!
        })
    }
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                //FIXME here can use logger
                Log.v("Logger Ktor =>", message)
            }
        }
        level = LogLevel.ALL
    }
    install(ResponseObserver) {
        onResponse { response ->
            Log.e("Logger_HTTP status :", "${response.status.value}")
        }
    }

    install(DefaultRequest) {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }
}

