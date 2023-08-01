package com.translator.uzbek.english.dictionary.android.presentation.dictionarySelection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
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
import com.translator.uzbek.english.dictionary.data.database.model.DictionaryModel
import com.translator.uzbek.english.dictionary.presentation.dictionarySelection.DictionarySelectionEvent
import com.translator.uzbek.english.dictionary.presentation.dictionarySelection.DictionarySelectionState
import com.translator.uzbek.english.dictionary.presentation.dictionarySelection.DictionarySelectionViewModel

@Destination
@Composable
fun DictionarySelectionScreen(
    viewModel: DictionarySelectionViewModel = viewModel(),
    navigator: DestinationsNavigator
) {
    val strings = LocalStrings.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    DictContainer(
        title = strings.dictionariesSelection,
        onNavigateUp = navigator::navigateUp
    ) {
        DictionarySelectionScreenContent(
            strings = strings,
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
private fun DictionarySelectionScreenContent(
    strings: StringResources,
    state: DictionarySelectionState,
    onEvent: (DictionarySelectionEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WindowBackground),
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(20.dp)
                .clip(MaterialTheme.shapes.medium)
                .border(
                    width = 1.dp,
                    color = DividerColor,
                    shape = MaterialTheme.shapes.medium
                )
                .background(MaterialTheme.colorScheme.background)
        ) {
            itemsIndexed(state.dictionaries) { index, model ->
                DictionarySelectionItemContent(
                    strings = strings,
                    model = model,
                    onClick = {
                        onEvent(DictionarySelectionEvent.SelectDictionary(model))
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

@Composable
private fun DictionarySelectionItemContent(
    strings: StringResources,
    model: DictionaryModel,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickableSingle(onClick = onClick)
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
                fontWeight = FontWeight.Medium,
                color = if (model.isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onBackground
                }
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

        DictIcon(
            painter = painterResource(
                id = if (model.isSelected) {
                    R.drawable.ic_check_circle
                } else {
                    R.drawable.ic_check_empty
                }
            ),
            color = if (model.isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            }
        )
    }
}