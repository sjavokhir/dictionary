package com.translator.uzbek.english.dictionary.data.database.model

import database.Dictionary
import database.FetchDictionaries

data class DictionaryModel(
    val id: String,
    val createdAt: Long,
    val title: String,
    val isDefault: Boolean,
    val wordsCount: Int,
    val percentage: Int,
)

fun FetchDictionaries.toModel(): DictionaryModel {
    return DictionaryModel(
        id = id,
        createdAt = createdAt,
        title = title,
        isDefault = isDefault == 0L,
        wordsCount = wordsCount.toInt(),
        percentage = if (wordsCount != 0L) {
            ((learnedWordsCount ?: 0) * 100 / wordsCount).toInt()
        } else 0,
    )
}

fun Dictionary.toModel(): DictionaryModel {
    return DictionaryModel(
        id = id,
        createdAt = createdAt,
        title = title,
        isDefault = isDefault == 0L,
        wordsCount = 0,
        percentage = 0,
    )
}
