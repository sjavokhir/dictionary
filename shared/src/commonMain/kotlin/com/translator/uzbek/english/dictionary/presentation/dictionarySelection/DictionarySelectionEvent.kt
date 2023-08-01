package com.translator.uzbek.english.dictionary.presentation.dictionarySelection

import com.translator.uzbek.english.dictionary.data.database.model.DictionaryModel

sealed class DictionarySelectionEvent {
    data class SelectDictionary(val model: DictionaryModel) : DictionarySelectionEvent()
}
