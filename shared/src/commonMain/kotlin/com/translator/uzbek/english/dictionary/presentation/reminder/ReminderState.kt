package com.translator.uzbek.english.dictionary.presentation.reminder

import com.translator.uzbek.english.dictionary.core.helpers.Constants

data class ReminderState(
    val hour: Int = Constants.defaultReminderHour,
    val minute: Int = Constants.defaultReminderMinute
)
