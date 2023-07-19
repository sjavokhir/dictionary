package com.translator.uzbek.english.dictionary.android.presentation.statistics.chart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.util.RandomEntriesGenerator
import kotlinx.coroutines.launch

class ShowcaseViewModel : ViewModel() {

    private val generator = RandomEntriesGenerator(
        xRange = 0..GENERATOR_X_RANGE_TOP,
        yRange = GENERATOR_Y_RANGE_BOTTOM..GENERATOR_Y_RANGE_TOP,
    )

    val multiDataSetChartEntryModelProducer: ChartEntryModelProducer =
        ChartEntryModelProducer()

    init {
        viewModelScope.launch {
            multiDataSetChartEntryModelProducer.setEntries(
                entries = List(size = MULTI_ENTRIES_COMBINED) {
                    generator.generateRandomEntries()
                },
            )
        }

        Log.d("LOG_TAG", List(size = MULTI_ENTRIES_COMBINED) {
            generator.generateRandomEntries()
        }.toString())
    }

    private companion object {
        const val MULTI_ENTRIES_COMBINED = 4
        const val GENERATOR_X_RANGE_TOP = 7
        const val GENERATOR_Y_RANGE_BOTTOM = 2
        const val GENERATOR_Y_RANGE_TOP = 20
    }
}
