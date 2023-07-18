package com.translator.uzbek.english.dictionary.presentation.faq

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.translator.uzbek.english.dictionary.data.model.common.FaqModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FaqViewModel : KMMViewModel() {

    private val stateData = MutableStateFlow(viewModelScope, FaqState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    init {
        getFaq()
    }

    private fun getFaq() {
        stateData.update { it.copy(faq = faq) }
    }
}

private val faq = listOf(
    FaqModel(
        id = 1,
        question = "How does the Uzbek-English dictionary work?",
        answer = ""
    ),
)