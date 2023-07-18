package com.translator.uzbek.english.dictionary.presentation.statistics

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

class StatisticsViewModel : KMMViewModel(), KoinComponent {

    private val stateData = MutableStateFlow(viewModelScope, StatisticsState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    init {
        fetchStatisticsStore()
    }

    fun onEvent(event: StatisticsEvent) {
    }

    private fun fetchStatisticsStore() {
        stateData.update {
            it.copy(
                today = 3,
                currentStreak = 12,
                allLearning = 2234,
                allLearned = 123,
                allBestStreak = 132,
                startOfLearning = "Sep, 2021"
            )
        }
    }
}