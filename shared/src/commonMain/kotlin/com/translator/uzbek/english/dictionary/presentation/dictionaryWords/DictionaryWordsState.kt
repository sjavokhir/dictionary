package com.translator.uzbek.english.dictionary.presentation.dictionaryWords

import com.translator.uzbek.english.dictionary.data.database.model.WordModel

data class DictionaryWordsState(
    val isLoading: Boolean = false,
    val dictionaryId: String = "",
    val words: List<WordModel> = emptyList(),
)
