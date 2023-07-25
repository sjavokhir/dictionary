package com.translator.uzbek.english.dictionary.presentation.dictionary

sealed class DictionaryEvent {
    object FetchDictionaries : DictionaryEvent()
    object Insert : DictionaryEvent()
    object Delete : DictionaryEvent()

    data class SetArgs(val title: String, val id: String) : DictionaryEvent()
    data class ChangeTitle(val title: String) : DictionaryEvent()
}
