package com.translator.uzbek.english.dictionary.presentation.dictionarySelection

import com.translator.uzbek.english.dictionary.data.database.model.DictionaryModel

data class DictionarySelectionState(
    val isLoading: Boolean = false,
    val dictionaries: List<DictionaryModel> = emptyList(),
)
