package com.translator.uzbek.english.dictionary.presentation.statistics

import com.translator.uzbek.english.dictionary.core.helpers.Constants

data class StatisticsState(
    val dailyGoal: Int = Constants.defaultDailyGoal,
    val today: Int = 0,
    val currentStreak: Int = 0,
    val allLearning: Int = 0,
    val allLearned: Int = 0,
    val allBestStreak: Int = 0,
    val startOfLearning: String = ""
) {
    enum class Time {
        Week,
        Month,
        Quarter,
        Year
    }
}
