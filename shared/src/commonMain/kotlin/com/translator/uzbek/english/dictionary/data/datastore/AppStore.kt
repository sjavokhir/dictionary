package com.translator.uzbek.english.dictionary.data.datastore

import com.translator.uzbek.english.dictionary.data.model.mode.ThemeMode
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.set
import com.translator.uzbek.english.dictionary.data.model.mode.LanguageMode
import com.translator.uzbek.english.dictionary.data.util.Keys

class AppStore(private val settings: ObservableSettings) {

    fun getSelectedThemeMode(): ThemeMode {
        return try {
            val themeMode = settings.getString(
                Keys.SELECTED_THEME_MODE,
                ThemeMode.System.name
            )
            ThemeMode.valueOf(themeMode)
        } catch (_: Throwable) {
            ThemeMode.System
        }
    }

    fun setSelectedThemeMode(themeMode: ThemeMode) {
        settings[Keys.SELECTED_THEME_MODE] = themeMode.name
    }

    fun getSelectedLanguage(): LanguageMode {
        return try {
            val language = settings.getString(
                Keys.SELECTED_LANGUAGE,
                LanguageMode.Uzbek.name
            )
            LanguageMode.valueOf(language)
        } catch (_: Throwable) {
            LanguageMode.Uzbek
        }
    }

    fun setSelectedLanguage(language: LanguageMode) {
        settings[Keys.SELECTED_LANGUAGE] = language.name
    }
}