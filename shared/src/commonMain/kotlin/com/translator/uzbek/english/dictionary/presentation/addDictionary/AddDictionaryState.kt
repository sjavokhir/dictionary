package com.translator.uzbek.english.dictionary.presentation.addDictionary

data class AddDictionaryState(
    val id: String = "",
    val title: String = "",
    val isLoading: Boolean = false,
    val isEnabled: Boolean = false,
    val isSuccess: Boolean = false,
)
