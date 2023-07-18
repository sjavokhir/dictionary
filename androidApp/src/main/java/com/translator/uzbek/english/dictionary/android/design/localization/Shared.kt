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

@Composable
fun Int.weekShortName(): String {
    return when (this) {
        0 -> LocalStrings.current.mondayShort
        1 -> LocalStrings.current.tuesdayShort
        2 -> LocalStrings.current.wednesdayShort
        3 -> LocalStrings.current.thursdayShort
        4 -> LocalStrings.current.fridayShort
        5 -> LocalStrings.current.saturdayShort
        6 -> LocalStrings.current.sundayShort
        else -> ""
    }
}

fun List<Boolean>.weekdays(strings: StringResources): String {
    val days = listOf(0, 1, 2, 3, 4, 5, 6)
        .map {
            when (it) {
                0 -> strings.mondayShort
                1 -> strings.tuesdayShort
                2 -> strings.wednesdayShort
                3 -> strings.thursdayShort
                4 -> strings.fridayShort
                5 -> strings.saturdayShort
                6 -> strings.sundayShort
                else -> ""
            }
        }

    return if (all { it }) {
        strings.everyday
    } else {
        days.zip(this)
            .filter { it.second }
            .joinToString(", ") { it.first }
    }
}