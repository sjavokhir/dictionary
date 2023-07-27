package com.translator.uzbek.english.dictionary.data.database.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.translator.uzbek.english.dictionary.core.datetime.currentTimestamp
import com.translator.uzbek.english.dictionary.data.database.model.DictionaryModel
import com.translator.uzbek.english.dictionary.data.database.model.toModel
import com.translator.uzbek.english.dictionary.db.AppDatabase
import com.translator.uzbek.english.dictionary.shared.ioDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class DictionaryDao(database: AppDatabase) {

    private val queries = database.appDatabaseQueries

    fun fetchDictionaries(): Flow<List<DictionaryModel>> {
        return queries.fetchDictionaries()
            .asFlow()
            .mapToList(ioDispatcher)
            .map { dict ->
                dict.map { it.toModel() }.sortedByDescending { it.createdAt }
            }
            .flowOn(ioDispatcher)
    }

    fun insert(
        id: String,
        title: String,
        isDefault: Boolean = false,
    ) {
        queries.insertDictionary(
            id = id,
            createdAt = currentTimestamp(),
            title = title.trim(),
            isDefault = if (isDefault) 0 else 1,
        )
    }

    fun delete(id: String) {
        queries.deleteDictionary(id)
    }

    fun clearAll() {
        queries.clearDictionaries()
    }
}