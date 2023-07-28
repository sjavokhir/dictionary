package com.translator.uzbek.english.dictionary.presentation.word

sealed class WordEvent {
    object Insert : WordEvent()
    object Delete : WordEvent()

    data class SetWordDetails(
        val dictionaryTitle: String,
        val dictionaryId: String,
    ) : WordEvent()

    data class ChangeDictionaryTitle(val dictionaryTitle: String) : WordEvent()
}
