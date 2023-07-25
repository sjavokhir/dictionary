package com.translator.uzbek.english.dictionary.android.presentation.statistics

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.ramcosta.composedestinations.annotation.Destination
import com.translator.uzbek.english.dictionary.android.R
import com.translator.uzbek.english.dictionary.android.core.extensions.defaultPadding
import com.translator.uzbek.english.dictionary.android.design.components.DictContainer
import com.translator.uzbek.english.dictionary.android.design.components.DictIcon
import com.translator.uzbek.english.dictionary.android.design.components.chart.StackedBarChart
import com.translator.uzbek.english.dictionary.android.design.localization.LocalStrings
import com.translator.uzbek.english.dictionary.android.design.localization.StringResources
import com.translator.uzbek.english.dictionary.android.design.theme.ChartColor1
import com.translator.uzbek.english.dictionary.android.design.theme.ChartColor2
import com.translator.uzbek.english.dictionary.android.design.theme.ChartColor3
import com.translator.uzbek.english.dictionary.android.design.theme.ChartColor4
import com.translator.uzbek.english.dictionary.android.design.theme.DividerColor
import com.translator.uzbek.english.dictionary.android.design.theme.WindowBackground
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
    val chartEntry = remember(state) {
        val result = listOf(
            listOf(
                FloatEntry(x = 0f, y = 8.109655f),
                FloatEntry(x = 1f, y = 8.359958f),
                FloatEntry(x = 2f, y = 5.276782f),
                FloatEntry(x = 3f, y = 6.746524f),
                FloatEntry(x = 4f, y = 5.259962f),
                FloatEntry(x = 5f, y = 19.461294f),
                FloatEntry(x = 6f, y = 11.246616f),
                FloatEntry(x = 7f, y = 15.599502f)
            ),
            listOf(
                FloatEntry(x = 0f, y = 18.21646f),
                FloatEntry(x = 1f, y = 4.4444942f),
                FloatEntry(x = 2f, y = 16.666405f),
                FloatEntry(x = 3f, y = 14.607819f),
                FloatEntry(x = 4f, y = 19.605549f),
                FloatEntry(x = 5f, y = 11.653311f),
                FloatEntry(x = 6f, y = 4.3142023f),
                FloatEntry(x = 7f, y = 3.143386f)
            ),
            listOf(
                FloatEntry(x = 0f, y = 16.893509f),
                FloatEntry(x = 1f, y = 5.230087f),
                FloatEntry(x = 2f, y = 9.749267f),
                FloatEntry(x = 3f, y = 7.5082035f),
                FloatEntry(x = 4f, y = 10.899482f),
                FloatEntry(x = 5f, y = 15.50517f),
                FloatEntry(x = 6f, y = 13.387053f),
                FloatEntry(x = 7f, y = 15.281599f)
            ),
            listOf(
                FloatEntry(x = 0f, y = 12.232462f),
                FloatEntry(x = 1f, y = 14.679606f),
                FloatEntry(x = 2f, y = 7.035064f),
                FloatEntry(x = 3f, y = 8.455473f),
                FloatEntry(x = 4f, y = 10.374655f),
                FloatEntry(x = 5f, y = 12.2341175f),
                FloatEntry(x = 6f, y = 10.900553f),
                FloatEntry(x = 7f, y = 6.320575f)
            )
        )

        ChartEntryModelProducer(result)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(WindowBackground),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item { TodayContent(strings, state) }
        item { ChartContent(strings, state, chartEntry) }
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
                color = DividerColor,
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ChartContent(
    strings: StringResources,
    state: StatisticsState,
    chartEntry: ChartEntryModelProducer
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
    ) {
        StackedBarChart(
            modifier = Modifier.padding(start = 10.dp),
            chartEntryModelProducer = chartEntry,
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.defaultPadding()
        ) {
            ChartCountItemContent(
                title = strings.countLearned(state.learned),
                color = ChartColor4
            )

            ChartCountItemContent(
                title = strings.countNew(state.new),
                color = ChartColor3
            )

            ChartCountItemContent(
                title = strings.countLearning(state.learning),
                color = ChartColor2
            )

            ChartCountItemContent(
                title = strings.countSkipped(state.skipped),
                color = ChartColor1
            )
        }
    }
}

@Composable
private fun ChartCountItemContent(
    title: String,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )

        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 13.sp
        )
    }
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
                color = DividerColor,
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
            .defaultPadding(),
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
