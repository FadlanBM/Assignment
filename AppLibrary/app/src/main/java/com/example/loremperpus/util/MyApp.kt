package com.example.loremperpus.util

import android.app.Application
import com.chibatching.kotpref.Kotpref
import com.example.loremperpus.core.di.repositoryModule
import com.example.loremperpus.core.di.viewModelModule
import com.example.loremperpus.core.id.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this)
        startKoin() {
            androidContext(this@MyApp)
            modules(listOf(appModule, viewModelModule, repositoryModule))
        }
    }
}