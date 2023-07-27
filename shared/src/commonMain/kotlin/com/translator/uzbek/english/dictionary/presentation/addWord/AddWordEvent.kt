package com.translator.uzbek.english.dictionary.presentation.addWord

sealed class AddWordEvent {
    data class FetchWord(
        val wordId: String,
        val dictionaryId: String,
    ) : AddWordEvent()

    data class ChangeWord(val word: String) : AddWordEvent()
    data class ChangeTranslation(val translation: String) : AddWordEvent()
    data class ChangeTranscription(val transcription: String) : AddWordEvent()

    object Insert : AddWordEvent()
}
