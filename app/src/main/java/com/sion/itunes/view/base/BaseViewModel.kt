package com.sion.itunes.view.base

import androidx.lifecycle.ViewModel
import com.sion.itunes.db.ItunesDb
import com.sion.itunes.model.api.search.SearchApiRepository
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseViewModel : ViewModel()
