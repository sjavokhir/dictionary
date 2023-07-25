package com.translator.uzbek.english.dictionary.presentation.searchForWords

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.translator.uzbek.english.dictionary.data.model.common.DictionaryModel
import com.translator.uzbek.english.dictionary.data.model.common.SearchForWordModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class SearchForWordsViewModel : KMMViewModel(), KoinComponent {

    private val stateData = MutableStateFlow(viewModelScope, SearchForWordsState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    fun onEvent(event: SearchForWordsEvent) {
        when (event) {
            is SearchForWordsEvent.ChangeQuery -> changeQuery(event.query)
        }
    }

    private fun changeQuery(query: String) {
        stateData.update {
            it.copy(
                isLoading = true,
                query = query
            )
        }

        if (query.isEmpty()) {
            stateData.update {
                it.copy(
                    isLoading = false,
                    words = emptyList()
                )
            }
        } else {
            viewModelScope.coroutineScope.launch {
                val words = buildList {
                    add(
                        SearchForWordModel(
                            id = "1",
                            word = "Word",
                            translation = "Translation",
                            dictionary = DictionaryModel(
                                id = "1",
                                name = "Oxford 3000 - A1",
                                countWords = 965,
                                percentage = 1
                            )
                        )
                    )
                    add(
                        SearchForWordModel(
                            id = "2",
                            word = "Word",
                            translation = "Translation",
                            dictionary = DictionaryModel(
                                id = "1",
                                name = "Oxford 3000 - A1",
                                countWords = 965,
                                percentage = 1
                            )
                        )
                    )
                    add(
                        SearchForWordModel(
                            id = "3",
                            word = "Word",
                            translation = "Translation",
                            dictionary = DictionaryModel(
                                id = "1",
                                name = "Oxford 3000 - A1",
                                countWords = 965,
                                percentage = 1
                            )
                        )
                    )
                    add(
                        SearchForWordModel(
                            id = "4",
                            word = "Word",
                            translation = "Translation",
                            dictionary = DictionaryModel(
                                id = "1",
                                name = "Oxford 3000 - A1",
                                countWords = 965,
                                percentage = 1
                            )
                        )
                    )
                    add(
                        SearchForWordModel(
                            id = "5",
                            word = "Word",
                            translation = "Translation",
                            dictionary = DictionaryModel(
                                id = "1",
                                name = "Oxford 3000 - A1",
                                countWords = 965,
                                percentage = 1
                            )
                        )
                    )
                }

                stateData.update {
                    it.copy(
                        isLoading = false,
                        words = words,
                    )
                }
            }
        }
    }
}