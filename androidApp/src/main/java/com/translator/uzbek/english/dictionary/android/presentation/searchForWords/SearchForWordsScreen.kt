package com.translator.uzbek.english.dictionary.android.presentation.searchForWords

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.translator.uzbek.english.dictionary.android.R
import com.translator.uzbek.english.dictionary.android.core.extensions.clickableSingle
import com.translator.uzbek.english.dictionary.android.core.extensions.defaultPadding
import com.translator.uzbek.english.dictionary.android.core.tts.rememberTtsHelper
import com.translator.uzbek.english.dictionary.android.design.components.DictContainer
import com.translator.uzbek.english.dictionary.android.design.components.DictIcon
import com.translator.uzbek.english.dictionary.android.design.components.DividerContent
import com.translator.uzbek.english.dictionary.android.design.localization.LocalStrings
import com.translator.uzbek.english.dictionary.android.design.localization.StringResources
import com.translator.uzbek.english.dictionary.android.design.mapper.color
import com.translator.uzbek.english.dictionary.android.design.mapper.localized
import com.translator.uzbek.english.dictionary.android.design.theme.DividerColor
import com.translator.uzbek.english.dictionary.android.design.theme.WindowBackground
import com.translator.uzbek.english.dictionary.android.presentation.destinations.AddWordScreenDestination
import com.translator.uzbek.english.dictionary.android.presentation.dictionaryWords.WordActionsSheet
import com.translator.uzbek.english.dictionary.data.database.model.WordModel
import com.translator.uzbek.english.dictionary.presentation.searchForWords.SearchForWordsEvent
import com.translator.uzbek.english.dictionary.presentation.searchForWords.SearchForWordsState
import com.translator.uzbek.english.dictionary.presentation.searchForWords.SearchForWordsViewModel

@Destination
@Composable
fun SearchForWordsScreen(
    viewModel: SearchForWordsViewModel = viewModel(),
    navigator: DestinationsNavigator
) {
    val strings = LocalStrings.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    val ttsHelper = rememberTtsHelper()

    var selectedWord by remember { mutableStateOf<WordModel?>(null) }

    WordActionsSheet(
        strings = strings,
        selectedWord = selectedWord,
        onDismiss = {
            selectedWord = null
        },
        onStatus = { wordId, newStatus ->
            viewModel.onEvent(SearchForWordsEvent.SetWordStatus(wordId, newStatus))
        },
        onEdit = {
            navigator.navigate(
                AddWordScreenDestination(
                    wordId = it.id,
                    dictionaryId = it.dictionaryId
                )
            )
        },
        onRemove = {
            viewModel.onEvent(SearchForWordsEvent.RemoveWord(it))
        }
    )

    DictContainer(
        title = strings.searchForWords,
        onNavigateUp = navigator::navigateUp
    ) {
        SearchForWordsScreenContent(
            strings = strings,
            state = state,
            onEvent = viewModel::onEvent,
            onWordClick = {
                selectedWord = it
            },
            onPlayClick = {
                ttsHelper.speak(it.word)
            },
        )
    }
}

@Composable
private fun SearchForWordsScreenContent(
    strings: StringResources,
    state: SearchForWordsState,
    onEvent: (SearchForWordsEvent) -> Unit,
    onWordClick: (WordModel) -> Unit,
    onPlayClick: (WordModel) -> Unit,
) {
    Column {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(WindowBackground),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (state.isLoading) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(MaterialTheme.shapes.large),
                            strokeCap = StrokeCap.Round,
                            strokeWidth = 3.dp
                        )
                    }
                }
            } else {
                items(state.words) { model ->
                    SearchForWordItemContent(
                        model = model,
                        onClick = {
                            onWordClick(model)
                        },
                        onPlayClick = {
                            onPlayClick(model)
                        }
                    )
                }
            }
        }

        SearchTextField(
            strings = strings,
            value = state.query,
            onValueChange = {
                onEvent(SearchForWordsEvent.ChangeQuery(it))
            }
        )
    }
}

@Composable
private fun SearchTextField(
    modifier: Modifier = Modifier,
    strings: StringResources,
    value: String,
    onValueChange: (String) -> Unit,
) {
    BasicTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onBackground
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.outline),
        decorationBox = { innerTextField ->
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                DividerContent()

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    DictIcon(
                        painter = painterResource(id = R.drawable.ic_search),
                        color = MaterialTheme.colorScheme.outline
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.Top)
                            .padding(vertical = 18.dp),
                        contentAlignment = Alignment.TopStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = strings.enterAtLeastOneLetter,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.outline,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        innerTextField()
                    }
                }
            }
        }
    )
}

@Composable
private fun SearchForWordItemContent(
    model: WordModel,
    onClick: () -> Unit,
    onPlayClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = 1.dp,
                color = DividerColor,
                shape = MaterialTheme.shapes.medium
            )
            .background(MaterialTheme.colorScheme.background)
            .clickableSingle(onClick = onClick)
            .defaultPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(9.dp)
                        .clip(CircleShape)
                        .background(model.status.color())
                )

                Text(
                    text = model.status.localized(repeats = model.repeats),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline,
                )
            }

            Text(
                text = "${model.word} - ${model.translation}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = model.dictionaryTitle.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }

        IconButton(
            onClick = onPlayClick,
            modifier = Modifier.size(36.dp)
        ) {
            DictIcon(
                painter = painterResource(id = R.drawable.ic_play_filled),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
