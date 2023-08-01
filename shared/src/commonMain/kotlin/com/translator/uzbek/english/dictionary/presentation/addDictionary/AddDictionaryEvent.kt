package com.translator.uzbek.english.dictionary.presentation.addDictionary

sealed class AddDictionaryEvent {
    data class FetchDictionary(val id: String) : AddDictionaryEvent()
    data class ChangeTitle(val title: String) : AddDictionaryEvent()

    object Insert : AddDictionaryEvent()
}
