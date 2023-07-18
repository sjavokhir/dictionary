package com.translator.uzbek.english.dictionary.android.presentation.statistics

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.translator.uzbek.english.dictionary.android.R
import com.translator.uzbek.english.dictionary.android.design.components.DictContainer
import com.translator.uzbek.english.dictionary.android.design.components.DictIcon
import com.translator.uzbek.english.dictionary.android.design.localization.LocalStrings
import com.translator.uzbek.english.dictionary.android.design.localization.StringResources
import com.translator.uzbek.english.dictionary.android.presentation.settings.DividerContent
import com.translator.uzbek.english.dictionary.presentation.statistics.StatisticsEvent
import com.translator.uzbek.english.dictionary.presentation.statistics.StatisticsState
import com.translator.uzbek.english.dictionary.presentation.statistics.StatisticsViewModel

@Destination
@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel = viewModel()
) {
    val strings = LocalStrings.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    DictContainer(strings.statistics) {
        StatisticsScreenContent(
            strings = strings,
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
private fun StatisticsScreenContent(
    strings: StringResources,
    state: StatisticsState,
    onEvent: (StatisticsEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.05f)),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item { TodayContent(strings, state) }
        item { ChartContent() }
        item { AllContent(strings, state) }
    }
}

@Composable
private fun TodayContent(
    strings: StringResources,
    state: StatisticsState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                shape = MaterialTheme.shapes.medium
            )
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = 14.dp,
                    end = 4.dp,
                    top = 12.dp,
                    bottom = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CircularProgressIndicator(
                progress = (state.today.toFloat() / state.dailyGoal.toFloat()),
                modifier = Modifier.size(26.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                strokeWidth = 3.dp,
                strokeCap = StrokeCap.Round
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = strings.newWords,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline
                )

                Text(
                    text = strings.todayWords(state.today, state.dailyGoal),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Divider(
            modifier = Modifier
                .height(24.dp)
                .width(1.dp)
        )

        Row(
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = 14.dp,
                    end = 4.dp,
                    top = 12.dp,
                    bottom = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CircularProgressIndicator(
                progress = (state.currentStreak.toFloat() / state.allBestStreak.toFloat()),
                modifier = Modifier.size(26.dp),
                color = MaterialTheme.colorScheme.error,
                trackColor = MaterialTheme.colorScheme.error.copy(alpha = 0.2f),
                strokeWidth = 3.dp,
                strokeCap = StrokeCap.Round
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = strings.currentStreak,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline
                )

                Text(
                    text = strings.countDays(state.currentStreak),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun ChartContent() {
}

@Composable
private fun AllContent(
    strings: StringResources,
    state: StatisticsState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                shape = MaterialTheme.shapes.medium
            )
            .background(MaterialTheme.colorScheme.background)
    ) {
        AllItemContent(
            title = strings.learning,
            value = strings.countWords(state.allLearning),
            icon = R.drawable.ic_learn_unselected
        )

        DividerContent()

        AllItemContent(
            title = strings.completeLearned,
            value = strings.countWords(state.allLearned),
            icon = R.drawable.ic_star_outline
        )

        DividerContent()

        AllItemContent(
            title = strings.bestStreak,
            value = strings.countDays(state.allBestStreak),
            icon = R.drawable.ic_streak
        )

        DividerContent()

        AllItemContent(
            title = strings.startOfLearning,
            value = state.startOfLearning,
            icon = R.drawable.ic_time
        )
    }
}

@Composable
private fun AllItemContent(
    title: String,
    value: String,
    @DrawableRes icon: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 14.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        DictIcon(
            painter = painterResource(id = icon),
            color = MaterialTheme.colorScheme.outline
        )

        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.outline
        )
    }
}
