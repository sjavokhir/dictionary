package com.translator.uzbek.english.dictionary.presentation.searchForWords

sealed class SearchForWordsEvent {
    data class ChangeQuery(val query: String) : SearchForWordsEvent()
}
