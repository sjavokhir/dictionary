package com.translator.uzbek.english.dictionary.presentation.reminder

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.translator.uzbek.english.dictionary.data.datastore.DictionaryStore
import com.translator.uzbek.english.dictionary.shared.ioDispatcher
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ReminderViewModel : KMMViewModel(), KoinComponent {

    private val dictionaryStore by inject<DictionaryStore>()

    private val stateData = MutableStateFlow(viewModelScope, ReminderState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    init {
        fetchReminderStore()
    }

    private fun fetchReminderStore() {
        viewModelScope.coroutineScope.launch(ioDispatcher) {
            val time = dictionaryStore.getReminderTime()

            stateData.update {
                it.copy(
                    hour = time.hour,
                    minute = time.minute
                )
            }
        }
    }
}