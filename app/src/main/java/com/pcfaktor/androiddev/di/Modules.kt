package com.pcfaktor.androiddev.di

import com.pcfaktor.androiddev.ui.feed.FeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { FeedViewModel() }
}