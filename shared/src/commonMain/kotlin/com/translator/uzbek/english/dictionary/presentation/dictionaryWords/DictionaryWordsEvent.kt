package com.translator.uzbek.english.dictionary.presentation.dictionaryWords

sealed class DictionaryWordsEvent {

    data class FetchWords(val dictionaryId: String) : DictionaryWordsEvent()
}