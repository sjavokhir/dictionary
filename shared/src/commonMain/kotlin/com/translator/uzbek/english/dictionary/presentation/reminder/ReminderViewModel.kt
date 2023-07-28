package com.translator.uzbek.english.dictionary.presentation.reminder

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.translator.uzbek.english.dictionary.core.extensions.tryCatch
import com.translator.uzbek.english.dictionary.data.datastore.DictionaryStore
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    fun onEvent(event: ReminderEvent) {
        when (event) {
            is ReminderEvent.ChangeHourMinute -> changeHourMinute(event.hour, event.minute)
            ReminderEvent.SelectAll -> selectAll()
            is ReminderEvent.SelectWeekday -> selectWeekday(event.position, event.selected)
        }
    }

    private fun fetchReminderStore() {
        tryCatch {
            val time = dictionaryStore.getReminderTime()
            val weekdays = dictionaryStore.getReminderDays()

            stateData.update {
                it.copy(
                    hour = time.hour,
                    minute = time.minute,
                    weekdays = weekdays,
                )
            }
        }
    }

    private fun changeHourMinute(hour: Int, minute: Int) {
        stateData.update {
            it.copy(
                hour = hour,
                minute = minute,
            )
        }
    }

    private fun selectAll() {
        stateData.update {
            it.copy(weekdays = listOf(true, true, true, true, true, true, true))
        }
    }

    private fun selectWeekday(position: Int, selected: Boolean) {
        if (state.value.weekdays.count { it } == 1 && selected) return

        val weekdays = state.value.weekdays.toMutableList()
        weekdays[position] = !selected

        stateData.update { it.copy(weekdays = weekdays) }
    }
}