package com.translator.uzbek.english.dictionary.presentation.settings

import com.translator.uzbek.english.dictionary.data.model.mode.FirstLanguageMode
import com.translator.uzbek.english.dictionary.data.model.mode.LanguageMode
import com.translator.uzbek.english.dictionary.data.model.mode.ThemeMode

sealed class SettingsEvent {
    data class SetDailyGoal(val goal: Int) : SettingsEvent()
    data class SetNewWordFirstLanguage(val firstLanguage: FirstLanguageMode) : SettingsEvent()
    data class SetRepeatedFirstLanguage(val firstLanguage: FirstLanguageMode) : SettingsEvent()
    data class ShowTranscription(val show: Boolean) : SettingsEvent()

    data class SetAppLanguage(val language: LanguageMode) : SettingsEvent()
    data class SetThemeMode(val themeMode: ThemeMode) : SettingsEvent()
    data class CheckReminder(val checked: Boolean) : SettingsEvent()
    data class CheckSoundEffects(val checked: Boolean) : SettingsEvent()
    data class CheckAutoPronounce(val checked: Boolean) : SettingsEvent()
}