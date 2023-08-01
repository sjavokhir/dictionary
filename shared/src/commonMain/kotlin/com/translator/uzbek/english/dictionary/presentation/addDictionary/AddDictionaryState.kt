package com.translator.uzbek.english.dictionary.presentation.addDictionary

data class AddDictionaryState(
    val title: String = "",
    val isNewDictionary: Boolean = true,
    val isLoading: Boolean = false,
    val isEnabled: Boolean = false,
    val isSuccess: Boolean = false,
)
