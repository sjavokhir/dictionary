package com.translator.uzbek.english.dictionary.presentation.dictionary

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.translator.uzbek.english.dictionary.data.database.dao.DictionaryDao
import com.translator.uzbek.english.dictionary.data.database.model.DictionaryModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DictionaryViewModel : KMMViewModel(), KoinComponent {

    private val dictionaryDao by inject<DictionaryDao>()

    private val stateData = MutableStateFlow(viewModelScope, DictionaryState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    init {
        fetchDictionaries()
    }

    private fun fetchDictionaries() {
        setLoading()

        viewModelScope.coroutineScope.launch {
            dictionaryDao.fetchDictionaries().collectLatest { dictionaries ->
                setSuccess(dictionaries)
            }
        }
    }

    private fun setLoading() {
        stateData.update { it.copy(isLoading = true) }
    }

    private fun setSuccess(dictionaries: List<DictionaryModel>) {
        stateData.update {
            it.copy(
                isLoading = false,
                dictionaries = dictionaries,
            )
        }
    }
}