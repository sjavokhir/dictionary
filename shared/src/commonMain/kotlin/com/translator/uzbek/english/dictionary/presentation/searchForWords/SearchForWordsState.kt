package com.translator.uzbek.english.dictionary.presentation.searchForWords

import com.translator.uzbek.english.dictionary.data.database.model.WordModel

data class SearchForWordsState(
    val query: String = "",
    val isLoading: Boolean = false,
    val words: List<WordModel> = emptyList(),
)
