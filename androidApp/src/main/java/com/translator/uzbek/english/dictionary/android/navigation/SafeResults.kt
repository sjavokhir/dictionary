package com.translator.uzbek.english.dictionary.android.navigation

import java.io.Serializable

data class ReminderResult(
    val hour: Int,
    val minute: Int,
    val weekdays: String
) : Serializable