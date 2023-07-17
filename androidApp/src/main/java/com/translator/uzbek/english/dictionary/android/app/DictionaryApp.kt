package com.translator.uzbek.english.dictionary.android.app

import android.app.Application
import com.translator.uzbek.english.dictionary.di.initKoin
import org.koin.android.ext.koin.androidContext

class DictionaryApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@DictionaryApp)
        }
    }
}