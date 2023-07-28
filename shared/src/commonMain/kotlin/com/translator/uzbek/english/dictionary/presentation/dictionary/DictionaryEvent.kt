package com.translator.uzbek.english.dictionary.presentation.dictionary

import com.translator.uzbek.english.dictionary.data.database.model.DictionaryModel

sealed class DictionaryEvent {
    data class ResetProgress(val model: DictionaryModel) : DictionaryEvent()
    data class RemoveDictionary(val model: DictionaryModel) : DictionaryEvent()
    data class ClearDictionary(val model: DictionaryModel) : DictionaryEvent()
}
