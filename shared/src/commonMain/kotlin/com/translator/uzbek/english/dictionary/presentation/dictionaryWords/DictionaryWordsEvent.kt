package com.translator.uzbek.english.dictionary.presentation.dictionaryWords

import com.translator.uzbek.english.dictionary.data.database.model.WordModel

sealed class DictionaryWordsEvent {

    data class FetchWords(val dictionaryId: String) : DictionaryWordsEvent()
    object ResetDictionary : DictionaryWordsEvent()
    object RemoveDictionary : DictionaryWordsEvent()
    object ClearDictionary : DictionaryWordsEvent()

    data class SetWordStatus(
        val wordId: String,
        val status: WordModel.WordStatus,
    ) : DictionaryWordsEvent()

    data class RemoveWord(val wordId: String) : DictionaryWordsEvent()
}