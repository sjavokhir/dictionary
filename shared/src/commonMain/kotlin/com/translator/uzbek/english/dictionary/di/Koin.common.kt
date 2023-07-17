package com.translator.uzbek.english.dictionary.di

import com.translator.uzbek.english.dictionary.data.datastore.AppStore
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

expect fun platformModule(): Module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            storeModule(),
            databaseModule(),
            platformModule()
        )
    }

fun storeModule() = module {
    singleOf(::AppStore)
}

fun databaseModule() = module {
//    singleOf(::HistoryDao)
}