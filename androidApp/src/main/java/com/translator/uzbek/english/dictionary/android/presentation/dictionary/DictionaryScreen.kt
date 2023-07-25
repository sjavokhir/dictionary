package com.translator.uzbek.english.dictionary.android.presentation.dictionary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.ramcosta.composedestinations.spec.Direction
import com.translator.uzbek.english.dictionary.android.R
import com.translator.uzbek.english.dictionary.android.core.extensions.clickableSingle
import com.translator.uzbek.english.dictionary.android.design.components.DictContainer
import com.translator.uzbek.english.dictionary.android.design.components.DictIcon
import com.translator.uzbek.english.dictionary.android.design.components.DictTextField
import com.translator.uzbek.english.dictionary.android.design.components.DividerContent
import com.translator.uzbek.english.dictionary.android.design.localization.LocalStrings
import com.translator.uzbek.english.dictionary.android.design.localization.StringResources
import com.translator.uzbek.english.dictionary.android.presentation.destinations.AddDictionaryScreenDestination
import com.translator.uzbek.english.dictionary.data.model.common.DictionaryModel
import com.translator.uzbek.english.dictionary.presentation.dictionary.DictionaryEvent
import com.translator.uzbek.english.dictionary.presentation.dictionary.DictionaryState
import com.translator.uzbek.english.dictionary.presentation.dictionary.DictionaryViewModel
import com.translator.uzbek.english.dictionary.shared.randomUUID

@Destination
@Composable
fun DictionaryScreen(
    viewModel: DictionaryViewModel = viewModel(),
    navigator: DestinationsNavigator,
) {
    val strings = LocalStrings.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(DictionaryEvent.FetchDictionaries)
    }

    DictContainer(strings.dictionary) {
        DictionaryScreenContent(
            strings = strings,
            state = state,
            onNavigate = navigator::navigate
        )
    }
}

@Composable
private fun DictionaryScreenContent(
    strings: StringResources,
    state: DictionaryState,
    onNavigate: (Direction) -> Unit
) {
    LazyColumn {
        item {
            DictTextField(
                baseModifier = Modifier
                    .padding(
                        vertical = 16.dp,
                        horizontal = 20.dp
                    )
                    .clickableSingle {
                    },
                placeholder = strings.searchForWords,
                leadingIcon = {
                    DictIcon(
                        painter = painterResource(id = R.drawable.ic_search),
                        color = MaterialTheme.colorScheme.outline
                    )
                },
                readOnly = true
            )
        }
        item {
            AddDictionaryItemContent(strings) {
                onNavigate(AddDictionaryScreenDestination(id = randomUUID()))
            }
        }
        items(state.list) { model ->
            DictionaryItemContent(strings, model) {
            }
        }
    }
}

@Composable
private fun AddDictionaryItemContent(
    strings: StringResources,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .clickableSingle(onClick = onClick)
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 16.dp
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            DictIcon(
                painter = painterResource(id = R.drawable.ic_add),
                modifier = Modifier
                    .size(36.dp)
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

        DividerContent()
    }
}

@Composable
private fun DictionaryItemContent(
    strings: StringResources,
    model: DictionaryModel,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .clickableSingle(onClick = onClick)
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 16.dp
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = model.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = strings.countWords(model.countWords),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Text(
                text = "${model.percentage}%",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline
            )
        }

        DividerContent()
    }
}