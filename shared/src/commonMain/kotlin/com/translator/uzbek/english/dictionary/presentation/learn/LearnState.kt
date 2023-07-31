package com.translator.uzbek.english.dictionary.presentation.learn

import com.translator.uzbek.english.dictionary.data.model.common.QuoteModel

data class LearnState(
    val headerTitle: Title = Title.Empty,
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
