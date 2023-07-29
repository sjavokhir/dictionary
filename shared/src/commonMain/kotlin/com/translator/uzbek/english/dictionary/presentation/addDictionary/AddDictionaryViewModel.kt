package com.translator.uzbek.english.dictionary.presentation.addDictionary

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

class AddDictionaryViewModel : KMMViewModel(), KoinComponent {

    private val dictionaryDao by inject<DictionaryDao>()

    private val stateData = MutableStateFlow(viewModelScope, AddDictionaryState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    private val dictionaryId = kotlinx.coroutines.flow.MutableStateFlow("")

    fun onEvent(event: AddDictionaryEvent) {
        when (event) {
            is AddDictionaryEvent.FetchDictionary -> fetchDictionary(event.id)
            is AddDictionaryEvent.ChangeTitle -> changeTitle(event.title)

            AddDictionaryEvent.Insert -> insert()
        }
    }

    private fun fetchDictionary(id: String) {
        dictionaryId.value = id

        viewModelScope.coroutineScope.launch {
            dictionaryDao.fetchDictionaryById(id).collectLatest { model ->
                stateData.update {
                    if (model != null) {
                        it.copy(
                            title = model.title,
                            isNewDictionary = false,
                            isEnabled = model.title.isNotBlank(),
                        )
                    } else {
                        it.copy(isNewDictionary = true)
                    }
                }
            }
        }
    }

    private fun changeTitle(title: String) {
        stateData.update {
            it.copy(
                title = title,
                isEnabled = title.isNotBlank(),
            )
        }
    }

    private fun insert() {
        setLoading()

        dictionaryDao.insert(
            id = dictionaryId.value,
            title = state.value.title,
        )

        setSuccess()
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