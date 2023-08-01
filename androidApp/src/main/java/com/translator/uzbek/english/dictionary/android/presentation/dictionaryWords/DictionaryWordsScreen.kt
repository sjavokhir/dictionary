package com.translator.uzbek.english.dictionary.android.presentation.dictionaryWords

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import com.translator.uzbek.english.dictionary.android.R
import com.translator.uzbek.english.dictionary.android.core.extensions.clickableSingle
import com.translator.uzbek.english.dictionary.android.core.extensions.defaultPadding
import com.translator.uzbek.english.dictionary.android.core.tts.rememberTtsHelper
import com.translator.uzbek.english.dictionary.android.design.components.DictContainer
import com.translator.uzbek.english.dictionary.android.design.components.DictIcon
import com.translator.uzbek.english.dictionary.android.design.localization.LocalStrings
import com.translator.uzbek.english.dictionary.android.design.localization.StringResources
import com.translator.uzbek.english.dictionary.android.design.mapper.color
import com.translator.uzbek.english.dictionary.android.design.mapper.localized
import com.translator.uzbek.english.dictionary.android.design.theme.DividerColor
import com.translator.uzbek.english.dictionary.android.design.theme.WindowBackground
import com.translator.uzbek.english.dictionary.android.navigation.DictionaryArgs
import com.translator.uzbek.english.dictionary.android.presentation.destinations.AddDictionaryScreenDestination
import com.translator.uzbek.english.dictionary.android.presentation.destinations.AddWordScreenDestination
import com.translator.uzbek.english.dictionary.android.presentation.dictionary.DictionaryActionsSheet
import com.translator.uzbek.english.dictionary.data.database.model.WordModel
import com.translator.uzbek.english.dictionary.presentation.dictionaryWords.DictionaryWordsEvent
import com.translator.uzbek.english.dictionary.presentation.dictionaryWords.DictionaryWordsState
import com.translator.uzbek.english.dictionary.presentation.dictionaryWords.DictionaryWordsViewModel
import com.translator.uzbek.english.dictionary.shared.randomUUID

@Destination
@Composable
fun DictionaryWordsScreen(
    args: DictionaryArgs,
    viewModel: DictionaryWordsViewModel = viewModel(),
    navigator: DestinationsNavigator,
) {
    val strings = LocalStrings.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    val ttsHelper = rememberTtsHelper()

    var showDictActionsSheet by remember { mutableStateOf(false) }
    var selectedWord by remember { mutableStateOf<WordModel?>(null) }

    DictionaryActionsSheet(
        strings = strings,
        show = showDictActionsSheet,
        isDefault = args.isDefault,
        onDismiss = {
            showDictActionsSheet = false
        },
        onReset = {
            viewModel.onEvent(DictionaryWordsEvent.ResetDictionary)
        },
        onEdit = {
            navigator.navigate(AddDictionaryScreenDestination(args.id))
        },
        onRemove = {
            viewModel.onEvent(DictionaryWordsEvent.RemoveDictionary)
            navigator.navigateUp()
        },
        onClear = {
            viewModel.onEvent(DictionaryWordsEvent.ClearDictionary)
        },
    )

    WordActionsSheet(
        strings = strings,
        selectedWord = selectedWord,
        onDismiss = {
            selectedWord = null
        },
        onStatus = { wordId, newStatus ->
            viewModel.onEvent(DictionaryWordsEvent.SetWordStatus(wordId, newStatus))
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
            viewModel.onEvent(DictionaryWordsEvent.RemoveWord(it))
        }
    )

    LaunchedEffect(args.id) {
        viewModel.onEvent(DictionaryWordsEvent.FetchWords(args.id))
    }

    DictContainer(
        title = args.title,
        onNavigateUp = navigator::navigateUp,
        actions = {
            IconButton(onClick = { showDictActionsSheet = true }) {
                DictIcon(painter = painterResource(id = R.drawable.ic_more_vert))
            }
        }
    ) {
        DictionaryWordsScreenContent(
            strings = strings,
            state = state,
            dictionaryId = args.id,
            onNavigate = navigator::navigate,
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
private fun DictionaryWordsScreenContent(
    strings: StringResources,
    state: DictionaryWordsState,
    dictionaryId: String,
    onNavigate: (Direction) -> Unit,
    onWordClick: (WordModel) -> Unit,
    onPlayClick: (WordModel) -> Unit,
) {
    val listState = rememberLazyListState()
    var previousIndex by remember(listState) {
        mutableIntStateOf(listState.firstVisibleItemIndex)
    }
    var previousScrollOffset by remember(listState) {
        mutableIntStateOf(listState.firstVisibleItemScrollOffset)
    }
    val isScrollingUp by remember(listState) {
        derivedStateOf {
            if (previousIndex != listState.firstVisibleItemIndex) {
                previousIndex > listState.firstVisibleItemIndex
            } else {
                previousScrollOffset >= listState.firstVisibleItemScrollOffset
            }.also {
                previousIndex = listState.firstVisibleItemIndex
                previousScrollOffset = listState.firstVisibleItemScrollOffset
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WindowBackground),
    ) {
        LazyColumn(
            state = listState,
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
            } else {
                items(state.words) { model ->
                    DictionaryWordItemContent(
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

        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            visible = isScrollingUp,
            enter = slideInVertically(initialOffsetY = { it * 2 }),
            exit = slideOutVertically(targetOffsetY = { it * 2 }),
        ) {
            ExtendedFloatingActionButton(
                onClick = {
                    onNavigate(
                        AddWordScreenDestination(
                            wordId = randomUUID(),
                            dictionaryId = dictionaryId
                        )
                    )
                },
            ) {
                Text(
                    text = strings.addWord,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun DictionaryWordItemContent(
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
                text = model.word,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = model.translation,
                style = MaterialTheme.typography.bodyLarge,
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