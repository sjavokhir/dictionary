package com.translator.uzbek.english.dictionary.android.design.localization

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

val LocalStrings = staticCompositionLocalOf { stringResourcesEnglish() }

@Immutable
data class StringResources(
    val appName: String = "Uzbek-English dictionary",
    val learn: String,
    val dictionary: String,
    val statistics: String,
    val settings: String
)