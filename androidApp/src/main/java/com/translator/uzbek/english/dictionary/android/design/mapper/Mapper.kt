package com.translator.uzbek.english.dictionary.android.design.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.translator.uzbek.english.dictionary.android.design.localization.LocalStrings
import com.translator.uzbek.english.dictionary.android.design.localization.StringResources
import com.translator.uzbek.english.dictionary.android.design.theme.ChartColor1
import com.translator.uzbek.english.dictionary.android.design.theme.ChartColor2
import com.translator.uzbek.english.dictionary.android.design.theme.ChartColor3
import com.translator.uzbek.english.dictionary.android.design.theme.ChartColor4
import com.translator.uzbek.english.dictionary.data.database.model.WordModel
import com.translator.uzbek.english.dictionary.data.model.mode.ThemeMode
import com.translator.uzbek.english.dictionary.presentation.learn.LearnState

@Composable
fun WordModel.WordStatus.color(): Color {
    return when (this) {
        WordModel.WordStatus.Learned -> ChartColor4
        WordModel.WordStatus.New -> ChartColor3
        WordModel.WordStatus.Learning -> ChartColor2
        WordModel.WordStatus.Skipped -> ChartColor1
    }
}

@Composable
fun WordModel.WordStatus.localized(repeats: Int): String {
    return when (this) {
        WordModel.WordStatus.Learned -> LocalStrings.current.learned
        WordModel.WordStatus.New -> LocalStrings.current.new
        WordModel.WordStatus.Learning -> LocalStrings.current.learningRepeats(repeats)
        WordModel.WordStatus.Skipped -> LocalStrings.current.skipped
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

@Composable
fun LearnState.Title.localized(): String {
    return when (this) {
        LearnState.Title.Empty -> ""
        LearnState.Title.Morning -> LocalStrings.current.goodMorning
        LearnState.Title.Afternoon -> LocalStrings.current.goodAfternoon
        LearnState.Title.Evening -> LocalStrings.current.goodEvening
        LearnState.Title.Night -> LocalStrings.current.goodNight
    }
}