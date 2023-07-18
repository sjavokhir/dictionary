package com.translator.uzbek.english.dictionary.data.datastore

import com.translator.uzbek.english.dictionary.data.model.mode.ThemeMode
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.set
import com.translator.uzbek.english.dictionary.data.model.mode.LanguageMode
import com.translator.uzbek.english.dictionary.data.util.Keys

class AppStore(private val settings: ObservableSettings) {

    fun getThemeMode(): ThemeMode {
        return try {
            val themeMode = settings.getString(
                Keys.THEME_MODE,
                ThemeMode.System.name
            )
            ThemeMode.valueOf(themeMode)
        } catch (_: Throwable) {
            ThemeMode.System
        }
    }

    fun setThemeMode(themeMode: ThemeMode) {
        settings[Keys.THEME_MODE] = themeMode.name
    }

    fun getAppLanguage(): LanguageMode {
        return try {
            val language = settings.getString(
                Keys.APP_LANGUAGE,
                LanguageMode.Uzbek.name
            )
            LanguageMode.valueOf(language)
        } catch (_: Throwable) {
            LanguageMode.Uzbek
        }
    }

    fun setAppLanguage(language: LanguageMode) {
        settings[Keys.APP_LANGUAGE] = language.name
    }
}