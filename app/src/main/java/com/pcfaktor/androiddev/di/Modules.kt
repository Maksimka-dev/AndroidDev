package com.pcfaktor.androiddev.di

import com.pcfaktor.androiddev.data.Repository
import com.pcfaktor.androiddev.data.network.ApiService
import com.pcfaktor.androiddev.data.network.dto.DtoMapper
import com.pcfaktor.androiddev.ui.feed.FeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { FeedViewModel(get()) }
}

val networkModule = module {
    single { DtoMapper() }
    single { ApiService.retrofitService }
}

val repositoryModule = module {
    single { Repository(get(), get()) }
}