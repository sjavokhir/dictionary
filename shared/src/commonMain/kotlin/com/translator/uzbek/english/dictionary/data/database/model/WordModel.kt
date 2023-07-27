package com.translator.uzbek.english.dictionary.data.database.model

import database.SearchWords
import database.Word

data class WordModel(
    val id: String,
    val createdAt: Long,
    val dictionaryId: String,
    val dictionaryTitle: String? = null,
    val word: String,
    val translation: String,
    val transcription: String? = null,
    val status: WordStatus,
) {
    enum class WordStatus {
        Learned,
        New,
        Learning,
        Skipped
    }
}

fun Word.toModel(): WordModel {
    return WordModel(
        id = id,
        createdAt = createdAt,
        dictionaryId = dictionaryId,
        word = word,
        translation = translation,
        transcription = transcription,
        status = when (status.toInt()) {
            WordModel.WordStatus.Learned.ordinal -> WordModel.WordStatus.Learned
            WordModel.WordStatus.Learning.ordinal -> WordModel.WordStatus.Learning
            WordModel.WordStatus.Skipped.ordinal -> WordModel.WordStatus.Skipped
            else -> WordModel.WordStatus.New
        },
    )
}

fun SearchWords.toModel(): WordModel {
    return WordModel(
        id = id,
        createdAt = createdAt,
        dictionaryId = dictionaryId,
        dictionaryTitle = dictionaryTitle,
        word = word,
        translation = translation,
        transcription = transcription,
        status = when (status.toInt()) {
            WordModel.WordStatus.Learned.ordinal -> WordModel.WordStatus.Learned
            WordModel.WordStatus.Learning.ordinal -> WordModel.WordStatus.Learning
            WordModel.WordStatus.Skipped.ordinal -> WordModel.WordStatus.Skipped
            else -> WordModel.WordStatus.New
        },
    )
}
