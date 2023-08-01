package com.translator.uzbek.english.dictionary.presentation.dictionaryWords

import com.translator.uzbek.english.dictionary.data.database.dao.DictionaryDao
import com.translator.uzbek.english.dictionary.data.database.dao.WordDao
import com.translator.uzbek.english.dictionary.data.database.model.WordModel
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DictionaryWordsViewModel : ViewModel(), KoinComponent {

    private val dictionaryDao by inject<DictionaryDao>()
    private val wordDao by inject<WordDao>()

    private val stateData = MutableStateFlow(DictionaryWordsState())
    val state = stateData.asStateFlow()

    private val dictionaryId = MutableStateFlow("")

    fun onEvent(event: DictionaryWordsEvent) {
        when (event) {
            is DictionaryWordsEvent.FetchWords -> fetchWords(event.dictionaryId)
            DictionaryWordsEvent.ResetDictionary -> resetDictionary()
            DictionaryWordsEvent.RemoveDictionary -> removeDictionary()
            DictionaryWordsEvent.ClearDictionary -> clearDictionary()
            is DictionaryWordsEvent.SetWordStatus -> setWordStatus(event.wordId, event.newStatus)
            is DictionaryWordsEvent.RemoveWord -> removeWord(event.wordId)
        }
    }

    private fun fetchWords(dictionaryId: String) {
        this.dictionaryId.value = dictionaryId

        setLoading()

        viewModelScope.launch {
            wordDao.fetchWords(dictionaryId).collectLatest { words ->
                setSuccess(words)
            }
        }
    }

    private fun resetDictionary() {
        wordDao.resetDictionaryProgress(dictionaryId.value)
    }

    private fun removeDictionary() {
        dictionaryDao.deleteDictionary(dictionaryId.value)
    }

    private fun clearDictionary() {
        wordDao.clearWordsByDictionaryId(dictionaryId.value)
    }

    private fun setWordStatus(wordId: String, status: WordModel.WordStatus) {
        wordDao.updateWordStatus(wordId, status)
    }

    private fun removeWord(wordId: String) {
        wordDao.deleteWordById(wordId)
    }

    private fun setLoading() {
        stateData.update { it.copy(isLoading = true) }
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