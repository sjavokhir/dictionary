package com.translator.uzbek.english.dictionary.android.presentation.dictionaryWords

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import com.translator.uzbek.english.dictionary.android.design.components.DictContainer
import com.translator.uzbek.english.dictionary.android.design.components.DictFilledButton
import com.translator.uzbek.english.dictionary.android.design.components.DividerContent
import com.translator.uzbek.english.dictionary.android.design.localization.LocalStrings
import com.translator.uzbek.english.dictionary.android.design.localization.StringResources
import com.translator.uzbek.english.dictionary.android.design.theme.WindowBackground
import com.translator.uzbek.english.dictionary.android.presentation.destinations.AddWordScreenDestination
import com.translator.uzbek.english.dictionary.data.database.model.WordModel
import com.translator.uzbek.english.dictionary.presentation.dictionaryWords.DictionaryWordsEvent
import com.translator.uzbek.english.dictionary.presentation.dictionaryWords.DictionaryWordsState
import com.translator.uzbek.english.dictionary.presentation.dictionaryWords.DictionaryWordsViewModel
import com.translator.uzbek.english.dictionary.shared.randomUUID

@Destination
@Composable
fun DictionaryWordsScreen(
    dictionaryId: String,
    dictionaryTitle: String,
    viewModel: DictionaryWordsViewModel = viewModel(),
    navigator: DestinationsNavigator,
) {
    val strings = LocalStrings.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(dictionaryId) {
        viewModel.onEvent(DictionaryWordsEvent.FetchWords(dictionaryId))
    }

    DictContainer(
        title = dictionaryTitle,
        onNavigateUp = navigator::navigateUp,
    ) {
        DictionaryWordsScreenContent(
            strings = strings,
            state = state,
            dictionaryId = dictionaryId,
            onNavigate = navigator::navigate
        )
    }
}

@Composable
private fun DictionaryWordsScreenContent(
    strings: StringResources,
    state: DictionaryWordsState,
    dictionaryId: String,
    onNavigate: (Direction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(WindowBackground),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        if (state.isLoading) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(MaterialTheme.shapes.large),
                        strokeCap = StrokeCap.Round,
                        strokeWidth = 3.dp,
                    )
                }
            }
        } else if (state.words.isEmpty()) {
            item {
                DictFilledButton(text = strings.addWord) {
                    onNavigate(
                        AddWordScreenDestination(
                            wordId = randomUUID(),
                            dictionaryId = dictionaryId
                        )
                    )
                }
            }
        } else {
            itemsIndexed(state.words) { index, model ->
                DictionaryWordItemContent(model = model)

                if (index != state.words.lastIndex) {
                    DividerContent(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun DictionaryWordItemContent(
    model: WordModel
) {
    Text(
        text = model.word,
        style = MaterialTheme.typography.bodyLarge
    )
}