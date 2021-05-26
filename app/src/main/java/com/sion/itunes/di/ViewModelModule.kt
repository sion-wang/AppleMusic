package com.sion.itunes.di

import androidx.paging.ExperimentalPagingApi
import com.sion.itunes.view.music.MusicViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalPagingApi
val viewModelModule = module {
    viewModel { MusicViewModel(get(), get()) }
}