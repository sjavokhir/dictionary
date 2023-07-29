package com.translator.uzbek.english.dictionary.presentation.searchForWords

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.translator.uzbek.english.dictionary.data.database.dao.WordDao
import com.translator.uzbek.english.dictionary.data.database.model.WordModel
import com.translator.uzbek.english.dictionary.presentation.dictionaryWords.DictionaryWordsEvent
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchForWordsViewModel : KMMViewModel(), KoinComponent {

    private val wordDao by inject<WordDao>()

    private val stateData = MutableStateFlow(viewModelScope, SearchForWordsState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    fun onEvent(event: SearchForWordsEvent) {
        when (event) {
            is SearchForWordsEvent.ChangeQuery -> changeQuery(event.query)
            is SearchForWordsEvent.SetWordStatus -> setWordStatus(event.wordId, event.newStatus)
            is SearchForWordsEvent.RemoveWord -> removeWord(event.wordId)
        }
    }

    private fun changeQuery(query: String) {
        setLoading(query)

        if (query.isEmpty()) {
            setSuccess(emptyList())
        } else {
            viewModelScope.coroutineScope.launch {
                wordDao.searchWords(query).collectLatest { words ->
                    setSuccess(words)
                }
            }
        }
    }

    private fun setWordStatus(wordId: String, status: WordModel.WordStatus) {
        wordDao.updateWordStatus(wordId, status)
    }

    private fun removeWord(wordId: String) {
        wordDao.delete(wordId)
    }

    private fun setLoading(query: String) {
        stateData.update {
            it.copy(
                isLoading = true,
                query = query,
            )
        }
    }

    private fun setSuccess(words: List<WordModel>) {
        stateData.update {
            it.copy(
                isLoading = false,
                words = words,
            )
        }
    }
}