package com.translator.uzbek.english.dictionary.presentation.addDictionary

sealed class AddDictionaryEvent {
    data class SetDictionaryDetails(
        val id: String,
        val title: String,
    ) : AddDictionaryEvent()

    data class ChangeTitle(val title: String) : AddDictionaryEvent()

    object Insert : AddDictionaryEvent()
}
