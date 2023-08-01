package com.translator.uzbek.english.dictionary.presentation.addWord

data class AddWordState(
    val word: String = "",
    val translation: String = "",
    val transcription: String = "",
    val repeats: Int = 0,
    val isNewWord: Boolean = true,
    val isLoading: Boolean = false,
    val isEnabled: Boolean = false,
    val isSuccess: Boolean = false,
)