package com.translator.uzbek.english.dictionary.presentation.faq

import com.translator.uzbek.english.dictionary.data.model.common.FaqModel

data class FaqState(
    val faq: List<FaqModel> = emptyList()
)