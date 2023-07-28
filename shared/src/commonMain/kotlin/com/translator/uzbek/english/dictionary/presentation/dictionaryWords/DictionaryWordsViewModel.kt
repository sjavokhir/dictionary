package com.translator.uzbek.english.dictionary.presentation.dictionaryWords

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.translator.uzbek.english.dictionary.data.database.dao.DictionaryDao
import com.translator.uzbek.english.dictionary.data.database.dao.WordDao
import com.translator.uzbek.english.dictionary.data.database.model.WordModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DictionaryWordsViewModel : KMMViewModel(), KoinComponent {

    private val dictionaryDao by inject<DictionaryDao>()
    private val wordDao by inject<WordDao>()

    private val stateData = MutableStateFlow(viewModelScope, DictionaryWordsState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    fun onEvent(event: DictionaryWordsEvent) {
        when (event) {
            is DictionaryWordsEvent.FetchWords -> fetchWords(event.dictionaryId)
            DictionaryWordsEvent.ResetDictionary -> resetDictionary()
            DictionaryWordsEvent.RemoveDictionary -> removeDictionary()
            DictionaryWordsEvent.ClearDictionary -> clearDictionary()
            is DictionaryWordsEvent.SetWordStatus -> setWordStatus(event.wordId, event.status)
            is DictionaryWordsEvent.RemoveWord -> removeWord(event.wordId)
        }
    }

    private fun fetchWords(dictionaryId: String) {
        setLoading()

        viewModelScope.coroutineScope.launch {
            wordDao.fetchWords(dictionaryId).collectLatest { words ->
                setSuccess(words, dictionaryId)
            }
        }
    }

    private fun resetDictionary() {
        wordDao.resetProgress(state.value.dictionaryId)
    }

    private fun removeDictionary() {
        dictionaryDao.delete(state.value.dictionaryId)
    }

    private fun clearDictionary() {
        wordDao.clearAll(state.value.dictionaryId)
    }

    private fun setWordStatus(wordId: String, status: WordModel.WordStatus) {
        wordDao.updateWordStatus(wordId, status)
    }

    private fun removeWord(wordId: String) {
        wordDao.delete(wordId)
    }

    private fun setLoading() {
        stateData.update { it.copy(isLoading = true) }
    }

    private fun setSuccess(
        words: List<WordModel>,
        dictionaryId: String,
    ) {
        stateData.update {
            it.copy(
                isLoading = false,
                dictionaryId = dictionaryId,
                words = words,
            )
        }
    }
}