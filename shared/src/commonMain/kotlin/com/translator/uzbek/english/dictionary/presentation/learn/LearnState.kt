package com.translator.uzbek.english.dictionary.presentation.learn

import com.translator.uzbek.english.dictionary.core.helpers.Constants
import com.translator.uzbek.english.dictionary.data.database.model.DictionaryModel
import com.translator.uzbek.english.dictionary.data.model.common.QuoteModel

data class LearnState(
    val headerTitle: Title = Title.Empty,
    val chosenDictionaries: List<DictionaryModel> = emptyList(),
    val dailyGoal: Int = Constants.defaultDailyGoal,
    val repeatedWords: Int = 0,
    val memorizedToday: Int = 0,
    val quote: QuoteModel? = null,
) {
    enum class Title {
        Empty,
        Morning,
        Afternoon,
        Evening,
        Night
    }
}
