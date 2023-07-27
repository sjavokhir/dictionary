package com.translator.uzbek.english.dictionary.presentation.dictionary

import com.translator.uzbek.english.dictionary.data.database.model.DictionaryModel

data class DictionaryState(
    val isLoading: Boolean = false,
    val dictionaries: List<DictionaryModel> = emptyList(),
)
