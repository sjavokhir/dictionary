package com.translator.uzbek.english.dictionary.data.datastore

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.set
import com.translator.uzbek.english.dictionary.core.datetime.TimeModel
import com.translator.uzbek.english.dictionary.core.helpers.Constants
import com.translator.uzbek.english.dictionary.data.util.Keys

class DictionaryStore(private val settings: ObservableSettings) {

    fun setDailyGoal(goal: Int) {
        settings[Keys.DAILY_GOAL] = goal
    }

    fun getDailyGoal(): Int {
        return settings.getInt(Keys.DAILY_GOAL, Constants.defaultDailyGoal)
    }

    fun showTranscription(): Boolean {
        return settings.getBoolean(Keys.SHOW_TRANSCRIPTION, true)
    }

    fun setShowTranscription(show: Boolean) {
        settings[Keys.SHOW_TRANSCRIPTION] = show
    }

    fun isReminderEnabled(): Boolean {
        return settings.getBoolean(Keys.REMINDER, true)
    }

    fun setReminderEnabled(isEnabled: Boolean) {
        settings[Keys.REMINDER] = isEnabled
    }

    fun getReminderTime(): TimeModel {
        val hour = settings.getInt(Keys.REMINDER_HOUR, Constants.defaultReminderHour)
        val minute = settings.getInt(Keys.REMINDER_MINUTE, Constants.defaultReminderMinute)

        return TimeModel(hour, minute)
    }

    fun setReminderTime(time: TimeModel) {
        settings[Keys.REMINDER_HOUR] = time.hour
        settings[Keys.REMINDER_MINUTE] = time.minute
    }

    private fun getReminderWeekdays(): String {
        return settings.getString(Keys.REMINDER_WEEKDAYS, "true,true,true,true,true,true,true")
    }

    fun getReminderDays(): List<Boolean> {
        return try {
            getReminderWeekdays()
                .split(",")
                .map { it.toBooleanStrictOrNull() ?: true }
        } catch (_: Throwable) {
            listOf(true, true, true, true, true, true, true)
        }
    }

    fun setReminderWeekdays(weekdays: String) {
        settings[Keys.REMINDER_WEEKDAYS] = weekdays
    }

    fun isSoundEffectsEnabled(): Boolean {
        return settings.getBoolean(Keys.SOUND_EFFECTS, true)
    }

    fun setSoundEffectsEnabled(isEnabled: Boolean) {
        settings[Keys.SOUND_EFFECTS] = isEnabled
    }

    fun isAutoPronounceEnabled(): Boolean {
        return settings.getBoolean(Keys.AUTO_PRONOUNCE, true)
    }

    fun setAutoPronounceEnabled(isEnabled: Boolean) {
        settings[Keys.AUTO_PRONOUNCE] = isEnabled
    }
}