package com.translator.uzbek.english.dictionary.data.database.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.translator.uzbek.english.dictionary.core.datetime.currentTimestamp
import com.translator.uzbek.english.dictionary.data.database.model.WordModel
import com.translator.uzbek.english.dictionary.data.database.model.toModel
import com.translator.uzbek.english.dictionary.db.AppDatabase
import com.translator.uzbek.english.dictionary.shared.ioDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class WordDao(database: AppDatabase) {

    private val queries = database.appDatabaseQueries

    fun fetchWords(dictionaryId: String): Flow<List<WordModel>> {
        return queries.fetchWords(dictionaryId)
            .asFlow()
            .mapToList(ioDispatcher)
            .map { word ->
                word.map { it.toModel() }.sortedByDescending { it.createdAt }
            }
            .flowOn(ioDispatcher)
    }

    fun searchWords(query: String): Flow<List<WordModel>> {
        return queries.searchWords(query)
            .asFlow()
            .mapToList(ioDispatcher)
            .map { word ->
                word.map { it.toModel() }
            }
            .flowOn(ioDispatcher)
    }

    fun fetchWordById(wordId: String): Flow<WordModel?> {
        return queries.fetchWordById(wordId)
            .asFlow()
            .mapToOneOrNull(ioDispatcher)
            .map { it?.toModel() }
            .flowOn(ioDispatcher)
    }

    fun countStatusWords(status: WordModel.WordStatus): Flow<Int> {
        return queries.countStatusWords(status.ordinal.toLong())
            .asFlow()
            .mapToOneOrNull(ioDispatcher)
            .map { (it ?: 0L).toInt() }
            .flowOn(ioDispatcher)
    }

    fun insert(
        id: String,
        dictionaryId: String,
        word: String,
        translation: String,
        transcription: String? = null,
        repeats: Int,
        status: WordModel.WordStatus,
    ) {
        queries.insertWord(
            id = id,
            createdAt = currentTimestamp(),
            dictionaryId = dictionaryId,
            word = word.trim(),
            translation = translation.trim(),
            transcription = transcription?.trim(),
            repeats = repeats.toLong(),
            status = status.ordinal.toLong()
        )
    }

    fun delete(id: String) {
        queries.deleteWord(id)
    }

    fun clearAll(dictionaryId: String) {
        queries.clearWords(dictionaryId)
    }

    fun updateWordStatus(wordId: String, status: WordModel.WordStatus) {
        queries.updateWordStatus(
            status = status.ordinal.toLong(),
            id = wordId
        )
    }

    fun resetProgress(dictionaryId: String) {
        queries.resetProgress(dictionaryId)
    }

    fun resetAllProgress() {
        queries.resetAllProgress()
    }
}