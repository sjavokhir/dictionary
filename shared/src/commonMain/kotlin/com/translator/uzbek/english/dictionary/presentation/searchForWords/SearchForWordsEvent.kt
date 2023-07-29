package com.translator.uzbek.english.dictionary.presentation.searchForWords

import com.translator.uzbek.english.dictionary.data.database.model.WordModel

sealed class SearchForWordsEvent {
    data class ChangeQuery(val query: String) : SearchForWordsEvent()
    data class SetWordStatus(
        val wordId: String,
        val newStatus: WordModel.WordStatus,
    ) : SearchForWordsEvent()

    data class RemoveWord(val wordId: String) : SearchForWordsEvent()
}
