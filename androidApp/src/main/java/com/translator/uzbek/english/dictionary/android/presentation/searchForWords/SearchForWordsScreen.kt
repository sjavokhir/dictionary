package com.translator.uzbek.english.dictionary.android.presentation.searchForWords

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
import com.translator.uzbek.english.dictionary.android.design.components.DictTextField
import com.translator.uzbek.english.dictionary.android.design.localization.LocalStrings
import com.translator.uzbek.english.dictionary.android.design.localization.StringResources
import com.translator.uzbek.english.dictionary.android.design.theme.DividerColor
import com.translator.uzbek.english.dictionary.android.design.theme.WindowBackground
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

    DictContainer(
        title = strings.search,
        onNavigateUp = navigator::navigateUp
    ) {
        SearchForWordsScreenContent(
            strings = strings,
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
private fun SearchForWordsScreenContent(
    strings: StringResources,
    state: SearchForWordsState,
    onEvent: (SearchForWordsEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(WindowBackground),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            DictTextField(
                baseModifier = Modifier.padding(bottom = 8.dp),
                value = state.query,
                onValueChange = {
                    onEvent(SearchForWordsEvent.ChangeQuery(it))
                },
                placeholder = strings.enterAtLeastOneLetter,
                leadingIcon = {
                    DictIcon(
                        painter = painterResource(id = R.drawable.ic_search),
                        color = MaterialTheme.colorScheme.outline
                    )
                },
                imeAction = ImeAction.Done
            )
        }

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
                    },
                    onPlayClick = {
                    }
                )
            }
        }
    }
}

@Composable
private fun SearchForWordItemContent(
    model: WordModel,
    onClick: () -> Unit,
    onPlayClick: () -> Unit
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
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
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
