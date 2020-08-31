package com.pcfaktor.androiddev

import android.app.Application
import com.pcfaktor.androiddev.di.networkModule
import com.pcfaktor.androiddev.di.repositoryModule
import com.pcfaktor.androiddev.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    viewModelModule,
                    networkModule,
                    repositoryModule
                )
            )
        }
        Timber.plant(Timber.DebugTree())
    }
}