package com.translator.uzbek.english.dictionary.data.datastore

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.set
import com.translator.uzbek.english.dictionary.core.helpers.Constants
import com.translator.uzbek.english.dictionary.data.model.mode.FirstLanguageMode
import com.translator.uzbek.english.dictionary.data.util.Keys

class DictionaryStore(private val settings: ObservableSettings) {

    fun setDailyGoal(goal: Int) {
        settings[Keys.DAILY_GOAL] = goal
    }

    fun getDailyGoal(): Int {
        return settings.getInt(Keys.DAILY_GOAL, Constants.defaultDailyGoal)
    }

    fun getNewWordFirstLanguage(): FirstLanguageMode {
        return try {
            val language = settings.getString(
                Keys.NEW_WORD_LANGUAGE,
                FirstLanguageMode.Uzbek.name
            )
            FirstLanguageMode.valueOf(language)
        } catch (_: Throwable) {
            FirstLanguageMode.Uzbek
        }
    }

    fun setNewWordFirstLanguage(firstLanguage: FirstLanguageMode) {
        settings[Keys.NEW_WORD_LANGUAGE] = firstLanguage.name
    }

    fun getRepeatedFirstLanguage(): FirstLanguageMode {
        return try {
            val language = settings.getString(
                Keys.REPEATED_LANGUAGE,
                FirstLanguageMode.Uzbek.name
            )
            FirstLanguageMode.valueOf(language)
        } catch (_: Throwable) {
            FirstLanguageMode.Uzbek
        }
    }

    fun setRepeatedFirstLanguage(firstLanguage: FirstLanguageMode) {
        settings[Keys.REPEATED_LANGUAGE] = firstLanguage.name
    }

    fun showTranscription(): Boolean {
        return settings.getBoolean(Keys.SHOW_TRANSCRIPTION, true)
    }

    fun setShowTranscription(show: Boolean) {
        settings[Keys.SHOW_TRANSCRIPTION] = show
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