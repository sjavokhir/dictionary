package com.translator.uzbek.english.dictionary.presentation.dictionary

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.translator.uzbek.english.dictionary.data.model.common.DictionaryModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class DictionaryViewModel : KMMViewModel(), KoinComponent {

    private val stateData = MutableStateFlow(viewModelScope, DictionaryState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    fun onEvent(event: DictionaryEvent) {
        when (event) {
            DictionaryEvent.FetchDictionaries -> fetchDictionaries()
            DictionaryEvent.Insert -> insertDictionary()
            DictionaryEvent.Delete -> deleteDictionary()
            is DictionaryEvent.SetArgs -> setArgs(event.title, event.id)
            is DictionaryEvent.ChangeTitle -> onChangeTitle(event.title)
        }
    }

    private fun fetchDictionaries() {
        viewModelScope.coroutineScope.launch {
            val list = buildList {
                add(DictionaryModel("1", "Oxford 3000 - A1", 965, 1))
                add(DictionaryModel("2", "Oxford 3000 - A2", 893, 10))
                add(DictionaryModel("3", "Oxford 3000 - B1", 849, 99))
                add(DictionaryModel("4", "Oxford 3000 - B2", 796, 0))
                add(DictionaryModel("5", "Oxford 5000 - B2", 734, 0))
                add(DictionaryModel("6", "Oxford 5000 - C1", 1401, 0))
            }

            stateData.update { it.copy(list = list) }
        }
    }

    private fun insertDictionary() {
        setLoading()
        setSuccess()
    }

    private fun deleteDictionary() {
        setLoading()
        setSuccess()
    }

    private fun setArgs(title: String, id: String) {
        stateData.update {
            it.copy(
                title = title,
                dictionaryId = id,
                isEnabled = title.trim().isNotEmpty()
            )
        }
    }

    private fun onChangeTitle(title: String) {
        stateData.update {
            it.copy(
                title = title,
                isEnabled = title.trim().isNotEmpty()
            )
        }
    }

    private fun setLoading() {
        stateData.update { it.copy(isLoading = true) }
    }

    private fun setSuccess() {
        stateData.update { it.copy(isSuccess = true) }
    }
}