package com.translator.uzbek.english.dictionary.android.design.localization

import androidx.compose.runtime.Composable
import com.translator.uzbek.english.dictionary.data.model.mode.FirstLanguageMode
import com.translator.uzbek.english.dictionary.data.model.mode.ThemeMode

@Composable
fun FirstLanguageMode.localized(): String {
    return when (this) {
        FirstLanguageMode.Uzbek -> LocalStrings.current.uzbek
        FirstLanguageMode.English -> LocalStrings.current.english
        FirstLanguageMode.Random -> LocalStrings.current.random
    }
}

@Composable
fun ThemeMode.localized(): String {
    return when (this) {
        ThemeMode.System -> LocalStrings.current.system
        ThemeMode.Light -> LocalStrings.current.light
        ThemeMode.Dark -> LocalStrings.current.dark
    }
}