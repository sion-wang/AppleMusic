package com.sion.itunes

import android.app.Application
import com.sion.itunes.di.apiModule
import com.sion.itunes.di.dbModule
import com.sion.itunes.widget.log.DebugLogTree
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App: Application() {
    companion object {
        const val DB_NAME = "itunes.db"
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugLogTree())
        }

        val module = listOf(
            apiModule,
            dbModule
        )

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(module)
        }
    }
}