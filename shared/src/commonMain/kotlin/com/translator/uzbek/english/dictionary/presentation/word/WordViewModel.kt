package com.translator.uzbek.english.dictionary.presentation.word

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.translator.uzbek.english.dictionary.data.database.dao.DictionaryDao
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WordViewModel : KMMViewModel(), KoinComponent {

    private val dictionaryDao by inject<DictionaryDao>()

    private val stateData = MutableStateFlow(viewModelScope, WordState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    init {
        fetchDictionaries()
    }

    fun onEvent(event: WordEvent) {
        when (event) {
            WordEvent.Insert -> insert()
            WordEvent.Delete -> delete()

            is WordEvent.SetWordDetails -> setDictionaryDetails(
                dictionaryTitle = event.dictionaryTitle,
                dictionaryId = event.dictionaryId,
            )

            is WordEvent.ChangeDictionaryTitle -> {
                changeDictionaryTitle(event.dictionaryTitle)
            }
        }
    }

    private fun fetchDictionaries() {
        setLoading()

        viewModelScope.coroutineScope.launch {
            dictionaryDao.fetchDictionaries().collectLatest { list ->
                stateData.update {
                    it.copy(
                        isLoading = false,
                        dictionaries = list,
                    )
                }
            }
        }
    }

    private fun insert() {
        setLoading()

        dictionaryDao.insert(
            id = state.value.dictionaryId,
            title = state.value.dictionaryTitle,
        )

        setSuccess()
    }

    private fun delete() {
        setLoading()

        dictionaryDao.delete(state.value.dictionaryId)

        setSuccess()
    }

    private fun setDictionaryDetails(dictionaryTitle: String, dictionaryId: String) {
        stateData.update {
            it.copy(
                dictionaryTitle = dictionaryTitle,
                dictionaryId = dictionaryId,
                isEnabled = dictionaryTitle.isNotBlank(),
            )
        }
    }

    private fun changeDictionaryTitle(title: String) {
        stateData.update {
            it.copy(
                dictionaryTitle = title,
                isEnabled = title.isNotBlank(),
            )
        }
    }

    private fun setLoading() {
        stateData.update { it.copy(isLoading = true) }
    }

    private fun setSuccess() {
        stateData.update {
            it.copy(
                isLoading = false,
                isSuccess = true,
            )
        }
    }
}