package com.translator.uzbek.english.dictionary.presentation.settings

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.translator.uzbek.english.dictionary.data.datastore.AppStore
import com.translator.uzbek.english.dictionary.data.datastore.DictionaryStore
import com.translator.uzbek.english.dictionary.data.model.mode.FirstLanguageMode
import com.translator.uzbek.english.dictionary.data.model.mode.LanguageMode
import com.translator.uzbek.english.dictionary.data.model.mode.ThemeMode
import com.translator.uzbek.english.dictionary.shared.Event
import com.translator.uzbek.english.dictionary.shared.EventChannel
import com.translator.uzbek.english.dictionary.shared.ioDispatcher
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingsViewModel : KMMViewModel(), KoinComponent {

    private val appStore by inject<AppStore>()
    private val dictionaryStore by inject<DictionaryStore>()

    private val stateData = MutableStateFlow(viewModelScope, SettingsState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    init {
        fetchStoreData()
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.SetNewWordFirstLanguage -> setNewWordFirstLanguage(event.firstLanguage)
            is SettingsEvent.SetRepeatedFirstLanguage -> setRepeatedFirstLanguage(event.firstLanguage)
            is SettingsEvent.ShowTranscription -> showTranscription(event.show)
            is SettingsEvent.SetAppLanguage -> setAppLanguage(event.language)
            is SettingsEvent.SetThemeMode -> setThemeMode(event.themeMode)
            is SettingsEvent.CheckSoundEffects -> onCheckedSoundEffects(event.checked)
            is SettingsEvent.CheckAutoPronounce -> onCheckedAutoPronounce(event.checked)
        }
    }

    private fun fetchStoreData() {
        viewModelScope.coroutineScope.launch(ioDispatcher) {
            stateData.update {
                it.copy(
                    newWordFirstLanguage = dictionaryStore.getNewWordFirstLanguage(),
                    repeatedFirstLanguage = dictionaryStore.getRepeatedFirstLanguage(),
                    showTranscription = dictionaryStore.showTranscription(),
                    appLanguage = appStore.getAppLanguage(),
                    themeMode = appStore.getThemeMode(),
                    isSoundEffects = dictionaryStore.isSoundEffectsEnabled(),
                    isAutoPronounce = dictionaryStore.isAutoPronounceEnabled()
                )
            }
        }
    }

    private fun setNewWordFirstLanguage(firstLanguage: FirstLanguageMode) {
        dictionaryStore.setNewWordFirstLanguage(firstLanguage)

        stateData.update { it.copy(newWordFirstLanguage = firstLanguage) }
    }

    private fun setRepeatedFirstLanguage(firstLanguage: FirstLanguageMode) {
        dictionaryStore.setRepeatedFirstLanguage(firstLanguage)

        stateData.update { it.copy(repeatedFirstLanguage = firstLanguage) }
    }

    private fun showTranscription(show: Boolean) {
        dictionaryStore.setShowTranscription(show)

        stateData.update { it.copy(showTranscription = show) }
    }

    private fun onCheckedSoundEffects(checked: Boolean) {
        dictionaryStore.setSoundEffectsEnabled(checked)

        stateData.update { it.copy(isSoundEffects = checked) }
    }

    private fun onCheckedAutoPronounce(checked: Boolean) {
        dictionaryStore.setAutoPronounceEnabled(checked)

        stateData.update { it.copy(isAutoPronounce = checked) }
    }

    private fun setAppLanguage(language: LanguageMode) {
        appStore.setAppLanguage(language)

        stateData.update { it.copy(appLanguage = language) }

        EventChannel.sendEvent(Event.LanguageChanged(language))
    }

    private fun setThemeMode(themeMode: ThemeMode) {
        appStore.setThemeMode(themeMode)

        stateData.update { it.copy(themeMode = themeMode) }

        EventChannel.sendEvent(Event.ThemeModeChanged(themeMode))
    }
}