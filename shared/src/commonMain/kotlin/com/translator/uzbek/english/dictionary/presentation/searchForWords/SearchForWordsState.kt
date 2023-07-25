package com.translator.uzbek.english.dictionary.presentation.searchForWords

import com.translator.uzbek.english.dictionary.data.model.common.SearchForWordModel

data class SearchForWordsState(
    val query: String = "",
    val isLoading: Boolean = false,
    val words: List<SearchForWordModel> = emptyList()
)
