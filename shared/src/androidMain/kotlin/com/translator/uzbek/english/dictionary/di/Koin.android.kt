package com.translator.uzbek.english.dictionary.di

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.translator.uzbek.english.dictionary.data.util.Keys
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings
import com.translator.uzbek.english.dictionary.db.AppDatabase
import org.koin.dsl.module

actual fun platformModule() = module {
    single { createSettings(get()) }
    single { AppDatabase(AndroidSqliteDriver(AppDatabase.Schema, get(), Keys.APP_DATABASE)) }
}

fun createSettings(context: Context): ObservableSettings {
    val delegate = context.getSharedPreferences(Keys.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    return SharedPreferencesSettings(delegate = delegate)
}
