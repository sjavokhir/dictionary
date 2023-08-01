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
import com.translator.uzbek.english.dictionary.presentation.addDictionary.AddDictionaryEvent
import com.translator.uzbek.english.dictionary.presentation.addDictionary.AddDictionaryState
import com.translator.uzbek.english.dictionary.presentation.addDictionary.AddDictionaryViewModel

@Destination
@Composable
fun AddDictionaryScreen(
    id: String,
    viewModel: AddDictionaryViewModel = viewModel(),
    resultNavigator: ResultBackNavigator<Boolean>
) {
    val strings = LocalStrings.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.onEvent(AddDictionaryEvent.FetchDictionary(id))
    }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            resultNavigator.navigateBack(result = true)
        }
    }

    DictContainer(
        title = if (state.isNewDictionary) strings.addDictionary else strings.editDictionary,
        onNavigateUp = {
            resultNavigator.navigateBack(result = false)
        }
    ) {
        AddDictionaryScreenContent(
            strings = strings,
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
private fun AddDictionaryScreenContent(
    strings: StringResources,
    state: AddDictionaryState,
    onEvent: (AddDictionaryEvent) -> Unit
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
                    onEvent(AddDictionaryEvent.ChangeTitle(it))
                },
                hint = strings.dictionaryTitle,
                placeholder = strings.enterDictionaryTitle
            )
        }
        item {
            DictFilledButton(
                text = if (state.isNewDictionary) strings.add else strings.save,
                enabled = state.isEnabled && !state.isLoading
            ) {
                onEvent(AddDictionaryEvent.Insert)
            }
        }
    }
}