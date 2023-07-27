package com.translator.uzbek.english.dictionary.presentation.dictionaryWords

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.translator.uzbek.english.dictionary.data.database.dao.WordDao
import com.translator.uzbek.english.dictionary.data.database.model.WordModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DictionaryWordsViewModel : KMMViewModel(), KoinComponent {

    private val wordDao by inject<WordDao>()

    private val stateData = MutableStateFlow(viewModelScope, DictionaryWordsState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    fun onEvent(event: DictionaryWordsEvent) {
        when(event) {
            is DictionaryWordsEvent.FetchWords -> fetchWords(event.dictionaryId)
        }
    }

    private fun fetchWords(dictionaryId: String) {
        setLoading()

        viewModelScope.coroutineScope.launch {
            wordDao.fetchWords(dictionaryId).collectLatest { words ->
                setSuccess(words)
            }
        }
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