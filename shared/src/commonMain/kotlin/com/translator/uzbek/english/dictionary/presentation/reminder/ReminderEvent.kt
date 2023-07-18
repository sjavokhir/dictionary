package com.translator.uzbek.english.dictionary.presentation.reminder

sealed class ReminderEvent {
    data class ChangeHourMinute(val hour: Int, val minute: Int) : ReminderEvent()

    object SelectAll : ReminderEvent()
    data class SelectWeekday(val position: Int, val selected: Boolean) : ReminderEvent()
}