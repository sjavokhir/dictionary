package com.translator.uzbek.english.dictionary.android.presentation.addDictionary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.translator.uzbek.english.dictionary.android.design.components.DictContainer
import com.translator.uzbek.english.dictionary.android.design.components.DictFilledButton
import com.translator.uzbek.english.dictionary.android.design.components.DictTextField
import com.translator.uzbek.english.dictionary.android.design.localization.LocalStrings
import com.translator.uzbek.english.dictionary.android.design.localization.StringResources
import com.translator.uzbek.english.dictionary.android.design.theme.WindowBackground
import com.translator.uzbek.english.dictionary.presentation.dictionary.DictionaryEvent
import com.translator.uzbek.english.dictionary.presentation.dictionary.DictionaryState
import com.translator.uzbek.english.dictionary.presentation.dictionary.DictionaryViewModel

@Destination
@Composable
fun AddDictionaryScreen(
    title: String = "",
    id: String,
    viewModel: DictionaryViewModel = viewModel(),
    resultNavigator: ResultBackNavigator<Boolean>
) {
    val strings = LocalStrings.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(title, id) {
        viewModel.onEvent(DictionaryEvent.SetArgs(title, id))
    }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            resultNavigator.navigateBack(result = true)
        }
    }

    DictContainer(
        title = if (title.isEmpty()) strings.addDictionary else strings.editDictionary,
        onNavigateUp = {
            resultNavigator.navigateBack(result = false)
        }
    ) {
        AddDictionaryScreenContent(
            strings = strings,
            state = state,
            isNewDictionary = title.isEmpty(),
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
private fun AddDictionaryScreenContent(
    strings: StringResources,
    state: DictionaryState,
    isNewDictionary: Boolean,
    onEvent: (DictionaryEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(WindowBackground),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            DictTextField(
                value = state.title,
                onValueChange = {
                    onEvent(DictionaryEvent.ChangeTitle(it))
                },
                hint = strings.dictionaryTitle
            )
        }
        item {
            DictFilledButton(
                text = if (isNewDictionary) strings.add else strings.save,
                enabled = state.isEnabled && !state.isLoading
            ) {
                onEvent(DictionaryEvent.Insert)
            }
        }
    }
}