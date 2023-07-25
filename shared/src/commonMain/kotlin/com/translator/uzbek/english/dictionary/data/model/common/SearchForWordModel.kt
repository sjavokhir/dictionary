package com.translator.uzbek.english.dictionary.data.model.common

data class SearchForWordModel(
    val id: String,
    val word: String,
    val translation: String,
    val dictionary: DictionaryModel
)
