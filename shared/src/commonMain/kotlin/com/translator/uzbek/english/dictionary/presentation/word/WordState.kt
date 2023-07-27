package com.translator.uzbek.english.dictionary.presentation.word

import com.translator.uzbek.english.dictionary.data.database.model.DictionaryModel

data class WordState(
    val dictionaryTitle: String = "",
    val dictionaryId: String = "",
    val isLoading: Boolean = false,
    val isEnabled: Boolean = false,
    val isSuccess: Boolean = false,
    val dictionaries: List<DictionaryModel> = emptyList(),
)
