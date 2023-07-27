package com.translator.uzbek.english.dictionary.presentation.addDictionary

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.translator.uzbek.english.dictionary.data.database.dao.DictionaryDao
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddDictionaryViewModel : KMMViewModel(), KoinComponent {

    private val dictionaryDao by inject<DictionaryDao>()

    private val stateData = MutableStateFlow(viewModelScope, AddDictionaryState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    fun onEvent(event: AddDictionaryEvent) {
        when (event) {
            is AddDictionaryEvent.SetDictionaryDetails -> setDictionaryDetails(
                id = event.id,
                title = event.title,
            )

            is AddDictionaryEvent.ChangeTitle -> changeTitle(event.title)

            AddDictionaryEvent.Insert -> insert()
        }
    }

    private fun insert() {
        setLoading()

        dictionaryDao.insert(
            id = state.value.id,
            title = state.value.title,
        )

        setSuccess()
    }

    private fun setDictionaryDetails(id: String, title: String) {
        stateData.update {
            it.copy(
                id = id,
                title = title,
                isEnabled = title.isNotBlank(),
            )
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