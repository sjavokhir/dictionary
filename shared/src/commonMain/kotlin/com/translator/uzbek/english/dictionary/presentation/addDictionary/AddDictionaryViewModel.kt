package com.translator.uzbek.english.dictionary.presentation.addDictionary

import com.translator.uzbek.english.dictionary.data.database.dao.DictionaryDao
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddDictionaryViewModel : ViewModel(), KoinComponent {

    private val dictionaryDao by inject<DictionaryDao>()

    private val stateData = MutableStateFlow(AddDictionaryState())
    val state = stateData.asStateFlow()

    private val dictionaryId = MutableStateFlow("")
    private val isSelected = MutableStateFlow(false)

    fun onEvent(event: AddDictionaryEvent) {
        when (event) {
            is AddDictionaryEvent.FetchDictionary -> fetchDictionary(event.id)
            is AddDictionaryEvent.ChangeTitle -> changeTitle(event.title)

            AddDictionaryEvent.Insert -> insert()
        }
    }

    private fun fetchDictionary(id: String) {
        dictionaryId.value = id

        viewModelScope.launch {
            dictionaryDao.fetchDictionaryById(id).collectLatest { model ->
                isSelected.value = model?.isSelected ?: false

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

        dictionaryDao.insertDictionary(
            id = dictionaryId.value,
            title = state.value.title,
            isSelected = isSelected.value
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