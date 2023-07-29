package com.translator.uzbek.english.dictionary.android.navigation

import com.translator.uzbek.english.dictionary.data.model.mode.FirstLanguageMode
import java.io.Serializable

data class FirstLanguageResult(
    val firstLanguage: FirstLanguageMode,
    val type: Type
) : Serializable {

    enum class Type {
        NewWord,
        Repeated
    }
}

data class ReminderResult(
    val hour: Int,
    val minute: Int,
    val weekdays: String
) : Serializable