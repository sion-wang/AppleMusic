package com.sion.itunes.di

import android.content.Context
import com.sion.itunes.db.ItunesDb
import org.koin.dsl.module


val dbModule = module {
    single { provideAppDatabase(get()) }
}

fun provideAppDatabase(context: Context): ItunesDb {
    return ItunesDb.create(context, false)
}