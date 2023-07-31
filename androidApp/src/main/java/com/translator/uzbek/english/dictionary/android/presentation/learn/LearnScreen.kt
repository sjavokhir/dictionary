package com.translator.uzbek.english.dictionary.android.presentation.learn

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import com.translator.uzbek.english.dictionary.android.R
import com.translator.uzbek.english.dictionary.android.core.extensions.clickableSingle
import com.translator.uzbek.english.dictionary.android.core.extensions.defaultPadding
import com.translator.uzbek.english.dictionary.android.design.components.DictContainer
import com.translator.uzbek.english.dictionary.android.design.components.DictIcon
import com.translator.uzbek.english.dictionary.android.design.localization.LocalStrings
import com.translator.uzbek.english.dictionary.android.design.localization.StringResources
import com.translator.uzbek.english.dictionary.android.design.mapper.localized
import com.translator.uzbek.english.dictionary.android.design.theme.ChartColor3
import com.translator.uzbek.english.dictionary.android.design.theme.ChartColor4
import com.translator.uzbek.english.dictionary.android.design.theme.DividerColor
import com.translator.uzbek.english.dictionary.android.design.theme.LocalSubscription
import com.translator.uzbek.english.dictionary.android.design.theme.WindowBackground
import com.translator.uzbek.english.dictionary.android.presentation.destinations.DictionarySelectionScreenDestination
import com.translator.uzbek.english.dictionary.android.presentation.destinations.LearnWordsScreenDestination
import com.translator.uzbek.english.dictionary.android.presentation.destinations.PremiumScreenDestination
import com.translator.uzbek.english.dictionary.android.presentation.destinations.RepeatWordsScreenDestination
import com.translator.uzbek.english.dictionary.android.presentation.settings.DividerContent
import com.translator.uzbek.english.dictionary.data.model.common.QuoteModel
import com.translator.uzbek.english.dictionary.presentation.learn.LearnState
import com.translator.uzbek.english.dictionary.presentation.learn.LearnViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun LearnScreen(
    viewModel: LearnViewModel = viewModel(),
    navigator: DestinationsNavigator
) {
    val strings = LocalStrings.current
    val hasSubscription = LocalSubscription.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    DictContainer(state.headerTitle.localized()) {
        LearnScreenContent(
            strings = strings,
            hasSubscription = hasSubscription,
            state = state,
            onNavigate = navigator::navigate,
        )
    }
}

@Composable
private fun LearnScreenContent(
    strings: StringResources,
    hasSubscription: Boolean,
    state: LearnState,
    onNavigate: (Direction) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(WindowBackground),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .border(
                        width = 1.dp,
                        color = DividerColor,
                        shape = MaterialTheme.shapes.medium
                    )
                    .background(MaterialTheme.colorScheme.background)
            ) {
                ChosenDictionariesContent(strings, state) {
                    onNavigate(DictionarySelectionScreenDestination)
                }

                DividerContent()

                LearnWordsContent(strings, state) {
                    onNavigate(LearnWordsScreenDestination)
                }

                DividerContent()

                RepeatWordsContent(strings, state) {
                    onNavigate(RepeatWordsScreenDestination)
                }
            }
        }

        if (!hasSubscription) {
            item { PremiumContent(strings, onNavigate) }
        }

        state.quote?.let {
            item { QuoteContent(it) }
        }
    }
}

@Composable
private fun ChosenDictionariesContent(
    strings: StringResources,
    state: LearnState,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickableSingle(onClick = onClick)
            .defaultPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        DictIcon(
            painter = painterResource(id = R.drawable.ic_choose),
            color = MaterialTheme.colorScheme.outline
        )

        Text(
            text = strings.dictionariesChosen(state.chosenDictionaries.size),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun LearnWordsContent(
    strings: StringResources,
    state: LearnState,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickableSingle(onClick = onClick)
            .defaultPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        DictIcon(
            painter = painterResource(id = R.drawable.ic_add_circle),
            color = ChartColor3
        )

        Column {
            Text(
                text = strings.learnNewWords,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = strings.memorizedToday(state.memorizedToday, state.dailyGoal),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
fun RepeatWordsContent(
    strings: StringResources,
    state: LearnState,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickableSingle(onClick = onClick)
            .defaultPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        DictIcon(
            painter = painterResource(id = R.drawable.ic_repeat),
            color = ChartColor4
        )

        Column {
            Text(
                text = strings.repeatWords,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = when {
                    state.repeatedWords == 0 -> strings.youHaventRepeatedWords
                    state.chosenDictionaries.isEmpty() -> strings.chooseCategoriesToRepeat
                    else -> strings.wordsToRepeat(state.repeatedWords)
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
private fun PremiumContent(
    strings: StringResources,
    onNavigate: (Direction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = 1.dp,
                color = ChartColor3.copy(alpha = 0.5f),
                shape = MaterialTheme.shapes.medium
            )
            .background(ChartColor3.copy(alpha = 0.1f))
            .clickableSingle {
                onNavigate(PremiumScreenDestination)
            }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_rewarded_ads),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                colorFilter = ColorFilter.tint(ChartColor3)
            )

            Text(
                text = strings.goPremium,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = ChartColor3
            )
        }

        Text(
            text = strings.premiumFeatures,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun QuoteContent(
    model: QuoteModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = 1.dp,
                color = DividerColor,
                shape = MaterialTheme.shapes.medium
            )
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DictIcon(
            painter = painterResource(id = R.drawable.ic_quote),
            color = MaterialTheme.colorScheme.outline
        )

        Text(
            text = model.quote,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline
        )

        Text(
            text = model.author,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.align(Alignment.End)
        )

        DictIcon(
            painter = painterResource(id = R.drawable.ic_quote),
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.align(Alignment.End)
        )
    }
}