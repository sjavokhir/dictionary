package com.translator.uzbek.english.dictionary.presentation.dictionarySelection

import com.translator.uzbek.english.dictionary.data.database.dao.DictionaryDao
import com.translator.uzbek.english.dictionary.data.database.model.DictionaryModel
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DictionarySelectionViewModel : ViewModel(), KoinComponent {

    private val dictionaryDao by inject<DictionaryDao>()

    private val stateData = MutableStateFlow(DictionarySelectionState())
    val state = stateData.asStateFlow()

    init {
        fetchDictionaries()
    }

    fun onEvent(event: DictionarySelectionEvent) {
        when (event) {
            is DictionarySelectionEvent.SelectDictionary -> selectDictionary(event.model)
        }
    }

    private fun fetchDictionaries() {
        setLoading()

        viewModelScope.launch {
            dictionaryDao.fetchDictionaries().collectLatest { dictionaries ->
                setSuccess(dictionaries)
            }
        }
    }

    private fun selectDictionary(model: DictionaryModel) {
        dictionaryDao.updateDictionarySelected(
            id = model.id,
            isSelected = !model.isSelected
        )
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