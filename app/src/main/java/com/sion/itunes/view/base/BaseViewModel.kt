package com.sion.itunes.view.base

import androidx.lifecycle.ViewModel
import com.sion.itunes.db.ItunesDb
import com.sion.itunes.model.api.ApiRepository
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
abstract class BaseViewModel: ViewModel(), KoinComponent {
    val apiRepository: ApiRepository by inject()
    val itunesDb: ItunesDb by inject()
}