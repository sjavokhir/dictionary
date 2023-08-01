package com.translator.uzbek.english.dictionary.android.navigation

import com.translator.uzbek.english.dictionary.data.database.model.DictionaryModel
import java.io.Serializable

data class DictionaryArgs(
    val id: String,
    val title: String,
    val isDefault: Boolean,
) : Serializable

fun DictionaryModel.toArgs(): DictionaryArgs {
    return DictionaryArgs(
        id = id,
        title = title,
        isDefault = isDefault
    )
}