package com.translator.uzbek.english.dictionary.presentation.statistics

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.translator.uzbek.english.dictionary.data.database.dao.WordDao
import com.translator.uzbek.english.dictionary.data.database.model.WordModel
import com.translator.uzbek.english.dictionary.data.datastore.DictionaryStore
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StatisticsViewModel : KMMViewModel(), KoinComponent {

    private val wordDao by inject<WordDao>()
    private val dictionaryStore by inject<DictionaryStore>()

    private val stateData = MutableStateFlow(viewModelScope, StatisticsState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    init {
        fetchStatisticsStore()
    }

    fun onEvent(event: StatisticsEvent) {
    }

    private fun fetchStatisticsStore() {
        viewModelScope.coroutineScope.launch {
            combine(
                wordDao.countStatusWords(WordModel.WordStatus.Learning),
                wordDao.countStatusWords(WordModel.WordStatus.Learned)
            ) { allLearning, allLearned ->
                stateData.update {
                    it.copy(
                        dailyGoal = dictionaryStore.getDailyGoal(),
                        today = 3,
                        currentStreak = 12,
                        learned = 8,
                        learning = 35,
                        new = 85,
                        skipped = 24,
                        allLearning = allLearning,
                        allLearned = allLearned,
                        allBestStreak = 132,
                        startOfLearning = dictionaryStore.getStartOfLearning()
                    )
                }
            }.collect()
        }
    }
}