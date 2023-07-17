package com.translator.uzbek.english.dictionary.di

import com.translator.uzbek.english.dictionary.data.datastore.AppStore
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class IOSModule : KoinComponent {
    val appStore: AppStore by inject()

    fun initKoin() = initKoin {}
}