package com.example.loremperpus.core.id

import com.example.loremperpus.core.data.source.local.LocalDataSource
import com.example.loremperpus.core.data.source.remote.RemoteDataSouce
import com.example.loremperpus.core.data.source.remote.network.ApiConfig
import org.koin.dsl.module

val appModule= module {
    single { ApiConfig.provideApiService }
    single { RemoteDataSouce(get()) }
    single { LocalDataSource() }
}