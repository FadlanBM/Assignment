package com.example.loremperpus.core.di

import com.example.loremperpus.core.data.repository.AppRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AppRepository(get(),get()) }
}