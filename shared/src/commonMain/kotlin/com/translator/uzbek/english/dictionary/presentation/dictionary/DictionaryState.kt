package com.translator.uzbek.english.dictionary.presentation.dictionary

import com.translator.uzbek.english.dictionary.data.model.common.DictionaryModel

data class DictionaryState(
    val title: String = "",
    val dictionaryId: String = "",
    val isLoading: Boolean = false,
    val isEnabled: Boolean = false,
    val isSuccess: Boolean = false,
    val list: List<DictionaryModel> = emptyList(),
)
