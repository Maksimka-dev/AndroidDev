package com.pcfaktor.androiddev.di

import com.pcfaktor.androiddev.data.Repository
import com.pcfaktor.androiddev.data.base.ArticlesRoomDatabase
import com.pcfaktor.androiddev.data.network.ApiService
import com.pcfaktor.androiddev.data.network.dto.DtoMapper
import com.pcfaktor.androiddev.ui.bookmarks.BookmarksViewModel
import com.pcfaktor.androiddev.ui.feed.FeedViewModel
import com.pcfaktor.androiddev.ui.full_article.FullArticleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { FeedViewModel(get()) }
    viewModel { FullArticleViewModel() }
    viewModel { BookmarksViewModel(get()) }
}

val networkModule = module {
    single { DtoMapper() }
    single { ApiService.retrofitService }
}

val repositoryModule = module {
    single { Repository(get(), get(), get()) }
    single { ArticlesRoomDatabase.getDatabase(context = get()) }
    single { get<ArticlesRoomDatabase>().articleDao() }
}