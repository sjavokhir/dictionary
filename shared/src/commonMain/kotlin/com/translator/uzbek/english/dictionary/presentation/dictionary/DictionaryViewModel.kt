package com.translator.uzbek.english.dictionary.presentation.dictionary

import com.translator.uzbek.english.dictionary.data.database.dao.DictionaryDao
import com.translator.uzbek.english.dictionary.data.database.dao.WordDao
import com.translator.uzbek.english.dictionary.data.database.model.DictionaryModel
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DictionaryViewModel : ViewModel(), KoinComponent {

    private val dictionaryDao by inject<DictionaryDao>()
    private val wordDao by inject<WordDao>()

    private val stateData = MutableStateFlow(DictionaryState())
    val state = stateData.asStateFlow()

    init {
        fetchDictionaries()
    }

    fun onEvent(event: DictionaryEvent) {
        when (event) {
            is DictionaryEvent.ResetProgress -> resetProgress(event.model)
            is DictionaryEvent.RemoveDictionary -> removeDictionary(event.model)
            is DictionaryEvent.ClearDictionary -> clearDictionary(event.model)
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

    private fun resetProgress(model: DictionaryModel) {
        wordDao.resetDictionaryProgress(model.id)
    }

    private fun removeDictionary(model: DictionaryModel) {
        dictionaryDao.deleteDictionary(model.id)
    }

    private fun clearDictionary(model: DictionaryModel) {
        wordDao.clearWordsByDictionaryId(model.id)
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