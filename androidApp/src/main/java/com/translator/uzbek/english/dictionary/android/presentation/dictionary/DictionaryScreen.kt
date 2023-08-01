package com.translator.uzbek.english.dictionary.android.presentation.dictionary

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.shape.CircleShape
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
import com.translator.uzbek.english.dictionary.android.design.components.DictContainer
import com.translator.uzbek.english.dictionary.android.design.components.DictIcon
import com.translator.uzbek.english.dictionary.android.design.components.DividerContent
import com.translator.uzbek.english.dictionary.android.design.localization.LocalStrings
import com.translator.uzbek.english.dictionary.android.design.localization.StringResources
import com.translator.uzbek.english.dictionary.android.design.theme.DividerColor
import com.translator.uzbek.english.dictionary.android.design.theme.WindowBackground
import com.translator.uzbek.english.dictionary.android.navigation.toArgs
import com.translator.uzbek.english.dictionary.android.presentation.destinations.AddDictionaryScreenDestination
import com.translator.uzbek.english.dictionary.android.presentation.destinations.DictionaryWordsScreenDestination
import com.translator.uzbek.english.dictionary.android.presentation.destinations.SearchForWordsScreenDestination
import com.translator.uzbek.english.dictionary.data.database.model.DictionaryModel
import com.translator.uzbek.english.dictionary.presentation.dictionary.DictionaryEvent
import com.translator.uzbek.english.dictionary.presentation.dictionary.DictionaryState
import com.translator.uzbek.english.dictionary.presentation.dictionary.DictionaryViewModel
import com.translator.uzbek.english.dictionary.shared.randomUUID

@Destination
@Composable
fun DictionaryScreen(
    viewModel: DictionaryViewModel = viewModel(),
    navigator: DestinationsNavigator
) {
    val strings = LocalStrings.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    var selectedDictionary by remember { mutableStateOf<DictionaryModel?>(null) }

    DictionaryActionsSheet(
        strings = strings,
        show = selectedDictionary != null,
        isDefault = selectedDictionary?.isDefault ?: true,
        onDismiss = {
            selectedDictionary = null
        },
        onReset = {
            selectedDictionary?.let {
                viewModel.onEvent(DictionaryEvent.ResetProgress(it))
            }
        },
        onEdit = {
            selectedDictionary?.let {
                navigator.navigate(AddDictionaryScreenDestination(it.id))
            }
        },
        onRemove = {
            selectedDictionary?.let {
                viewModel.onEvent(DictionaryEvent.RemoveDictionary(it))
            }
        },
        onClear = {
            selectedDictionary?.let {
                viewModel.onEvent(DictionaryEvent.ClearDictionary(it))
            }
        },
    )

    DictContainer(
        title = strings.dictionary,
        actions = {
            IconButton(
                onClick = {
                    navigator.navigate(SearchForWordsScreenDestination)
                }
            ) {
                DictIcon(painter = painterResource(id = R.drawable.ic_search))
            }
        }
    ) {
        DictionaryScreenContent(
            strings = strings,
            state = state,
            onNavigate = navigator::navigate,
            onBottomSheetActions = {
                selectedDictionary = it
            }
        )
    }
}

@Composable
private fun DictionaryScreenContent(
    strings: StringResources,
    state: DictionaryState,
    onNavigate: (Direction) -> Unit,
    onBottomSheetActions: (DictionaryModel) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(WindowBackground),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            AddDictionaryItemContent(strings) {
                onNavigate(AddDictionaryScreenDestination(randomUUID()))
            }
        }
        item {
            if (state.isLoading) {
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
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .border(
                            width = 1.dp,
                            color = DividerColor,
                            shape = MaterialTheme.shapes.medium
                        )
                        .background(MaterialTheme.colorScheme.background),
                ) {
                    state.dictionaries.forEachIndexed { index, model ->
                        DictionaryItemContent(
                            strings = strings,
                            model = model,
                            onClick = {
                                onNavigate(DictionaryWordsScreenDestination(model.toArgs()))
                            },
                            onLongClick = {
                                onBottomSheetActions(model)
                            }
                        )

                        if (index != state.dictionaries.lastIndex) {
                            DividerContent(
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AddDictionaryItemContent(
    strings: StringResources,
    onClick: () -> Unit
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
        DictIcon(
            painter = painterResource(id = R.drawable.ic_add),
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.outline.copy(0.15f)),
            color = MaterialTheme.colorScheme.outline.copy(0.75f)
        )

        Text(
            text = strings.addDictionary,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DictionaryItemContent(
    strings: StringResources,
    model: DictionaryModel,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
            )
            .defaultPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = model.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )

            if (model.wordsCount > 0) {
                Text(
                    text = strings.countWords(model.wordsCount),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

        Text(
            text = "${model.percentage}%",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.outline
        )
    }
}