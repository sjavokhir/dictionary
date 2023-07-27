package com.translator.uzbek.english.dictionary.android.presentation.addWord

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
import com.translator.uzbek.english.dictionary.presentation.addWord.AddWordEvent
import com.translator.uzbek.english.dictionary.presentation.addWord.AddWordState
import com.translator.uzbek.english.dictionary.presentation.addWord.AddWordViewModel

@Destination
@Composable
fun AddWordScreen(
    wordId: String,
    dictionaryId: String,
    viewModel: AddWordViewModel = viewModel(),
    resultNavigator: ResultBackNavigator<Boolean>
) {
    val strings = LocalStrings.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(wordId) {
        viewModel.onEvent(AddWordEvent.FetchWord(wordId, dictionaryId))
    }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            resultNavigator.navigateBack(result = true)
        }
    }

    DictContainer(
        title = if (state.isNewWord) strings.addWord else strings.editWord,
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
    state: AddWordState,
    onEvent: (AddWordEvent) -> Unit
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
                value = state.word,
                onValueChange = {
                    onEvent(AddWordEvent.ChangeWord(it))
                },
                hint = strings.word,
                placeholder = strings.enterWord
            )
        }
        item {
            DictTextField(
                value = state.translation,
                onValueChange = {
                    onEvent(AddWordEvent.ChangeTranslation(it))
                },
                hint = strings.translation,
                placeholder = strings.enterTranslation
            )
        }
        item {
            DictTextField(
                value = state.transcription,
                onValueChange = {
                    onEvent(AddWordEvent.ChangeTranscription(it))
                },
                hint = strings.transcription,
                placeholder = strings.enterTranscription
            )
        }
        item {
            DictFilledButton(
                text = if (state.isNewWord) strings.add else strings.save,
                enabled = state.isEnabled && !state.isLoading
            ) {
                onEvent(AddWordEvent.Insert)
            }
        }
    }
}